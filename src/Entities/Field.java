package Entities;

public class Field {
    int fieldId;
    Category category;

    public Field(int fieldId, Category category, int rowNumber) {
        this.fieldId = fieldId;
        this.category = category;
        this.rowNumber = rowNumber;
    }

    int rowNumber;

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
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
}
