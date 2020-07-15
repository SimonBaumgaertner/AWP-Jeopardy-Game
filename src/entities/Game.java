package entities;

import db.DatabaseManager;

import java.util.List;

public class Game extends Entity{
    Template template;
    List<Question> fieldList;
    List<Player> players;


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


}
