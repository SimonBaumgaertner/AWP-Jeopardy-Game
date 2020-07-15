package db;

import Entities.*;

import javax.xml.crypto.Data;
import java.util.*;

public class DatabaseManager {
    // this List always has to be synchronized with Database.
    private static List<Entity> synchronizedEntities = new LinkedList<>();
    private static int id = 1;
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

    /*
    if this is for install input true so ids get reset
     */
    public DatabaseManager(boolean install) {
        if (install) {
            synchronizedEntities.clear();
            id = 1;
            if (!matrixIsInitialized) {
                matrixIsInitialized = true;
                initializeMatrix();
            }
        }
    }

    public DatabaseManager() {
        if (!matrixIsInitialized) {
            matrixIsInitialized = true;
            initializeMatrix();
            id = getHighestId() + 1;
        }

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
      List<Entity> cloneFromDb = new LinkedList<>(executor.getAllOfFromDatabase(entityClass));
      List<Entity> entitiesFromList = new LinkedList<>();
      for (Entity entity : synchronizedEntities) {
          if (entity.getClass().equals(entityClass)) {
              entitiesFromList.add(entity);
          }
      }
      synchronizedEntities = synchronizeLists(entitiesFromList,cloneFromDb);
      return synchronizedEntities;
    }


    public Entity findById(int id, Class c) {
        for (Entity entity : synchronizedEntities) {
            if (id == entity.getId()) {
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
    // add all Entities that do not exist in entitiesFromList also update all Entities that exist in both list
    private List<Entity> synchronizeLists(List<Entity> entitiesFromList, List<Entity> cloneFromDb) {
        Map<Integer, Entity> cloneListIdMap = new HashMap<>();
        Map<Integer, Entity> realListIdMap = new HashMap<>();

        for (Entity entity : entitiesFromList) {
            cloneListIdMap.put(entity.getId(), entity);
        }

        for (Entity entity : cloneFromDb) {
            realListIdMap.put(entity.getId(), entity);
        }

        for (Entity entity : cloneFromDb) {
            // if entitiesFromList contain the entity from the dbClone
            if (realListIdMap.containsKey(entity.getId())) {
                // Update
                realListIdMap.get(entity.getId()).takeValuesOf(entity);
                // if entities dont exist in real list yet
            } else {
                // Add
                synchronizedEntities.add(entity);
            }
        }
        return entitiesFromList;

    }

}
