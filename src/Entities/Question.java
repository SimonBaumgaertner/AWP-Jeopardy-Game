package Entities;

public class Question {
    int questionId;
    Field field;
    String statement;
    String answer;

    public Question(int questionId, Field field, String statement, String answer) {
        this.questionId = questionId;
        this.field = field;
        this.statement = statement;
        this.answer = answer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
