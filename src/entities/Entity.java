package entities;

public abstract class Entity {
    int id;

    public int getId() {
        return id;
    }

    public abstract void takeValuesOf(Entity e);

    public Entity createClone() {
        Entity entity;
        if (this.getClass().equals(Template.class)) {
                entity = new Template();
                entity.takeValuesOf(this);
        } else if (this.getClass().equals(Category.class)) {
            entity = new Category();
            entity.takeValuesOf(this);
        }else if (this.getClass().equals(Field.class)) {
            entity = new Field();
            entity.takeValuesOf(this);
        }else if (this.getClass().equals(Game.class)) {
            entity = new Game();
            entity.takeValuesOf(this);
        }else if (this.getClass().equals(Question.class)) {
            entity = new Question();
            entity.takeValuesOf(this);
        }else if (this.getClass().equals(Player.class)) {
            entity = new Player();
            entity.takeValuesOf(this);
        }else{
            entity = new AnsweredQuestion();
            entity.takeValuesOf(this);
        }
        return entity;
    }

    public abstract String getValues();
}
