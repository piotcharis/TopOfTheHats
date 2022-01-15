package pgdp.game.testing;

import org.junit.jupiter.api.Test;
import pgdp.game.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestFigureVisit {
    @Test
    public void testFigureVisit() {
        CollisionBoard collisionBoard = new CollisionBoard(100, 100, 90, 90);
        Game game = new Game(collisionBoard);
        WolfPingu wolfPingu = new WolfPingu(new Position(50, 50));
        Hamster hamster = new Hamster(new Position(30, 30));
        Hat hat = new Hat(new Position(10, 10));
        Position startPos = wolfPingu.getHitBox().get().getPosition();
        Controls control = new WolfPinguAI();
        game.getCollisionBoard().set(wolfPingu);
        game.getCollisionBoard().set(hamster);
        game.getCollisionBoard().set(hat);
        game.getEntities().add(wolfPingu);
        game.getEntities().add(hamster);
        game.getEntities().add(hat);

        wolfPingu.setControls(control);

        // Falls der DisabledCooldown größer 1 ist, soll der dekrementiert und true zurückgegeben werden.
        wolfPingu.setDisabledCooldown(5);
        assertTrue(wolfPingu.visit(game),
                "Visit gibt bei einer Figur False zurück, obwohl es True zurückgeben sollte.");
        assertEquals(4, wolfPingu.getDisabledCooldown(), "Der disabledCooldown wurde nicht dekrementiert.");

        // Die Cool downs für move und disabled sind 0 und die Figur kann bewegt werden vom AI in Richtung des Huts.
        // Die Methode soll True zurückgeben und den moveCooldown auf den Standard Wert setzen.
        wolfPingu.setDisabledCooldown(0);
        wolfPingu.setMoveCooldown(0);
        wolfPingu.setAttackCooldown(10);

        assertTrue(wolfPingu.visit(game),
                "Visit gibt bei einer Figur False zurück, obwohl es True zurückgeben sollte.");
        assertEquals(wolfPingu.getFullMoveCooldown(), wolfPingu.getMoveCooldown(),
                "Der MoveCooldown wurde nicht wieder zum Standard gesetzt.");
        assertEquals(49, wolfPingu.getHitBox().get().getPosition().getX(), "Die Figur wurde nicht oder falsch bewegt.");
        assertEquals(49, wolfPingu.getHitBox().get().getPosition().getY(), "Die Figur wurde nicht oder falsch bewegt.");

        // Falls der AttackCooldown größer 0 ist, soll es dekrementiert werden und weil die Figur ein WolfPingu ist
        // und mit einer Attacke keine Figur treffen wurde, wird der Cooldown nicht wieder auf dem Standard gesetzt,
        // sondern bleibt bei 0.
        wolfPingu.setAttackCooldown(1);

        assertTrue(wolfPingu.visit(game),
                "Visit gibt bei einer Figur False zurück, obwohl es True zurückgeben sollte.");
        assertEquals(0, wolfPingu.getAttackCooldown(),
                "attackCooldown wurde nicht dekrementiert oder zum Standard Wert gesetzt obwohl das nicht gemacht werden sollte.");

    }
}
