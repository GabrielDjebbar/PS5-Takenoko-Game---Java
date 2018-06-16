package takenoko.ai.strategy;
import org.junit.Before;
import org.junit.Test;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.objective.Objectif;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.referee.Possibility;


import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ScoreObjectifTest {
    Plateau pl = new Plateau();
    AnalyseParcelle analyse;
    ObjectifsTypeParcelle obj1 = new ObjectifsTypeParcelle("Curve", "Green");
    ObjectifsTypeParcelle obj2 = new ObjectifsTypeParcelle("Straight", "Green");
    ObjectifsTypeParcelle obj3 = new ObjectifsTypeParcelle("Triangle", "Green");
    ObjectifsTypeParcelle obj4 = new ObjectifsTypeParcelle("Diamond", "Green");


    public ScoreObjectif initialize(ObjectifsTypeParcelle o) {
        analyse = new AnalyseParcelle(o, pl);
        Possibility possibility = new Possibility(pl);
        return new ScoreObjectif(analyse.getParcellesAIrriguer(), analyse.getParcellesAPoser(), pl, possibility,o.getColor());


    }

    @Test
    public void plateauPresqueVideObjCurve() throws Exception {
        pl.addParcelle(-2, 0, new Parcelle("Green"));
        pl.addParcelle(-1, -1, new Parcelle("Green"));
        analyse = new AnalyseParcelle(obj1, pl);
        ScoreObjectif score = initialize(obj1);
        int result = 1;
        int actual = score.getScore();
        assertEquals(result, actual);
    }
    @Test
    public void plateauPresqueVideObjCurve2() throws Exception {
        pl.addParcelle(-1, 1, new Parcelle("Green"));
        pl.addParcelle(1, -1, new Parcelle("Green"));
        analyse = new AnalyseParcelle(obj1, pl);
        ScoreObjectif score = initialize(obj1);
        int result = 2;
        int actual = score.getScore();
        assertEquals(result, actual);
    }
    @Test
    public void plateauPresqueVideObjCurve3() throws Exception {
        pl.addParcelle(-2, 0, new Parcelle("Yellow"));
        pl.addParcelle(1, -1, new Parcelle("Green"));
        analyse = new AnalyseParcelle(obj1, pl);
        ScoreObjectif score = initialize(obj1);
        int result = 2;
        int actual = score.getScore();
        assertEquals(result, actual);
    }
    @Test
    public void plateauObjTriangle1() throws Exception {
        pl.addParcelle(-2, 0, new Parcelle("Yellow"));
        pl.addParcelle(1, -1, new Parcelle("Pink"));
        pl.addParcelle(-1, 1, new Parcelle("Green"));
        //pl.addParcelle(1, 1, new Parcelle("Green"));
        analyse = new AnalyseParcelle(obj3, pl);
        ScoreObjectif score = initialize(obj3);
        int result =5;
        int actual = score.getScore();
        assertEquals(result, actual);
    }
    @Test
    public void plateauObjTriangle2() throws Exception {
        pl.addParcelle(2, 0, new Parcelle("Yellow"));
        pl.addParcelle(0, 2, new Parcelle("Pink"));
        pl.addParcelle(-1, 1, new Parcelle("Green"));
        pl.addParcelle(-3, 1, new Parcelle("Yellow"));
        pl.addParcelle(1, 1, new Parcelle("Green"));
        pl.addParcelle(1, -1, new Parcelle("Green"));
        //pl.addParcelle(1, 1, new Parcelle("Green"));
        analyse = new AnalyseParcelle(obj3, pl);
        ScoreObjectif score = initialize(obj3);
        int result =5;
        int actual = score.getScore();
        assertEquals(result, actual);
    }
    @Test
    public void plateauObjDiamond() throws Exception {
        pl.addParcelle(2, 0, new Parcelle("Yellow"));
        pl.addParcelle(0, 2, new Parcelle("Pink"));
        pl.addParcelle(-1, 1, new Parcelle("Green"));
        pl.addParcelle(-3, 1, new Parcelle("Yellow"));
        pl.addParcelle(0, -2, new Parcelle("Yellow"));
        pl.addParcelle(1, 1, new Parcelle("Green"));
        //pl.addParcelle(1, 1, new Parcelle("Green"));
        analyse = new AnalyseParcelle(obj4, pl);
        ScoreObjectif score = initialize(obj4);
        int result =10;
        int actual = score.getScore();
        assertEquals(result, actual);
    }


}