package takenoko.ai.strategy;
import org.junit.Before;
import org.junit.Test;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.objective.ObjectifsTypeParcelle;


import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AnalyseParcelleTest {
    Plateau pl=new Plateau();
    AnalyseParcelle analyse;
    ObjectifsTypeParcelle obj1 =new ObjectifsTypeParcelle("Curve","Green");
    ObjectifsTypeParcelle obj2 =new ObjectifsTypeParcelle("Straight","Green");
    ObjectifsTypeParcelle obj3 =new ObjectifsTypeParcelle("Triangle","Green");
    ObjectifsTypeParcelle obj4 = new ObjectifsTypeParcelle("Diamond", "Green");


    @Before
    public void initialize() {



    }
    @Test
    public void plateauPresqueVideObjCurve() throws Exception {
        pl.addParcelle(-2,0,new Parcelle("Green"));
        pl.addParcelle(-1,-1,new Parcelle("Green"));
        analyse=new AnalyseParcelle(obj1,pl);
        ArrayList<Point> result=new ArrayList<>();
        result.add(new Point(-1,1));
        ArrayList<Point> actual=analyse.getParcellesAPoser();
        //int actual=analyse.superposeObjectifSurPoint(new Point(-2,0));
        assertEquals(result,actual);
    }
    @Test
    public void plateauPresqueVideObjCurve2() throws Exception {
        pl.addParcelle(-2,0,new Parcelle("Green"));
        pl.addParcelle(-2,-2,new Parcelle("Green"));
        analyse=new AnalyseParcelle(obj1,pl);
        ArrayList<Point> result=new ArrayList<>();
        result.add(new Point(-1,-1));
        ArrayList<Point> actual=analyse.getParcellesAPoser();
        //int actual=analyse.superposeObjectifSurPoint(new Point(-2,0));
        assertEquals(result,actual);
    }
    @Test
    public void plateauPresqueVideObjCurve3() throws Exception {
        pl.addParcelle(-2,0,new Parcelle("Green"));
        pl.addParcelle(-4,-2,new Parcelle("Green"));
        analyse=new AnalyseParcelle(obj1,pl);
        ArrayList<Point> result=new ArrayList<>();
        result.add(new Point(-1,1));
        result.add(new Point(-1,-1));
        ArrayList<Point> actual=analyse.getParcellesAPoser();
        //int actual=analyse.superposeObjectifSurPoint(new Point(-2,0));
        assertEquals(result,actual);
    }
    @Test
    public void plateauPresqueVideObjCurve4() throws Exception {
        pl.addParcelle(-3,1,new Parcelle("Green"));
        pl.addParcelle(-2,2,new Parcelle("Green"));
        analyse=new AnalyseParcelle(obj1,pl);
        ArrayList<Point> result=new ArrayList<>();
        result.add(new Point(-2,0));
        ArrayList<Point> actual=analyse.getParcellesAPoser();
        //int actual=analyse.superposeObjectifSurPoint(new Point(-2,0));
        assertEquals(result,actual);
    }
    @Test
    public void plateauPresqueVideObjStraight() throws Exception {
        pl.addParcelle(-1,-1,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        analyse=new AnalyseParcelle(obj2,pl);
        ArrayList<Point> result=new ArrayList<>();
        result.add(new Point(-2,0));
        result.add(new Point(0,-2));
        ArrayList<Point> actual=analyse.getParcellesAPoser();
        //int actual=analyse.superposeObjectifSurPoint(new Point(-2,0));
        assertEquals(result,actual);
    }
    @Test
    public void triangeVertSurPlateauPresqueToutJaune() throws Exception {
        pl.addParcelle(-1,1,new Parcelle("Yellow"));
        pl.addParcelle(-2,0,new Parcelle("Yellow"));
        pl.addParcelle(-1,-1,new Parcelle("Yellow"));
        pl.addParcelle(1,-1,new Parcelle("Yellow"));
        pl.addParcelle(2,0,new Parcelle("Yellow"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        pl.addParcelle(3,1,new Parcelle("Green"));
        analyse=new AnalyseParcelle(obj3,pl);
        ArrayList<Point> result=new ArrayList<>();
        //result.add(new Point(3,1));
        result.add(new Point(2,2));
        ArrayList<Point> actual=analyse.getParcellesAPoser();
        //int actual=analyse.superposeObjectifSurPoint(new Point(-2,0));
        assertEquals(result,actual);
    }
    @Test
    public void plateauObjTriangle1() throws Exception {
        pl.addParcelle(-2, 0, new Parcelle("Yellow"));
        pl.addParcelle(1, -1, new Parcelle("Pink"));
        pl.addParcelle(-1, 1, new Parcelle("Green"));
        analyse=new AnalyseParcelle(obj3,pl);
        ArrayList<Point> result=new ArrayList<>();
        result.add(new Point(0,2));
        result.add(new Point(1,1));
        ArrayList<Point> actual=analyse.getParcellesAPoser();
        assertEquals(result,actual);
    }
    @Test
    public void plateauObjTriangle2() throws Exception {
        pl.addParcelle(-2, 0, new Parcelle("Yellow"));
        pl.addParcelle(1, -1, new Parcelle("Pink"));
        pl.addParcelle(-1, 1, new Parcelle("Green"));
        pl.addParcelle(1, 1, new Parcelle("Pink"));
        analyse=new AnalyseParcelle(obj3,pl);
        ArrayList<Point> result=new ArrayList<>();
        result.add(new Point(-2,2));
        result.add(new Point(0,2));
        ArrayList<Point> actual=analyse.getParcellesAPoser();
        assertEquals(result,actual);
    }
    @Test
    public void plateauObjDiamond() throws Exception {
        pl.addParcelle(-1, 1, new Parcelle("Green"));
        analyse=new AnalyseParcelle(obj4,pl);
        ArrayList<Point> result=new ArrayList<>();
        result.add(new Point(-2,2));
        result.add(new Point(0,2));
        result.add(new Point(1,1));

        ArrayList<Point> actual=analyse.getParcellesAPoser();
        assertEquals(result,actual);
    }
}


