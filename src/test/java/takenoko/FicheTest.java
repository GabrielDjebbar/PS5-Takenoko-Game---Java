package takenoko;

import org.junit.Test;
import takenoko.player.Fiche;

import static org.junit.Assert.*;

public class FicheTest {


    /**
     * Testing if we get the right name
     */
    @Test
    public void getjName() throws Exception {
        Fiche fiche =new Fiche("player");
        assertTrue("player".equals(fiche.getjName()));
    }


    /**
     * Testing if we set the right name.
     */
    @Test
    public void setjName() throws Exception {
        Fiche fiche =new Fiche("player");
        fiche.setjName("player1");
        assertTrue("player1".equals(fiche.getjName()));
    }


    /**
     * Testing if we initialize score to zero
     */
    @Test
    public void ifScoreInitializedToZero() throws Exception {
        Fiche fiche =new Fiche("player");
        assertTrue(fiche.getScore() == 0);
    }

    /**
     * Testing if we initialize irrigation to zero
     */
    @Test
    public void ifIrrigationInitializedToZero() throws Exception {
        Fiche fiche =new Fiche("player");
        assertTrue(fiche.getNbIrrigations() == 0);
    }

    /**
     * Testing if we set the right score
     */
    @Test
    public void setScore() throws Exception {
        Fiche fiche =new Fiche("player");
        fiche.setScore(2);
        assertTrue(fiche.getScore() == 2);
    }


    /**
     * Testing if addScore works.
     */
    @Test
    public void addScore() throws Exception {
        Fiche fiche =new Fiche("player");
        fiche.addScore(5);
        assertTrue(fiche.getScore() == 5);
    }
}