package pgdp.game.testing;

import org.junit.jupiter.api.Test;
import pgdp.game.*;

import static org.junit.jupiter.api.Assertions.*;
import static pgdp.game.Direction.*;

public class TestHamsterAttack {
    @Test
    public void testHamsterAttack() {
        CollisionBoard collisionBoard = new CollisionBoard(100, 100, 90, 90);
        Game game = new Game(collisionBoard);
        Hamster hamster = new Hamster(new Position(50, 50));
        WolfPingu wolf = new WolfPingu(new Position(50, 53));
        hamster.setLastDirection(DOWN);
        wolf.setHasHat(true);

        game.getCollisionBoard().set(wolf);
        game.getCollisionBoard().set(hamster);
        game.getEntities().add(wolf);
        game.getEntities().add(hamster);

        // Hamster kollidiert bei einer Bewegung in Richtung lastDirection mit dem WolfPingu und der
        // disabledCooldown für den WolfPingu soll auf 60 gesetzt werden und weil er einen Hut hat soll der Hut
        // von ihm genommen werden.
        hamster.attack(game);
        assertEquals(60, wolf.getDisabledCooldown(), "Der disabledCooldown des WolfPingus wurde nicht auf 60 gesetzt.");
        assertFalse(wolf.isHasHat(), "Der Hut des WolfPingus wurde nicht entfernt.");
        assertTrue(hamster.isHasHat(), "Der Hamster hat den Hut des WolfPingus nicht genommen.");

        // Hamster kollidiert bei einer Bewegung in Richtung lastDirection mit dem WolfPingu und der disabledCooldown
        // für den WolfPingu soll auf 60 gesetzt werden, aber der Hamster hat schon einen Hut und nimmt deswegen
        // den Hut vom WolfPingu nicht weg.
        wolf.setDisabledCooldown(0);
        wolf.setHasHat(true);
        hamster.setHasHat(true);

        hamster.attack(game);
        assertEquals(60, wolf.getDisabledCooldown(), "Der disabledCooldown des WolfPingus wurde nicht auf 60 gesetzt.");
        assertTrue(wolf.isHasHat(), "Der Hut des WolfPingus wurde entfernt, obwohl der Hamster schon einen hat.");
        assertTrue(hamster.isHasHat(), "Der Hut des Hamsters wurde entfernt, ohne Grund.");

        // Hamster kollidiert mit keine Figuren und es soll deswegen ein Cookie in Richtung
        // lastDirection geworfen werden (an der entsprechenden Position laut Aufgabe).
        wolf.getHitBox().get().setPosition(new Position(20, 20));
        hamster.attack(game);

        assertTrue(game.getEntityAdd().get(0) instanceof Cookie, "Es wurde kein Cookie erzeugt.");
        assertEquals(53, game.getEntityAdd().get(0).getHitBox().get().getPosition().getY(),
                "Die Y-Koordinate der Position des Cookies ist falsch.");
        assertEquals(51, game.getEntityAdd().get(0).getHitBox().get().getPosition().getX(),
                "Die X-Koordinate der Position des Cookies ist falsch.");
    }
}
