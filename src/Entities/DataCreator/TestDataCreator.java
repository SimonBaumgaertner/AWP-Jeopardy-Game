package Entities.DataCreator;

import Entities.*;
import db.DatabaseManager;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TestDataCreator {
    public void installTestData() {
        List<Entity> entityList = new LinkedList<>();

        Template template = new Template("Test Template");
        Category cBWP = new Category("BWP", template);
        Category cAWP = new Category("AWP", template);
        Category cSozi = new Category("Sozi", template);
        Category cVSY = new Category("VSY", template);
        Category cEnglisch = new Category("Englisch", template);
        Category cITP = new Category("IT-P", template);
        entityList.addAll(Arrays.asList(template, cBWP, cAWP, cSozi, cVSY, cEnglisch, cITP));


        for (int i = 1; i <= 6; i++) {
             for (int j = 1; j <= 5; j++) {
                 Field field = new Field((Category) entityList.get(i), j);
                 Question question = new Question(field, "This is a test statement/question", "This is a test answer");
                 entityList.add(field);
                 entityList.add(question);
             }
        }

        DatabaseManager databaseManager = new DatabaseManager();


        databaseManager.openTransaction();
        for (int i = 0; i < entityList.size(); i++) {
            databaseManager.persist(entityList.get(i));
        }
        databaseManager.commitTransaction();

    }
}
