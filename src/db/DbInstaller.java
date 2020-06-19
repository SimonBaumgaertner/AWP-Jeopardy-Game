package db;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DbInstaller {

    DatabaseHelper dbh = new DatabaseHelper();

    public boolean install() {
        try {
            Path installerPath = Paths.get(Paths.get("").toAbsolutePath().toString() + "/src/db/sql/installer.sql");
            dbh.executeSqlData(installerPath);
            System.out.println("Install was SUCCESSFUL!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    };

}
