package entities;

import db.DatabaseManager;

import java.util.LinkedList;
import java.util.List;

public class Game extends Entity{
    Template template;
    String gameName;
    DatabaseManager db = new DatabaseManager();

    public Game(int gameId, Template template) {
        this.id = gameId;
        this.template = template;
    }

    public Game(Template template) {
        this.template = template;
        id = DatabaseManager.getAndIncreaseID();
    }

    public Game() {

    }

    public void generateName() {
      List<Entity> players = db.getAllOf(Player.class);
      List<String> names = new LinkedList<>();

      for (Entity entity : players) {
          Player player = (Player) entity;
          if (player.game == this) {
              names.add(player.getPlayerName());
          }
      }
      gameName = names.get(0) + " VS " + names.get(1)+ " #" + id;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public void takeValuesOf(Entity e) {
        Game model = (Game) e;
        setTemplate(model.getTemplate());
    }

    @Override
    public String getValues() {
        return "(" + id + ", " + getTemplate().getId()+ ")";
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

}
