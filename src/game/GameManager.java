package game;

import entities.Game;
import entities.Player;
import entities.Template;

public class GameManager {
    private static Game activeGame;

    public void startGame(Template template, String player1, String player2) {
        Game game = new Game(template);
        Player playerOne = new Player(player1, game, 0);
        Player playerTwo = new Player(player2, game,  0);

        game.getPlayers().add(playerOne);
        game.getPlayers().add(playerTwo);

        setActiveGame(game);

    }

    public static Game getActiveGame() {
        return activeGame;
    }

    public static void setActiveGame(Game activeGame) {
        GameManager.activeGame = activeGame;
    }
}
