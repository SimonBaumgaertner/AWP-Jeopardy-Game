package db;

import Entities.DataCreator.TestDataCreator;

public class Install {
    public static void main(String[] args) {
        DatabaseManager installer = new DatabaseManager();
        installer.install();
        TestDataCreator tdc = new TestDataCreator();
        tdc.installTestData();
    }
}
