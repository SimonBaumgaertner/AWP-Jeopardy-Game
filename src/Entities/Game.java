package Entities;

public class Game {
    int gameId;
    Template template;

    public Game(int gameId, Template template) {
        this.gameId = gameId;
        this.template = template;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
