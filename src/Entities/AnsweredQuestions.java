package Entities;

public class AnsweredQuestions {
    int answeredQuestion;
    Game game;
    Question question;

    public AnsweredQuestions(int answeredQuestion, Game game, Question question) {
        this.answeredQuestion = answeredQuestion;
        this.game = game;
        this.question = question;
    }

    public int getAnsweredQuestion() {
        return answeredQuestion;
    }

    public void setAnsweredQuestion(int answeredQuestion) {
        this.answeredQuestion = answeredQuestion;
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
}
