package Entities;

import db.DatabaseManager;

public class Field extends Entity{
    Category category;
    int rowNumber;

    public Field(int id, Category category, int rowNumber) {
        this.id = id;
        this.category = category;
        this.rowNumber = rowNumber;
    }

    public Field(Category category, int rowNumber) {
        this.category = category;
        this.rowNumber = rowNumber;
        id = DatabaseManager.getAndIncreaseID();
    }

    public Field() {

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    public void takeValuesOf(Entity e) {
        Field model = (Field) e;
        setCategory(model.getCategory());
        setRowNumber(model.getRowNumber());
    }

    @Override
    public String getValues() {
        return "(" + id + ", " + getCategory().getId()+ ", " + getRowNumber() + ")";
    }

}
