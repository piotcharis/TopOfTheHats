package pgdp.game;

import java.util.List;

public class WolfPingu extends Figure {
    public WolfPingu(Position pos) {
        super(new LocatedBoundingBox(pos, new BoundingBox(3, 3)));
    }

    @Override
    public int getFullAttackCooldown() {
        return 120;
    }

    @Override
    public int getFullMoveCooldown() {
        return 10;
    }

    @Override
    public void attack(Game game) {
        List<Entity> collisions = game.getCollisionBoard().getCollisions(this, getLastDirection());
        attackHelp(game, collisions);
    }

    @Override
    public Image getImage() {
        if (isHasHat()) {
            return Image.WOLF_PINGU_HAT;
        }
        return Image.WOLF_PINGU;
    }
}
