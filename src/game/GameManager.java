package game;

import db.DatabaseManager;
import entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {
    private static Game activeGame;
    private static Question[][] questionMatrix = new Question[7][6]; // [category][row]
    private static Question activeQuestion;
    private static Player[] players = new Player[2];
    private static Category[] categories = new Category[7];
    private static Map<Category,Integer> categoryMap=new HashMap<Category,Integer>();
    private static Player activePlayer;

    DatabaseManager db = new DatabaseManager();

    public void startGame(Template template, String player1, String player2) {
        activeGame = new Game(template);
        players[0] = new Player(player1, activeGame, 0);
        players[1] = new Player(player2, activeGame,  0);
        loadCategories();
        loadQuestions();
        activePlayer = players[0];
    }

    private void loadCategories() {
        List<Entity>allCategories = db.getAllOf(Category.class);
        for (int i = 0; i < allCategories.size(); i++) {
            Category category = (Category) allCategories.get(i);
            if (category.getTemplate() == activeGame.getTemplate()) {
                categoryMap.put(category,categoryMap.size() +1);
                categories[categoryMap.size()] = category;
             }
            }
        }


    private void loadQuestions() {
        List<Entity> questionList =  db.getAllOf(Question.class);
        for (int i = 1; i < questionList.size(); i++) {
            Question question = (Question) questionList.get(i);
            if (question.getField().getCategory().getTemplate() == activeGame.getTemplate()) {
                questionMatrix[categoryMap.get(question.getField().getCategory())][question.getField().getRowNumber()] = question;
            }
        }
    }

    private void loadPlayer(Game game) {
        int count = 0;
        List<Entity> playersList = db.getAllOf(Player.class);
        for (int i = 0; i < playersList.size(); i++) {
            Player player = (Player) playersList.get(i);
            if (player.getGame() == game) {
                players[count] = player;
                count++;
            }
        }
    }

    public void loadGame(Game game){
        activeGame = game;
        loadPlayer(game);
        loadCategories();
        loadQuestions();
        activePlayer = players[0];

    }

    public void resetManager () {
        activeGame = null;
        questionMatrix = new Question[7][6]; // [category][row]
        activeQuestion = null;
        players = new Player[2];
        categories = new Category[7];
        categoryMap=    new HashMap<Category,Integer>();
        activePlayer = null;
    }

    public Game getActiveGame() {
        return activeGame;
    }

    public void setActiveGame(Game activeGame) {
        GameManager.activeGame = activeGame;
    }
    public Question[][] getQuestionMatrix() {
        return questionMatrix;
    }

    public void setQuestionMatrix(Question[][] questionMatrix) {
        GameManager.questionMatrix = questionMatrix;
    }

    public Question getActiveQuestion() {
        return activeQuestion;
    }

    public void setActiveQuestion(Question activeQuestion) {
        GameManager.activeQuestion = activeQuestion;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        GameManager.players = players;
    }

    public Map<Category, Integer> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map<Category, Integer> categoryMap) {
        GameManager.categoryMap = categoryMap;
    }

    public Category[] getCategories() {
        return categories;
    }
    public Player getActivePlayer() {
        return activePlayer;
    }
    public void turnSwap() {
        if (activePlayer == players[0]) {
            activePlayer = players[1];
        } else if (activePlayer == players[1]) {
            activePlayer = players[0];
        }
    }

}
