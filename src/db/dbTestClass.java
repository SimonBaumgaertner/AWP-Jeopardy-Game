package db;

public class dbTestClass {
    public static void main(String[] args) {
        DatabaseHelper dbh = new DatabaseHelper();
        dbh.executeSqlStatement("Insert into template values ('test232');");

    }
}
