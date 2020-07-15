package entities;

import db.DatabaseManager;

public class Player extends Entity{
    String playerName;
    Game game;
    int points;

    public Player(int id, String playerName, Game game, int points) {
        this.id = id;
        this.playerName = playerName;
        this.game = game;
        this.points = points;
    }

    public Player(String playerName, Game game, int points) {
        this.playerName = playerName;
        this.game = game;
        this.points = points;
        id = DatabaseManager.getAndIncreaseID();
    }

    public Player() {
        
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public void takeValuesOf(Entity e) {
        Player model = (Player) e;
        setGame(model.getGame());
        setPlayerName(model.getPlayerName());
        setPoints(model.getPoints());
    }
    @Override
    public String getValues() {
        return "(" + id + ", " + getGame().getId()+ ", '" +  getPlayerName() + "', " + getPoints() +")";
    }


}
