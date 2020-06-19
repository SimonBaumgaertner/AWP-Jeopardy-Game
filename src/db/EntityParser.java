package db;


import Entities.Category;
import Entities.Template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EntityParser {

    DatabaseHelper dbh;


    public List<Object> parseEntriesIntoObjects(String answer, Class c) {
        List<String> EntriesAsStrings = Arrays.asList(answer.split("\n"));
        return createObjectsForEntries(EntriesAsStrings, c);
    }

    private List<Object> createObjectsForEntries(List<String> entriesAsStrings, Class c) {
        List<Object> list = new LinkedList<>();
        for (String entry: entriesAsStrings) {
            System.out.println(entry.split("\\\\\t"));
            System.out.println(entry);
            if (c == Category.class) {
               list.add(new Category(Integer.valueOf(entry.split("\t")[0]), entry.split("\t")[1], (Template) dbh.findById("test232", Template.class)));
            }
        }
        return list;
    }

}
