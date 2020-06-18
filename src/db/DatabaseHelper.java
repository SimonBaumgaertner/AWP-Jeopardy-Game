package db;


import db.cfg.CfgReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseHelper {

    CfgReader cfgReader = new CfgReader();

    public boolean executeSql(String sqlStatement) {
        return true;
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
