package db;

import entities.DataCreator.TestDataCreator;

public class Install {
    public static void main(String[] args) {
        DatabaseManager installer = new DatabaseManager(true);
        installer.install();
        TestDataCreator tdc = new TestDataCreator();
        tdc.installTestData();
    }
}
