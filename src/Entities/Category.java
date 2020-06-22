package Entities;

import db.DatabaseManager;

public class Category extends Entity{
    String categoryName;
    Template template;

    public Category(int id, String categoryName, Template template) {
        this.id = id;
        this.categoryName = categoryName;
        this.template = template;
    }

    public Category(String categoryName, Template template) {
        this.categoryName = categoryName;
        this.template = template;
        id = DatabaseManager.getAndIncreaseID();
    }

    public Category() {

    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public void takeValuesOf(Entity e) {
        Category model = (Category) e;
        setCategoryName(model.getCategoryName());
        setTemplate(model.getTemplate());
    }

    @Override
    public String getValues() {
        return "(" + id + ", '" + getCategoryName()+ "'," + getTemplate().getId() + ")";
    }


}
