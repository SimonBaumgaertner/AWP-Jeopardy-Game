package Entities;

import db.DatabaseManager;

public class Question extends Entity{
    Field field;
    String statement;
    String answer;

    public Question(int questionId, Field field, String statement, String answer) {
        this.id = questionId;
        this.field = field;
        this.statement = statement;
        this.answer = answer;
    }

    public Question(Field field, String statement, String answer) {
        this.field = field;
        this.statement = statement;
        this.answer = answer;
        id = DatabaseManager.getAndIncreaseID();
    }

    public Question() {

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

    @Override
    public void takeValuesOf(Entity e) {
        Question model = (Question) e;
        setAnswer(model.getAnswer());
        setField(model.getField());
        setStatement(model.getStatement());
    }

    @Override
    public String getValues() {
        return "(" + id + ", " + getField().getId()+ ", '" +  getStatement() + "', '" + getAnswer() +"')";
    }
}
