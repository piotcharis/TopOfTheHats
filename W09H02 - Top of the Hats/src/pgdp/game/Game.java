package pgdp.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private CollisionBoard collisionBoard;
    private List<Entity> entities;
    private List<Entity> entityAdd;

    public CollisionBoard getCollisionBoard() {
        return collisionBoard;
    }

    public void setCollisionBoard(CollisionBoard collisionBoard) {
        this.collisionBoard = collisionBoard;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Entity> getEntityAdd() {
        return entityAdd;
    }

    public void setEntityAdd(List<Entity> entityAdd) {
        this.entityAdd = entityAdd;
    }

    public Game(CollisionBoard collisionBoard) {
        this.collisionBoard = collisionBoard;
        this.entities = new ArrayList<Entity>();
        this.entityAdd = new ArrayList<Entity>();
    }

    public void runTick() {
        for (int i = 0; i < entities.size(); i++) {
            if (!entities.get(i).visit(this)) {
                collisionBoard.remove(entities.get(i));
                entities.remove(entities.get(i));
            }
        }

        for (int i = 0; i < entityAdd.size(); i++) {
            if (collisionBoard.set(entityAdd.get(i))) {
                entities.add(entityAdd.get(i));
            }
        }
        entityAdd.clear();
    }
}
