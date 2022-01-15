package pgdp.game.testing;

import org.junit.jupiter.api.Test;
import pgdp.game.*;

import static org.junit.jupiter.api.Assertions.*;
import static pgdp.game.Direction.*;

public class TestCookieVisit {
    @Test
    public void testCookieVisit() {
        CollisionBoard collisionBoard = new CollisionBoard(100, 100, 90, 90);
        Game game = new Game(collisionBoard);

        // Cookie kollidiert mit einer Entity und es soll am Ende False zurückgegeben werden und außerdem das
        // disabledCooldown der Figur auf 60 gesetzt werden
        Cookie cookie = new Cookie(new Position(50, 50), DOWN);
        WolfPingu wolfPingu = new WolfPingu(new Position(50, 51));
        game.getCollisionBoard().set(cookie);
        game.getCollisionBoard().set(wolfPingu);

        assertFalse(cookie.visit(game), "Visit gibt bei Cookie True zurück, obwohl es False zurückgeben sollte.");
        assertEquals(wolfPingu.getDisabledCooldown(), 60, "Der DisabledCooldown wurde nicht oder falsch gesetzt.");

        // Der Cookie kollidiert mit einer Figur, die einen Hut trägt, damit soll zusätzlich der hasHat der Figur auf False
        // gesetzt werden und ein neuer Hut im Board hinzugefügt werden
        wolfPingu.setHasHat(true);
        assertFalse(cookie.visit(game), "Visit gibt bei Cookie True zurück, obwohl es False zurückgeben sollte.");
        assertFalse(wolfPingu.isHasHat(), "Der Hut der Figur wurde nach der Kollision nicht entfernt");

        Position expected = wolfPingu.getHitBox().get().getPosition();

        assertTrue(game.getEntityAdd().get(0) instanceof Hat, "Die erzeugte Entity ist kein Hut");
        assertEquals(expected, game.getEntityAdd().get(0).getHitBox().get().getPosition(),
                "Der Hut wurde an der falschen Position erzeugt.");

        // Es gibt keine Kollision und der Cookie soll nach unten bewegt werden
        game.getCollisionBoard().remove(wolfPingu);

        assertTrue(cookie.visit(game));
        assertEquals(50, cookie.getHitBox().get().getPosition().getX(),
                "Die X-Koordinate des Cookies ist nach der Bewegung falsch");
        assertEquals(51, cookie.getHitBox().get().getPosition().getY(),
                "Die Y-Koordinate des Cookies ist nach der Bewegung falsch");
    }
}
