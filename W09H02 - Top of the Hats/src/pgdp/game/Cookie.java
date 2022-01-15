package pgdp.game;

import java.util.List;

public class Cookie extends Entity {
    private Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Cookie(Position pos, Direction direction) {
        super(new LocatedBoundingBox(pos, new BoundingBox(1, 1)));
        this.direction = direction;
    }

    public boolean visit(Game game) {
        if (this.getHitBox().isEmpty()) {
            return false;
        }

        if (game.getCollisionBoard().hasCollisions(this, direction)) {
            List<Entity> collisions = game.getCollisionBoard().getCollisions(this, direction);
            for (Entity collision : collisions) {
                if (!(collision instanceof Cookie) && !(collision instanceof Hat)) {
                    ((Figure) collision).setDisabledCooldown(60);
                    if (((Figure) collision).isHasHat()) {
                        ((Figure) collision).setHasHat(false);
                        game.getEntityAdd().add(new Hat(collision.getHitBox().get().getPosition()));
                    }
                }
            }
            return false;
        } else {
            game.getCollisionBoard().move(this, direction);
            return true;
        }
    }

    @Override
    public Image getImage() {
        return Image.COOKIE;
    }
}
