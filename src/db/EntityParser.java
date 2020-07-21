package db;


import entities.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EntityParser {

    DatabaseManager databaseManager;

    public Collection<? extends Entity> parseEntriesIntoObjects(String answer, Class c) {
        databaseManager = new DatabaseManager();
        List<String> entriesAsStrings = Arrays.asList(answer.split("\n"));

        // if there is no entry entriesAsStrings will have emptyString at pos 0 so we empty it
        if (entriesAsStrings.get(0).isEmpty()) {
            entriesAsStrings = new LinkedList<>();
        }
        return createObjectsForEntries(entriesAsStrings, c);
    }

    private Collection<? extends Entity> createObjectsForEntries(List<String> entriesAsStrings, Class c) {
        List<Entity> list = new LinkedList<>();
        for (String entry: entriesAsStrings) {
            if (c == Template.class) {
                int id = Integer.valueOf(entry.split(" --- ")[0]);
                String name = entry.split(" --- ")[1];

                list.add(new Template(id,name));

            } else if (c == Category.class) {
                int id = Integer.valueOf(entry.split(" --- ")[0]);
                String name = entry.split(" --- ")[1];
                Template template = (Template) databaseManager.findById(Integer.valueOf(entry.split(" --- ")[2]), Template.class);

                list.add(new Category(id,name,template));

            } else if (c == Field.class) {
                int id = Integer.valueOf(entry.split(" --- ")[0]);
                Category category = (Category) databaseManager.findById(Integer.valueOf(entry.split(" --- ")[1]), Category.class);
                int rowNumber = Integer.valueOf(entry.split(" --- ")[2]);

                list.add(new Field(id,category,rowNumber));

            } else if (c == Question.class) {
                int id = Integer.valueOf(entry.split(" --- ")[0]);
                Field field = (Field) databaseManager.findById(Integer.valueOf(entry.split(" --- ")[1]), Field.class);
                String statement = entry.split(" --- ")[2];
                String answer = entry.split(" --- ")[3];
                boolean answered = parseToBoolean(entry.split(" --- ")[4]);
                if (id == 143) {
                    System.out.println("yee");
                }

                list.add(new Question(id,field,statement,answer,answered));

            }  else if (c == Game.class) {
                int id = Integer.valueOf(entry.split(" --- ")[0]);
                Template template = (Template) databaseManager.findById(Integer.valueOf(entry.split(" --- ")[1]), Template.class);

                list.add(new Game(id,template));

            } else if (c == Player.class) {
                int id = Integer.valueOf(entry.split(" --- ")[0]);
                Game game = (Game) databaseManager.findById(Integer.valueOf(entry.split(" --- ")[1]), Game.class);
                String name = entry.split(" --- ")[2];
                int points = Integer.valueOf(entry.split(" --- ")[3]);

                list.add(new Player(id,name,game,points));

            }else if (c == AnsweredQuestion.class) {
                int id = Integer.valueOf(entry.split(" --- ")[0]);
                Game game = (Game) databaseManager.findById(Integer.valueOf(entry.split(" --- ")[1]), Game.class);
                Question question = (Question) databaseManager.findById(Integer.valueOf(entry.split(" --- ")[2]), Question.class);

                list.add(new AnsweredQuestion(id,game,question));

            }
        }
        return list;
    }

    private boolean parseToBoolean(String s) {
        return s.equals("1");
    }

}
