import simulator.model.LogicManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {
    static LogicManager logic = new LogicManager();

    @Test
    public void speedX()  {
        assertEquals(0,  logic.speedX(0, 0));
        assertEquals(-3,  Math.round(logic.speedX(3, -3)));
    }

    @Test
    public void speedY()  {
        assertEquals(0,  logic.speedY(0, 0));
        assertEquals(0,  Math.round(logic.speedY(3, -3)));
    }

    @Test
    public void acceleration()  {
        assertEquals(0,  Math.abs(logic.acceleration(0, 80, 400, 500, 80)));
        assertEquals(-0.0625,  logic.acceleration(4, 80, 400, 500, 80));
    }

    @Test
    public void distance() {
        assertEquals(5, logic.distance(0, 0, 3, 4));
    }

    @Test
    public void timePortation() {
        double x = 200;
        var tpXY = logic.timePortation(1, x, 451.2, 1.25, 2.64, 0.27, 1.33, 6.67, 255);
        assertEquals(214, Math.round(tpXY[0]));
        assertEquals(480, Math.round(tpXY[1]));
        assertEquals(1, Math.round(tpXY[2]));
        assertEquals(3, Math.round(tpXY[3]));
        assertNotEquals(x, tpXY[0], 0.0);
    }
}