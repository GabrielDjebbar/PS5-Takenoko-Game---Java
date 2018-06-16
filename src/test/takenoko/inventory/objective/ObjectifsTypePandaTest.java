package takenoko.inventory.objective;

import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectifsTypePandaTest {
    @Test
    public void ifObjPandaHaveTheRightColorMulticolor() throws Exception {
        ObjectifsTypePanda obj = new ObjectifsTypePanda(1,1,1, 5);
        assertTrue(obj.getColor().equals("multicolor"));
    }

    @Test
    public void ifObjPandaHaveTheRightColorGreen() throws Exception {
        ObjectifsTypePanda obj = new ObjectifsTypePanda(2,0,0, 5);
        assertTrue(obj.getColor().equals("Green"));
    }

    @Test
    public void ifObjPandaHaveTheRightColorPink() throws Exception {
        ObjectifsTypePanda obj = new ObjectifsTypePanda(0,2,0, 5);
        assertTrue(obj.getColor().equals("Pink"));
    }

    @Test
    public void ifObjPandaHaveTheRightColorYellow() throws Exception {
        ObjectifsTypePanda obj = new ObjectifsTypePanda(0,0,2, 5);
        assertTrue(obj.getColor().equals("Yellow"));
    }

    @Test
    public void ifObjPandaHaveTheRightScore() throws Exception {
        ObjectifsTypePanda obj = new ObjectifsTypePanda(1,1,1, 5);
        assertTrue(obj.getNbDePoints() == 5);
    }

}