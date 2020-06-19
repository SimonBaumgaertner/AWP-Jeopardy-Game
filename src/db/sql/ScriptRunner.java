package db.sql;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Clinton Begin
 */
public class ScriptRunner {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

    private static final String DEFAULT_DELIMITER = ";";

    private static final Pattern DELIMITER_PATTERN = Pattern.compile("^\\s*((--)|(//))?\\s*(//)?\\s*@DELIMITER\\s+([^\\s]+)", Pattern.CASE_INSENSITIVE);

    private final Connection connection;

    private boolean stopOnError;
    private boolean throwWarning;
    private boolean autoCommit;
    private boolean sendFullScript;
    private boolean removeCRs;
    private boolean escapeProcessing = true;

    private PrintWriter logWriter = new PrintWriter(System.out);
    private PrintWriter errorLogWriter = new PrintWriter(System.err);

    private String delimiter = DEFAULT_DELIMITER;
    private boolean fullLineDelimiter;

    public ScriptRunner(Connection connection) {
        this.connection = connection;
    }

    public void setStopOnError(boolean stopOnError) {
        this.stopOnError = stopOnError;
    }

    public void setThrowWarning(boolean throwWarning) {
        this.throwWarning = throwWarning;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public void setSendFullScript(boolean sendFullScript) {
        this.sendFullScript = sendFullScript;
    }

    public void setRemoveCRs(boolean removeCRs) {
        this.removeCRs = removeCRs;
    }

    /**
     * @since 3.1.1
     */
    public void setEscapeProcessing(boolean escapeProcessing) {
        this.escapeProcessing = escapeProcessing;
    }

    public void setLogWriter(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    public void setErrorLogWriter(PrintWriter errorLogWriter) {
        this.errorLogWriter = errorLogWriter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void setFullLineDelimiter(boolean fullLineDelimiter) {
        this.fullLineDelimiter = fullLineDelimiter;
    }

    public String runScript(Reader reader) throws Exception {
        setAutoCommit();
        String answer = "";
        try {
            if (false) {
                answer = executeFullScript(reader);
            } else {
                answer = executeLineByLine(reader);
            }
        } finally {
            rollbackConnection();
        }
        return answer;
    }

    private String executeFullScript(Reader reader) throws Exception {
        String answer = "";
        StringBuilder script = new StringBuilder();
        try {
            BufferedReader lineReader = new BufferedReader(reader);
            String line;
            while ((line = lineReader.readLine()) != null) {
                script.append(line);
                script.append(LINE_SEPARATOR);
            }
            String command = script.toString();
            println(command);
            answer = executeStatement(command);
            commitConnection();
        } catch (Exception e) {
            String message = "Error executing: " + script + ".  Cause: " + e;
            printlnError(message);
            throw new Exception(message, e);
        }
        return answer;
    }

    private String executeLineByLine(Reader reader) throws Exception {
        String answer = "";
        StringBuilder command = new StringBuilder();
        try {
            BufferedReader lineReader = new BufferedReader(reader);
            String line;
            while ((line = lineReader.readLine()) != null) {
                answer += handleLine(command, line);
            }
            commitConnection();
            checkForMissingLineTerminator(command);
        } catch (Exception e) {
            String message = "Error executing: " + command + ".  Cause: " + e;
            printlnError(message);
            throw new Exception(message, e);
        }
        return answer;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            // ignore
        }
    }

    private void setAutoCommit() throws Exception {
        try {
            if (autoCommit != connection.getAutoCommit()) {
                connection.setAutoCommit(autoCommit);
            }
        } catch (Throwable t) {
            throw new Exception("Could not set AutoCommit to " + autoCommit + ". Cause: " + t, t);
        }
    }

    private void commitConnection() throws Exception {
        try {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (Throwable t) {
            throw new Exception("Could not commit transaction. Cause: " + t, t);
        }
    }

    private void rollbackConnection() {
        try {
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
        } catch (Throwable t) {
            // ignore
        }
    }

    private void checkForMissingLineTerminator(StringBuilder command) throws Exception {
        if (command != null && command.toString().trim().length() > 0) {
            throw new Exception("Line missing end-of-line terminator (" + delimiter + ") => " + command);
        }
    }

    private String handleLine(StringBuilder command, String line) throws SQLException {
        String answer = "";
        String trimmedLine = line.trim();
        if (lineIsComment(trimmedLine)) {
            Matcher matcher = DELIMITER_PATTERN.matcher(trimmedLine);
            if (matcher.find()) {
                delimiter = matcher.group(5);
            }
            println(trimmedLine);
        } else if (commandReadyToExecute(trimmedLine)) {
            command.append(line.substring(0, line.lastIndexOf(delimiter)));
            command.append(LINE_SEPARATOR);
            println(command);
            answer = executeStatement(command.toString());
            command.setLength(0);
        } else if (trimmedLine.length() > 0) {
            command.append(line);
            command.append(LINE_SEPARATOR);
        }
        return answer;
    }

    private boolean lineIsComment(String trimmedLine) {
        return trimmedLine.startsWith("//") || trimmedLine.startsWith("--");
    }

    private boolean commandReadyToExecute(String trimmedLine) {
        // issue #561 remove anything after the delimiter
        return !fullLineDelimiter && trimmedLine.contains(delimiter) || fullLineDelimiter && trimmedLine.equals(delimiter);
    }

    private String executeStatement(String command) throws SQLException {
        Statement statement = connection.createStatement();
        String answer = "";
        try {
            statement.setEscapeProcessing(escapeProcessing);
            String sql = command;
            if (removeCRs) {
                sql = sql.replaceAll("\r\n", "\n");
            }
            try {
                boolean hasResults = statement.execute(sql);
                while (!(!hasResults && statement.getUpdateCount() == -1)) {
                    checkWarnings(statement);
                    answer += printResults(statement, hasResults);
                    hasResults = statement.getMoreResults();
                }
            } catch (SQLWarning e) {
                throw e;
            } catch (SQLException e) {
                if (stopOnError) {
                    throw e;
                } else {
                    String message = "Error executing: " + command + ".  Cause: " + e;
                    printlnError(message);
                }
            }
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
                // Ignore to workaround a bug in some connection pools
                // (Does anyone know the details of the bug?)
            }
            return answer;
        }
    }

    private void checkWarnings(Statement statement) throws SQLException {
        if (!throwWarning) {
            return;
        }
        // In Oracle, CREATE PROCEDURE, FUNCTION, etc. returns warning
        // instead of throwing exception if there is compilation error.
        SQLWarning warning = statement.getWarnings();
        if (warning != null) {
            throw warning;
        }
    }

    private String printResults(Statement statement, boolean hasResults) {
        String answer = "";
        if (!hasResults) {
            return answer;
        }
        try (ResultSet rs = statement.getResultSet()) {
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            for (int i = 0; i < cols; i++) {
                String name = md.getColumnLabel(i + 1);
                print(name + "\t");
            }
            println("");
            while (rs.next()) {
                for (int i = 0; i < cols; i++) {
                    String value = rs.getString(i + 1);
                    answer += value + "\t";
                    print(value + "\t");
                }
                println("");
                answer += "\n";
            }
        } catch (SQLException e) {
            printlnError("Error printing results: " + e.getMessage());
        }
        return answer;
    }

    private void print(Object o) {
        if (logWriter != null) {
            logWriter.print(o);
            logWriter.flush();
        }
    }

    private void println(Object o) {
        if (logWriter != null) {
            logWriter.println(o);
            logWriter.flush();
        }
    }

    private void printlnError(Object o) {
        if (errorLogWriter != null) {
            errorLogWriter.println(o);
            errorLogWriter.flush();
        }
    }

}
