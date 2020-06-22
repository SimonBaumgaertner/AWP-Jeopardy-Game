package db;

import Entities.*;

import java.util.*;

public class DatabaseManager {
    // this List always has to be synchronized with Database.
    private static List<Entity> synchronizedEntities = new LinkedList<>();
    private static int id = 0;
    DatabaseTransaction databaseTransaction;
    DatabaseExecutor executor = new DatabaseExecutor();

    public void persist(Entity entity) {
        synchronizedEntities.add(entity);
        databaseTransaction.persist(entity);
    }

    public void remove(Entity entity) {
        synchronizedEntities.remove(entity);
        databaseTransaction.remove(entity);
    }
    public void update(Entity entity) {
        databaseTransaction.update(entity);
    }

    public void persist(Entity ... entities) {
        for (Entity entity :  entities) {
            persist(entity);
        }
    }

    /**
     * opening 2 Transactions can lead to problems!
     */
    public void openTransaction()  {
        databaseTransaction = new DatabaseTransaction(synchronizedEntities, this);
    }

    public void commitTransaction() {
        databaseTransaction.commit();
        databaseTransaction = null;
    }

    public void flush() {
        databaseTransaction.flush();
    };

    // only do this at the start as it will destroy Object relations
    private void initializeMatrix() {
        synchronizedEntities.addAll(executor.getAllOfFromDatabase(Template.class));
        synchronizedEntities.addAll(executor.getAllOfFromDatabase(Category.class));
        synchronizedEntities.addAll(executor.getAllOfFromDatabase(Field.class));
        synchronizedEntities.addAll(executor.getAllOfFromDatabase(Question.class));
        synchronizedEntities.addAll(executor.getAllOfFromDatabase(Game.class));
        synchronizedEntities.addAll(executor.getAllOfFromDatabase(Player.class));
        synchronizedEntities.addAll(executor.getAllOfFromDatabase(AnsweredQuestion.class));
    }

    static boolean matrixIsInitialized = false;

    public DatabaseManager() {
        if (!matrixIsInitialized) {
            matrixIsInitialized = true;
            initializeMatrix();
        }
        id = getHighestId() + 1;
    }

    private int getHighestId() {
        int highestId = 0;
        for (Entity entity : synchronizedEntities) {
            if (entity.getId() > highestId) {
                highestId = entity.getId();
            }
        }
        return highestId;
    }

    public List<Entity> getAllOf(Class entityClass) {
        return new LinkedList<>(executor.getAllOfFromDatabase(entityClass));
    }

    public Entity findById(int id, Class c) {
        for (Entity entity : synchronizedEntities) {
            if (id == entity.getId() && entity.getClass().isInstance(c)) {
                return entity;
            }
        }
        System.out.println("DatabaseManager could not find: " + c + " with id" + id);
        return null;
    }

    public void install() {
        executor.install();
    }

    public static int getAndIncreaseID() {
        return id++;
    }
}
