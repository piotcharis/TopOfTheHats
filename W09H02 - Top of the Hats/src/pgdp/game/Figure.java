package pgdp.game;

import java.util.List;

public abstract class Figure extends Entity {
    private Direction lastDirection;
    private int attackCooldown;
    private int moveCooldown;
    private int disabledCooldown;
    private boolean hasHat;
    private Controls controls;

    public Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public int getMoveCooldown() {
        return moveCooldown;
    }

    public void setMoveCooldown(int moveCooldown) {
        this.moveCooldown = moveCooldown;
    }

    public int getDisabledCooldown() {
        return disabledCooldown;
    }

    public void setDisabledCooldown(int disabledCooldown) {
        this.disabledCooldown = disabledCooldown;
    }

    public boolean isHasHat() {
        return hasHat;
    }

    public void setHasHat(boolean hasHat) {
        this.hasHat = hasHat;
    }

    public Controls getControls() {
        return controls;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }

    public Figure(LocatedBoundingBox hitBox) {
        super(hitBox);
        this.moveCooldown = getFullMoveCooldown();
        this.attackCooldown = getFullAttackCooldown();
        this.hasHat = false;
        this.lastDirection = Direction.UP;
        this.disabledCooldown = 0;
    }

    public void moveTo(Game game, Direction dir) {
        this.lastDirection = dir;
        List<Entity> collisions = game.getCollisionBoard().getCollisions(this, dir);
        for (int i = 0; i < collisions.size(); i++) {
            if (collisions.get(i) instanceof Hat && !this.hasHat) {
                this.hasHat = true;
                game.getCollisionBoard().remove(collisions.get(i));
                ((Hat) collisions.get(i)).setDontRemove(false);
            }
        }
        game.getCollisionBoard().move(this, dir);
    }

    public boolean visit(Game game) {
        if (disabledCooldown > 0) {
            disabledCooldown--;
            if (disabledCooldown > 0) {
                return true;
            }
        }

        moveCooldown--;

        if (moveCooldown <= 0) {
            moveCooldown = getFullMoveCooldown();
            controls.move(game, this);
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }

        if (attackCooldown == 0 && controls.attack(game, this)) {
            attackCooldown = getFullAttackCooldown();
            attack(game);
        }

        return true;
    }

    public abstract int getFullAttackCooldown();

    public abstract int getFullMoveCooldown();

    public abstract void attack(Game game);

    void attackHelp(Game game, List<Entity> collisions) {
        for (Entity collision : collisions) {
            if (collision instanceof Hat) {
                this.setHasHat(true);
//                game.getEntities().remove(collision);
            } else if (!(collision instanceof Cookie)) {
                ((Figure) collision).setDisabledCooldown(60);
                if (((Figure) collision).isHasHat() && !this.isHasHat()) {
                    ((Figure) collision).setHasHat(false);
                    this.setHasHat(true);
                }
            }
        }
    }
}
