package takenoko.ai.strategy;

import org.junit.Before;
import org.junit.Test;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.characters.Gardener;
import takenoko.inventory.characters.Panda;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.player.Fiche;
import takenoko.referee.Possibility;


import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AnalysePandaTest {
    Panda panda  = new Panda();
    Gardener gardener= new Gardener();
    Plateau pl=new Plateau(panda,gardener);
    AnalysePanda analyse;
    Fiche f=new Fiche("Joueur 1");
    ObjectifsTypePanda obj1=new ObjectifsTypePanda(1,1,1,3);
    ObjectifsTypePanda obj2=new ObjectifsTypePanda(0,0,3,3);



    @Before
    public void initialize() {


    }

    @Test
    public void plateauPresqueMulticolor() throws Exception {
        pl.addParcelle(-2, 0, new Parcelle("Green"));
        pl.addParcelle(-1, -1, new Parcelle("Green"));
        Possibility p=new Possibility(pl);
        analyse = new AnalysePanda(obj1,pl,f);
        int[] actual = analyse.getBambousAManger();
        int[] result ={1,1,1};
        assertEquals(result[0], actual[0]);

    }
    @Test
    public void plateauPresqueVideMulti2() throws Exception {
        pl.addParcelle(-2, 0, new Parcelle("Green"));
        pl.addParcelle(-1, -1, new Parcelle("Yellow"));
        pl.getParcelleByCoord(-1,-1).add1Bamboo();
        Possibility p=new Possibility(pl);
        analyse = new AnalysePanda(obj1,pl,f);
        Point actual = analyse.getDirection();
        Point result =new Point(-1,-1);
        assertEquals(result, actual);

    }
}
