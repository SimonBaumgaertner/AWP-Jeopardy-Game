package entities;

import db.DatabaseManager;

@Deprecated
public class AnsweredQuestion extends Entity {
    Game game;
    Question question;

    public AnsweredQuestion(int id, Game game, Question question) {
        this.id = id;
        this.game = game;
        this.question = question;
    }

    public AnsweredQuestion(Game game, Question question) {
        this.game = game;
        this.question = question;
        id = DatabaseManager.getAndIncreaseID();
    }

    public AnsweredQuestion() {

    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public void takeValuesOf(Entity e) {
        AnsweredQuestion model = (AnsweredQuestion) e;
        setQuestion(model.getQuestion());
        setGame(model.getGame());
    }

    @Override
    public String getValues() {
        return "(" + id + ", " + getGame().getId()+ ", " + getQuestion().getId() + ")";
    }

}
