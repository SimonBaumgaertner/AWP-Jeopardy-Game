package Entities;

public class Category {
    int categoryId;
    String categoryName;
    Template template;

    public Category(int categoryId, String categoryName, Template template) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.template = template;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
}
