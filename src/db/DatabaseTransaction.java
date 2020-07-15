package db;

import entities.Entity;

import java.util.LinkedList;
import java.util.List;

public class DatabaseTransaction {
    // this is a List of cloned Entities so we know what value actually changed when updating
    protected List<Entity> entitiesClone;
    private DatabaseManager databaseManager;
    protected List<String> SQLInstructions = new LinkedList<>();

    DatabaseTransaction(List<Entity> entities, DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        cloneEntities(entities);
    }

    private void cloneEntities(List<Entity> entities) {
        entitiesClone = new LinkedList<>();
        for (Entity entity : entities) {
            entitiesClone.add(entity.createClone());
        }
    }

    protected void persist(Entity entity) {
        String sqlTable = entity.getClass().toString().split("\\.")[1];
        String sqlInstruction = "Insert Into " + sqlTable + " values " + entity.getValues() + ";\n";
        SQLInstructions.add(sqlInstruction);
    }
    protected void update(Entity entity) {
        remove(entity);
        persist(entity);
    }
    protected void remove(Entity entity) {
        String sqlTable = entity.getClass().toString().split("\\.")[1];
        String sqlInstructionDelete ="DELETE FROM "+ sqlTable +" WHERE "+ sqlTable +"id=" + entity.getId()+";\n";
        SQLInstructions.add(sqlInstructionDelete);
    }
    protected void flush() {
        commit();
    }

    public void commit() {
        String hugeSql = "";
        for (String s : SQLInstructions) {
            hugeSql += s;
        }

        databaseManager.executor.executeSqlStatement(hugeSql);
    }
}
