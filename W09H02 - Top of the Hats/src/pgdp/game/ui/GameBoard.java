package pgdp.game.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameBoard extends JComponent {
    private List<Wrapper.ToRender> toRenderList = new ArrayList<>();

    public GameBoard() {
        super();
        var size = new Dimension(800, 450);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    public void render(List<Wrapper.ToRender> toRenderList) {
        Objects.requireNonNull(toRenderList);
        this.toRenderList = toRenderList;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, 799, 449);
        g.setColor(getForeground());
        g.drawRect(0, 0, 799, 449);
        for (
                var entity : toRenderList
        ) {
            g.drawImage(entity.getImage(), entity.getBox().getPosition().getX() * 10,
                    entity.getBox().getPosition().getY() * 10,
                    (i, flags, x, y, w, h) -> (flags & ImageObserver.ALLBITS) != ImageObserver.ALLBITS
            );
        }
    }
}
