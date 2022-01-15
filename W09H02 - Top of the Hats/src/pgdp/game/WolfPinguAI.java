package pgdp.game;

import java.util.List;

public class WolfPinguAI extends AIControls {
    public boolean attack(Game game, Figure figure) {
        List<Entity> collisions = game.getCollisionBoard().getCollisions(figure, figure.getLastDirection());
        return collisions.size() != 0;
    }
}
