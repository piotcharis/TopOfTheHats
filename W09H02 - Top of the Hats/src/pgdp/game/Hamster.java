package pgdp.game;

import java.util.List;

public class Hamster extends Figure {

    public Hamster(Position pos) {
        super(new LocatedBoundingBox(pos, new BoundingBox(3, 3)));
    }

    @Override
    public int getFullAttackCooldown() {
        return 140;
    }

    @Override
    public int getFullMoveCooldown() {
        return 15;
    }

    @Override
    public void attack(Game game) {
        List<Entity> collisions = game.getCollisionBoard().getCollisions(this, getLastDirection());

        if (collisions.size() == 0) {
            game.getEntityAdd().add(new Cookie(getAttackPosition(), getLastDirection()));
        } else {
            attackHelp(game, collisions);
        }
    }

    private Position getAttackPosition() {
        Position figurePos = this.getHitBox().get().getPosition();

        return switch (getLastDirection()) {
            case DOWN -> new Position(figurePos.getX() + 1, figurePos.getY() + 3);
            case RIGHT -> new Position(figurePos.getX() + 3, figurePos.getY() + 1);
            case LEFT -> new Position(figurePos.getX() - 1, figurePos.getY() + 1);
            case UP_RIGHT -> new Position(figurePos.getX() + 3, figurePos.getY() - 1);
            case UP_LEFT -> new Position(figurePos.getX() - 1, figurePos.getY() - 1);
            case DOWN_RIGHT -> new Position(figurePos.getX() + 3, figurePos.getY() + 3);
            case DOWN_LEFT -> new Position(figurePos.getX() - 1, figurePos.getY() + 3);
            default -> new Position(figurePos.getX() + 1, figurePos.getY() - 1);
        };
    }

    @Override
    public Image getImage() {
        if (isHasHat()) {
            return Image.HAMSTER_HAT;
        }
        return Image.HAMSTER;
    }
}
