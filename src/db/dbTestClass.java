package db;

import Entities.Category;
import Entities.Template;

public class dbTestClass {
    public static void main(String[] args) {
        DatabaseHelper dbh = new DatabaseHelper();

        System.out.println(dbh.getAllOf(Category.class));

    }
}
