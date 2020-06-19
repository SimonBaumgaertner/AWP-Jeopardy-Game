package db;


import db.cfg.CfgReader;
import db.sql.ScriptRunner;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseHelper {

    CfgReader cfgReader = new CfgReader();

    public boolean executeSqlStatement(String sqlStatement) {

        String path = Paths.get("").toAbsolutePath().toString() + "/src/db/sql/temp.sql";
        try {
            FileWriter myWriter = new FileWriter(path);

            myWriter.write("USE jeopardy; \n" + sqlStatement);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return executeSqlData(Paths.get(path));
    }

    public boolean executeSqlData(Path path) {
        //Registering the Driver
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Getting the connection
        Connection con = getConnection();
        System.out.println("Connection established......");
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
            sr.runScript(reader);
            System.out.println("script ran SUCCESSFULLY!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean createDatabase() {
        try {
            if (getConnection() == null) {
                getmySqlConnection().createStatement().execute("CREATE DATABASE Jeopardy;");
                System.out.println("Database 'Jeopardy' Created");
                return true;
            } else {
                System.out.println("Database already exists");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
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
    public Connection getmySqlConnection() {
        try {
            return DriverManager.getConnection(
                    cfgReader.getUrl() + "/mySql?&useLegacyDatetimeCode=false&serverTimezone=CET", cfgReader.getUser(), cfgReader.getPassword());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    };


    public void printCfg() {
        System.out.println(cfgReader.getUrl());
        System.out.println(cfgReader.getUser());
        System.out.println(cfgReader.getPassword());
    }
}
