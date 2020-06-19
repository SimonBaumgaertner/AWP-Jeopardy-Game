package Entities;

public class Player {
    int playerId;
    String playerName;
    Game game;
    int points;

    public Player(int playerId, String playerName, Game game, int points) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.game = game;
        this.points = points;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
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
}
