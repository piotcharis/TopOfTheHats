package pgdp.game;

import pgdp.game.helper.PlayerCtl;

import static pgdp.game.Direction.*;

public class PlayerControls implements Controls {
    public void move(Game game, Figure figure) {
        if (getDirection() != null) {
            figure.moveTo(game, getDirection());
        }
    }

    public boolean attack(Game game, Figure figure) {
        return PlayerCtl.isAttack();
    }

    private Direction getDirection() {
        if (PlayerCtl.isUp() && !PlayerCtl.isDown() && !PlayerCtl.isRight() && !PlayerCtl.isLeft()) {
            return UP;
        } else if (!PlayerCtl.isUp() && PlayerCtl.isDown() && !PlayerCtl.isRight() && !PlayerCtl.isLeft()) {
            return DOWN;
        } else if (!PlayerCtl.isUp() && !PlayerCtl.isDown() && PlayerCtl.isRight() && !PlayerCtl.isLeft()) {
            return RIGHT;
        } else if (!PlayerCtl.isUp() && !PlayerCtl.isDown() && !PlayerCtl.isRight() && PlayerCtl.isLeft()) {
            return LEFT;
        } else if (PlayerCtl.isUp() && !PlayerCtl.isDown() && PlayerCtl.isRight() && !PlayerCtl.isLeft()) {
            return UP_RIGHT;
        } else if (PlayerCtl.isUp() && !PlayerCtl.isDown() && !PlayerCtl.isRight() && PlayerCtl.isLeft()) {
            return UP_LEFT;
        } else if (!PlayerCtl.isUp() && PlayerCtl.isDown() && PlayerCtl.isRight() && !PlayerCtl.isLeft()) {
            return DOWN_RIGHT;
        } else if (!PlayerCtl.isUp() && PlayerCtl.isDown() && !PlayerCtl.isRight() && PlayerCtl.isLeft()) {
            return DOWN_LEFT;
        } else if (PlayerCtl.isUp() && !PlayerCtl.isDown() && PlayerCtl.isRight() && PlayerCtl.isLeft()) {
            return UP;
        } else if (!PlayerCtl.isUp() && PlayerCtl.isDown() && PlayerCtl.isRight() && PlayerCtl.isLeft()) {
            return DOWN;
        } else if (PlayerCtl.isUp() && PlayerCtl.isDown() && PlayerCtl.isRight() && !PlayerCtl.isLeft()) {
            return RIGHT;
        } else if (PlayerCtl.isUp() && PlayerCtl.isDown() && !PlayerCtl.isRight() && PlayerCtl.isLeft()) {
            return LEFT;
        }
        return null;
    }
}
