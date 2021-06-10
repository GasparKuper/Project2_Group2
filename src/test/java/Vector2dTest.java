import Body.Vector.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector2dTest {

    @Test
    void testGetX() {
        Vector2d v = new Vector2d(-1.1, 0.1);
        assertEquals(-1.1, v.getX());
    }

    @Test void testSetX() {
        Vector2d v = new Vector2d();
        v.setX(-1.1);
        assertEquals(-1.1, v.getX());
    }

    @Test void testGetY() {
        Vector2d v = new Vector2d(-1.1, 0.1);
        assertEquals(0.1, v.getY());
    }

    @Test void testSetY() {
        Vector2d v = new Vector2d();
        v.setY(0.1);
        assertEquals(0.1, v.getY());
    }

    @Test void testAddVector3d() {
        Vector2d a = new Vector2d(-1.1, 0.1);
        Vector2d b = new Vector2d( 0.5, 0.6);
        Vector2d ab = a.add(b);
        assertEquals(-1.1+0.5, ab.getX());
        assertEquals( 0.1+0.6, ab.getY());
    }

    @Test void testSub() {
        Vector2d a = new Vector2d(-1.1, 0.1);
        Vector2d b = new Vector2d( 0.5, 0.6);
        Vector2d ab = a.sub(b);
        assertEquals(-1.1-0.5, ab.getX());
        assertEquals( 0.1-0.6, ab.getY());
    }

    @Test void testMul() {
        Vector2d a = new Vector2d(-1.1, 0.1);
        Vector2d b = a.mul(0.5);
        assertEquals(-1.1*0.5, b.getX());
        assertEquals( 0.1*0.5, b.getY());
    }

    @Test void testAddMul() {
        Vector2d a = new Vector2d( 0.6, 0.7);
        Vector2d b = new Vector2d(-1.1, 0.1);
        Vector2d ab = a.addMul(0.5, b);
        assertEquals(0.6 + 0.5*(-1.1), ab.getX());
        assertEquals(0.7 + 0.5*0.1,    ab.getY());
    }

    @Test void testNorm() {
        Vector2d v = new Vector2d(3.0, -4.0);
        assertEquals(5.0, v.norm());
    }

    @Test void testDist() {
        Vector2d a = new Vector2d(3.5, -2.5);
        Vector2d b = new Vector2d(0.5, 1.5);
        assertEquals(5.0, a.dist(b));
    }

    @Test void testToString() {
        Vector2d v = new Vector2d(-1.1, 2.1);
        String stringV = "(-1.1,2.1)";
        assertEquals(stringV, v.toString());
    }
}
