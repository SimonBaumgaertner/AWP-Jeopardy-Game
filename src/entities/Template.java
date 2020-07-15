package entities;

import db.DatabaseManager;

public class Template extends Entity{

    String templateName;

    public Template(int id,String templateName) {
        this.id = id;
        this.templateName = templateName;
    }

    public Template(String templateName) {
        this.templateName = templateName;
        id = DatabaseManager.getAndIncreaseID();
    }

    public Template() {

    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public void takeValuesOf(Entity e) {
        Template model = (Template) e;
        id = model.getId();
        setTemplateName(model.getTemplateName());
    }

    @Override
    public String getValues() {
        return "(" + id + ", '" +  getTemplateName() + "')";
    }
}
