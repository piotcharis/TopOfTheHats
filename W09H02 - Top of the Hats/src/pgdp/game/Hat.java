package pgdp.game;

public class Hat extends Entity {
    private boolean dontRemove;

    public boolean isDontRemove() {
        return dontRemove;
    }

    public void setDontRemove(boolean dontRemove) {
        this.dontRemove = dontRemove;
    }

    public Hat(Position pos) {
        super(new LocatedBoundingBox(pos, new BoundingBox(2, 1)));
        this.dontRemove = true;
    }

    public boolean visit(Game game) {
        return dontRemove;
    }

    @Override
    public Image getImage() {
        return Image.HAT;
    }
}
