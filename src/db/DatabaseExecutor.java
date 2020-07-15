package db;

import entities.Entity;
import db.cfg.CfgReader;
import db.sql.ScriptRunner;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

public class DatabaseExecutor {
    CfgReader cfgReader = new CfgReader();
    EntityParser ep = new EntityParser();


    protected Collection<? extends Entity> getAllOfFromDatabase(Class c) {
        String answer = executeSqlStatement("select * from " + c.toString().split("\\.")[1] + ";");
        return ep.parseEntriesIntoObjects(answer, c);
    }

    protected String executeSqlStatement(String sqlStatement) {

        String path = Paths.get("").toAbsolutePath().toString() + "/src/db/sql/temp.sql";
        try {
            FileWriter myWriter = new FileWriter(path);

            myWriter.write("USE jeopardy; \n" + sqlStatement);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return executeSqlData(Paths.get(path));
    }

    private String executeSqlData(Path path) {
        //Registering the Driver
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Getting the connection
        Connection con = getConnection();
        //Initialize the script runner
        ScriptRunner sr = new ScriptRunner(con);
        //Creating a reader object
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Running the script
        try {
            String console = sr.runScript(reader);
            return console;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    cfgReader.getUrl() + "/Jeopardy?&useLegacyDatetimeCode=false&serverTimezone=CET", cfgReader.getUser(), cfgReader.getPassword());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    };

    /**
     * @return connection to mySql
     */
    private Connection getmySqlConnection() {
        try {
            return DriverManager.getConnection(
                    cfgReader.getUrl() + "/mySql?&useLegacyDatetimeCode=false&serverTimezone=CET", cfgReader.getUser(), cfgReader.getPassword());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    };

    protected boolean install() {
        //Registering the Driver
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Getting the connection
        Connection con = getmySqlConnection();
        System.out.println("Connection established......");
        try {
            con.createStatement().execute("drop database if exists jeopardy;");
            con.createStatement().execute("create database jeopardy;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            Path installerPath = Paths.get(Paths.get("").toAbsolutePath().toString() + "/src/db/sql/installer.sql");
            executeSqlData(installerPath);
            System.out.println("Install was SUCCESSFUL!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
