package pgdp.game;

import pgdp.game.helper.Random;

import java.util.ArrayList;
import java.util.List;

import static pgdp.game.Direction.*;

public abstract class AIControls implements Controls {
    private Entity target;

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public Entity selectTarget(Game game) {
        if (target == null || target.getHitBox().isEmpty()) {
            if (target instanceof Figure && !((Figure) target).isHasHat()) {
                return null;
            }
            List<Entity> entities = new ArrayList<>();

            for (int i = 0; i < game.getEntities().size(); i++) {
                if (game.getEntities().get(i) instanceof Hat && game.getEntities().get(i).getHitBox().isPresent()) {
                    entities.add(game.getEntities().get(i));
                }

                if (game.getEntities().get(i) instanceof Figure && ((Figure) game.getEntities().get(i)).isHasHat() &&
                        game.getEntities().get(i).getHitBox().isPresent()) {
                    entities.add(game.getEntities().get(i));
                }
            }

            int randomInt = Random.get(0, entities.size());

            setTarget(entities.get(randomInt));
        }
        return target;
    }

    public Direction selectDirection(Entity other, Figure self) {
        Position otherPos = other.getHitBox().get().getPosition();
        Position selfPos = self.getHitBox().get().getPosition();

        if (otherPos.equals(selfPos) ||
                other.getHitBox().isEmpty() || self.getHitBox().isEmpty()) {
            return UP;
        }

        if (otherPos.getX() == selfPos.getX()) {
            if (otherPos.getY() > selfPos.getY()) {
                return DOWN;
            }

            if (otherPos.getY() < selfPos.getY()) {
                return UP;
            }
        }

        if (otherPos.getY() == selfPos.getY()) {
            if (otherPos.getX() > selfPos.getX()) {
                return RIGHT;
            }

            if (otherPos.getX() < selfPos.getX()) {
                return LEFT;
            }
        }

        boolean diffX = otherPos.getX() > selfPos.getX();
        boolean diffY = otherPos.getY() > selfPos.getY();

        if (diffX && diffY) {
            return DOWN_RIGHT;
        }
        if (!diffX && !diffY) {
            return UP_LEFT;
        }
        if (diffX) {
            return UP_RIGHT;
        }
        return DOWN_LEFT;

    }

    public void move(Game game, Figure figure) {
        if (!figure.isHasHat()) {
//            game.getCollisionBoard().move(figure, selectDirection(selectTarget(game), figure));
            figure.moveTo(game, selectDirection(selectTarget(game), figure));
        } else {
            int randomInt = Random.get(0, 8);
            Direction randomDir = values()[randomInt];
//            game.getCollisionBoard().move(figure, randomDir);
            figure.moveTo(game, randomDir);
        }
    }
}
