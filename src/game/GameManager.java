package game;

import entities.Game;

public class GameManager {
    private static Game activeGame;

    public void startGame() {

    }

    public static Game getActiveGame() {
        return activeGame;
    }

    public static void setActiveGame(Game activeGame) {
        GameManager.activeGame = activeGame;
    }
}
