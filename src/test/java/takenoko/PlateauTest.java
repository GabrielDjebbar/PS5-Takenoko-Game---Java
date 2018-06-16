package takenoko;

import org.junit.Test;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;

import static org.junit.Assert.*;

public class PlateauTest {
    // tests for isEmpty method
    @Test
    public void isEmptyEtang() throws Exception {
        Plateau pl =new Plateau();
        Boolean expected =false;
        Boolean actual = pl.isEmpty(0,0);
        assertEquals(expected,actual);
    }

    @Test
    public void isEmptyNotExists() throws Exception {
        Plateau pl =new Plateau();
        Boolean expected =true;
        Boolean actual = pl.isEmpty(5,3);
        assertEquals(expected,actual);
    }
    @Test
    public void isEmptyNotRandValue() throws Exception {
        int x=4, y=4;
        Plateau pl =new Plateau();
        Boolean expected =false;
        pl.addParcelle(x,y,new Parcelle("Green"));
        Boolean actual = pl.isEmpty(x,y);
        assertEquals(expected,actual);
    }

}