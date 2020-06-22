package db;

import Entities.Category;
import Entities.Entity;
import Entities.Template;

import java.util.List;

public class dbTestClass {
    public static void main(String[] args) {
        int testNumber = 7;
        System.out.println("Start Test: " + testNumber);
        switch (testNumber) {
            case 1:
                persistWithoutTransactionTest();
                break;
            case 2:
                persistWithTransactionTest();
                break;
            case 3:
                openSecondTransactionTest();
                break;
            case 4:
                changeObjectWithoutPesisting();
                break;
            case 5:
                updateTest();
                break;
            case 6:
                removeTest();
                break;
            case 7:
                relationsTest();
                break;
        }
    }

    private static void persistWithoutTransactionTest() {
        DatabaseManager databaseManager = new DatabaseManager();
        Template template = new Template("Template");
        System.out.println(template.getClass());
        try {
            databaseManager.persist(template);
        } catch (Exception e) {
            System.out.println("Test'persistWithoutTransactionTest' was successful! (Exception when trying to persist without transaction)");
        }
    }

    private static void persistWithTransactionTest() {
        DatabaseManager databaseManager = new DatabaseManager();
        int sizeAtBeginning = databaseManager.getAllOf(Template.class).size();
        Template template = new Template("Template");
        databaseManager.openTransaction();
        databaseManager.persist(template);
        databaseManager.commitTransaction();
        List<Entity> list = databaseManager.getAllOf(Template.class);
        if (list.size() == sizeAtBeginning + 1) {
            System.out.println("Test'persistWithTransactionTest' was successful! ("+ list.size()+" Entity/ies)");
        }
    }

    private static void openSecondTransactionTest() {
        DatabaseManager databaseManager = new DatabaseManager();
        int sizeAtBeginning = databaseManager.getAllOf(Template.class).size();
        Template template = new Template("Template");
        databaseManager.openTransaction();
        databaseManager.persist(template);
        try {
            databaseManager.openTransaction();
        } catch (Exception e) {
            System.out.println("Test'openSecondTransactionTest' was successful! (Exception when trying to open second Transaction)");
        }
    }

    private static void changeObjectWithoutPesisting() {
        DatabaseManager databaseManager = new DatabaseManager();
        Template template = new Template("Template");
        databaseManager.openTransaction();
        databaseManager.persist(template);
        databaseManager.commitTransaction();
        template.setTemplateName("ModifiedName");
        List<Entity> list = databaseManager.getAllOf(Template.class);
        if (!((Template) list.get(list.size()-1)).getTemplateName().equals("ModifiedName")) {
            System.out.println("Test'changeObjectWithoutPesisting' was successful! (name did not change)");
        }
    }

    private static void updateTest() {
        DatabaseManager databaseManager = new DatabaseManager();
        Template template = new Template("Template");
        databaseManager.openTransaction();
        databaseManager.persist(template);
        databaseManager.commitTransaction();
        template.setTemplateName("ModifiedName");
        databaseManager.openTransaction();
        databaseManager.update(template);
        databaseManager.commitTransaction();
        List<Entity> list = databaseManager.getAllOf(Template.class);
        if (((Template) list.get(0)).getTemplateName().equals("ModifiedName")) {
            System.out.println("Test'updateTest' was successful! (name did change)");
        }
    }
    private static void removeTest() {
        DatabaseManager databaseManager = new DatabaseManager();
        Template template = new Template("Template");
        databaseManager.openTransaction();
        databaseManager.persist(template);
        databaseManager.commitTransaction();
        // now delete
        databaseManager.openTransaction();
        databaseManager.remove(template);
        databaseManager.commitTransaction();
        List<Entity> list = databaseManager.getAllOf(Template.class);
        if (list.isEmpty()) {
            System.out.println("Test'removeTest' was successful! (all Removed)");
        }
    }

    private static void relationsTest() {
        DatabaseManager databaseManager = new DatabaseManager();
        Template template = new Template("TemplateTest");
        // create Template
        databaseManager.openTransaction();
        databaseManager.persist(template);
        databaseManager.commitTransaction();
        // create Category
        Category category = new Category("CategoryTest", template);
        databaseManager.openTransaction();
        databaseManager.persist(category);
        databaseManager.commitTransaction();

        List<Entity> list = databaseManager.getAllOf(Category.class);
        Template categoryTemplate = ((Category)(list.get(0))).getTemplate();
        if (categoryTemplate.equals(template)) {
            System.out.println("Test'relationsTest' was successful! (relations Match)");
        }
    }


}
