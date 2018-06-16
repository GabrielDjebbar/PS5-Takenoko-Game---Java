package takenoko;

import org.junit.Before;
import org.junit.Test;

import takenoko.controller.Action;
import takenoko.controller.Generator;
import takenoko.inventory.board.Amenagement;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.characters.Gardener;
import takenoko.referee.Possibility;
import takenoko.utils.Log;

import java.awt.*;
import java.util.*;

import java.lang.*;
import java.util.List;
import java.util.logging.Level;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class PossibilityTest {
    Plateau pl;
    Generator gn;
    Action act;

    
    @Before
    public void init()
    {
        Log.logger.setLevel(Level.OFF);
        pl=new Plateau();
        gn= new Generator();
        act = new Action(pl,gn);        
    }


    @Test
    public void getListOfParcelByColorTrue() {
        List<Parcelle> listActual = new ArrayList<>();
        pl.addParcelle(-1,1,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        listActual.addAll(pl.getListParcelle());
        listActual.remove(pl.getParcelleByCoord(0,0));
        Possibility poss= new Possibility(pl);
        List<Parcelle> listExepected = poss.getListOfParcelByColor("Green");
        assertTrue(listActual.containsAll(listExepected)&&listExepected.containsAll(listActual));
    }

    /* --------------------- TESTS DE AVAILABLE POSITIONS ---------------------*/

    @Test
    public void availablePositions() {
        
        pl.addParcelle(-1,1,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        pl.addParcelle(2,0,new Parcelle("Green"));
        Possibility possibility=new Possibility(pl);
        Set<Point> actual=new HashSet<Point>();
        Set<Point> expected=new HashSet<Point>(Arrays.asList(new Point(0,2),new Point(3,1),
                new Point(1,-1),new Point(-1,-1),new Point(-2,0)));
        actual=possibility.availablePositions();
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void checkAvailablePosEtang() {
        
        Set<Point> expected = new HashSet<>(Arrays.asList(new Point(2,0),new Point(-2,0),
                new Point(1,1),new Point(1,-1),new Point(-1,1),new Point(-1,-1)));
        Set<Point> actual=new HashSet<>();
        Possibility possibility =new Possibility(pl);
        actual=possibility.checkAvailablePosForPoint(0,0);
        assertTrue(expected.containsAll(actual)&& actual.containsAll(expected));
    }

    @Test
    public void checkAvailablePosEtangWithOneTaken()  {
        
        pl.addParcelle(2,0,new Parcelle("Green"));
        Set<Point> expected = new HashSet<>(Arrays.asList(new Point(-1,1),new Point(-2,0),
                new Point(1,1),new Point(1,-1),new Point(-1,-1)));
        Set<Point> actual=new HashSet<>();
        Possibility possibility =new Possibility(pl);
        actual=possibility.checkAvailablePosForPoint(0,0);
        assertTrue(expected.containsAll(actual)&& actual.containsAll(expected));
    }

    @Test
    public void checkAvailablePosOtherPos()  {
        

        pl.addParcelle(2,0,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        pl.addParcelle(-1,1,new Parcelle("Green"));
        pl.addParcelle(0,2,new Parcelle("Green"));
        Set<Point> expected = new HashSet<>(Arrays.asList(new Point(2,2),
                new Point(3,1)));
        Set<Point> actual=new HashSet<>();
        Possibility possibility =new Possibility(pl);
        actual=possibility.checkAvailablePosForPoint(1,1);
        assertTrue(expected.containsAll(actual)&& actual.containsAll(expected));
    }

    @Test
    public void checkAvailablePos() {
        

        pl.addParcelle(2,0,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        pl.addParcelle(-1,1,new Parcelle("Green"));
        Set<Point> expected = new HashSet<>(Arrays.asList(new Point(0,2),new Point(3,1)));
        Set<Point> actual=new HashSet<>();
        Possibility possibility =new Possibility(pl);
        actual=possibility.checkAvailablePosForPoint(1,1);
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }


    /* ------------------------------- TESTS DE NEIGHBOORS ------------------------*/
    @Test
    public void hasTwoNeighboursEtang()  {


        Possibility possibility =new Possibility(pl);
        Boolean expected= true;
        Boolean actual=possibility.hasTwoNeighbours(new Point(2,0));
        assertEquals(expected,actual);

    }

    @Test
    public void isNeighbourTrue() {
        pl.addParcelle(-1,1,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        pl.addParcelle(2,0,new Parcelle("Green"));
        Possibility possibility=new Possibility(pl);
        possibility.getListOfParcelByColor("Green");
        boolean actual = possibility.isNeighbour(possibility.getParcelleByCoord(2,0),
                possibility.getParcelleByCoord(1,1));
        assertTrue(actual);
    }

    @Test
    public void isNeighbourFalse() {
        pl.addParcelle(-1,1,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        pl.addParcelle(2,0,new Parcelle("Green"));
        Possibility possibility=new Possibility(pl);
        boolean expected = false ;
        boolean actual = possibility.isNeighbour(possibility.getParcelleByCoord(2,0),
                possibility.getParcelleByCoord(-1,1));
        assertEquals(expected,actual);
    }

    @Test
    public void hasTwoNeighboursFalseValue() {


        Possibility possibility =new Possibility(pl);
        Boolean expected= false;
        Boolean actual=possibility.hasTwoNeighbours(new Point(2,2));
        assertEquals(expected,actual);

    }

    @Test
    public void hasTwoNeighboursTrueValue()  {


        pl.addParcelle(1,1,new Parcelle("Green"));
        pl.addParcelle(2,0,new Parcelle("Green"));
        Possibility possibility =new Possibility(pl);
        Boolean expected= true;
        Boolean actual=possibility.hasTwoNeighbours(new Point(3,1));
        assertEquals(expected,actual);

    }

    @Test
    public void hasTwoNeighboursEtangNeighbour()  {



        Possibility possibility =new Possibility(pl);
        Boolean expected= true;
        Boolean actual=possibility.hasTwoNeighbours(new Point(-1,-1));
        assertEquals(expected,actual);

    }

    @Test
    public void getNeighboorParcellebsyCoordTest() {

        Possibility posstest = new Possibility(pl);
        posstest.setPlateaufromPossibility(2,0);
        posstest.setPlateaufromPossibility(1,-1);
        posstest.setPlateaufromPossibility(-1,-1);
        posstest.getPlateauFromPossibility().printListParcelle();
        ArrayList<Parcelle> parcellesResultat = posstest.getNeighboorParcellesByCoord(3,-1);


        Possibility possExpected = new Possibility(pl);
        possExpected.setPlateaufromPossibility(2,0);
        possExpected.setPlateaufromPossibility(1,-1);
        possExpected.getPlateauFromPossibility().printListParcelle();
        ArrayList<Parcelle> parcellesExpected=possExpected.getPlateauFromPossibility().getListParcelle();
        parcellesExpected.remove(0);

        for(int i=0;i<2;i++){
            Parcelle pExpected = parcellesExpected.get(i);
            Parcelle pResultat=parcellesResultat.get(i);
            assertEquals(pResultat.getX(),pExpected.getX());
            assertEquals(pResultat.getY(),pExpected.getY());}
    }

    /* ------------------ TESTS DE MOUVEMENT PANDA ET JARDINIER------------------------*/

    @Test
    public void testListMovementPanda() {
        Possibility posstest = new Possibility(pl);

        // création de 3 parcelles
        Parcelle p11 = new Parcelle("Jaune");
        Parcelle p22 = new Parcelle("Jaune");
        Parcelle p33 = new Parcelle("Jaune");
        Parcelle p55 = new Parcelle("Jaune");
        Parcelle p66 = new Parcelle("Jaune");

        Amenagement am = new Amenagement("nopanda");

        pl.addParcelle(1,1,p11); // déplacemnt possible
        pl.addParcelle(2,2,p22); // déplacement possible

        // parcelle avec aménagement no panda
        pl.addParcelle(-1,-1,p33); // déplacemnt impossible
        p33.setAmenagement(am);

        pl.addParcelle(4,2,p55); // déplacemnt impossible


        ArrayList<Parcelle> ListParcelExpected = new ArrayList<Parcelle>();
        ArrayList<Parcelle> ListParcelGardener = new ArrayList<Parcelle>();
        ListParcelGardener=posstest.possibilityPanda(0,0);
        ListParcelExpected.add(p11);
        ListParcelExpected.add(p22);


        assertTrue(ListParcelGardener.containsAll(ListParcelExpected));
        assertTrue(ListParcelExpected.containsAll(ListParcelGardener));

    }

    @Test
    public void testListMovementGardener() {
        Possibility posstest = new Possibility(pl);

        // création de 3 parcelles
        Parcelle p11 = new Parcelle("Jaune");
        Parcelle p22 = new Parcelle("Jaune");
        Parcelle p33 = new Parcelle("Jaune");
        Parcelle p43 = new Parcelle("Jaune");
        pl.addParcelle(1,1,p11); // la seule qui sera aménageable
        pl.addParcelle(2,2,p22); // ne sera pas aménageble
        pl.addParcelle(-1,-1,p33); // ne sera pas aménageble
        pl.addParcelle(4,2,p43); // ne sera pas aménageble


        ArrayList<Parcelle> ListParcelExpected = new ArrayList<Parcelle>();
        ArrayList<Parcelle> ListParcelGardener = new ArrayList<Parcelle>();
        ListParcelGardener=posstest.possibilityCharacter(0,0);
        ListParcelExpected.add(p11);
        ListParcelExpected.add(p22);
        ListParcelExpected.add(p33);
        for(Parcelle p:ListParcelGardener){
            p.printCoord();
        }
        for(Parcelle p:ListParcelExpected){
            p.printCoord();
        }
        assertTrue(ListParcelGardener.containsAll(ListParcelExpected));

    }

    @Test
    public  void testGardenerIsStoppedByEmptyParcelle(){
        Plateau pL=new Plateau();
        boolean expected = true;
        Parcelle p1 = new Parcelle("Green");
        Parcelle p2 = new Parcelle("Green");
        Parcelle p3 = new Parcelle("Green");
        Parcelle p4 = new Parcelle("Green");
        Parcelle p5 = new Parcelle("Green");
        Parcelle p6 = new Parcelle("Green");
        Parcelle p7 = new Parcelle("Green");

        pL.addParcelle(-3,-1,p1);
        pL.addParcelle(-1,-1,p2);
        pL.addParcelle(1,-1,p3);

        pL.addParcelle(5,-1,p4);
        pL.addParcelle(7,-1,p5);
        pL.addParcelle(-2,0,p6);
        pL.addParcelle(0,2,p7);

        ArrayList<Parcelle> listExpected=new ArrayList<>();
        listExpected.add(p2);
        listExpected.add(p3);
        listExpected.add(p6);

        Possibility possibility =new Possibility(pL);


        for (Parcelle p : possibility.possibilityCharacter(-3,-1)){
            assertTrue(listExpected.contains(p));

        }

        for (Parcelle p : listExpected){
            assertTrue(possibility.possibilityCharacter(-3,-1).contains(p));

        }

    }

    /* ------------------------------- TESTS DE IRRIGATIONS  ----------------------DONE*/

    /**
     * Fonction qui nous renvoit les segments qui sont possibles a irriguer
     */
    @Test
    public void segAIrriguer()  {
        pl.addParcelle(2,0,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));

        Possibility poss = new Possibility(pl);
        poss.majTabSegementsPossible();

        // vérifier si la liste des segments a irriguer est égale à 1
        assertTrue(poss.getListSegementsPossible().size() == 2);
    }

    /**
     * Fonction 2 qui nous renvoit les segments qui sont possibles a irriguer
     */
    @Test
    public void segAIrriguer2()  {
        pl.addParcelle(2,0,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        pl.addParcelle(-1,1,new Parcelle("Green"));

        Possibility poss = new Possibility(pl);
        poss.majTabSegementsPossible();

        // vérifier si la liste des segments a irriguer est égale à 1
        assertTrue(poss.getListSegementsPossible().size() == 4);
    }

    /**
     * Une fonction junit composée de plusieurs mini tests internes
     * pour une parcelle donnée, à chaque fois, on irrigue un point
     * et on regarde si nous avons le bon nombre de segments irriguée
     * (on irrigue une fois, ce qui fait 0 seg irrigué, deux fois, un segment..etc)
     */
    @Test
    public void testSegemntsIrrigues()  {
        init();
        boolean expected = true;

        // parcelle avec 6 seg irrigués
        pl.addParcelle(5,5,new Parcelle("Green"));
        Parcelle p1 = pl.getParcelleByCoord(5,5);
        p1.irriguerSide1();
        p1.irriguerSide2();
        p1.irriguerSide3();
        p1.irriguerSide4();
        p1.irriguerSide5();
        p1.irriguerSide6();
        if(p1.getTabSegIrrigues().size() != 6){
            expected = false;
        }


        // parcelle avec 4 seg irrigués
        pl.addParcelle(8,8,new Parcelle("Green"));
        Parcelle p2 = pl.getParcelleByCoord(8,8);
        p2.irriguerSide1();
        p2.irriguerSide2();
        p2.irriguerSide3();
        p2.irriguerSide4();
        p2.irriguerSide5();
        if(p2.getTabSegIrrigues().size() != 4){
            expected = false;
        }

        // parcelle avec 3 seg irrigués
        pl.addParcelle(-2,-2,new Parcelle("Green"));
        Parcelle p3 = pl.getParcelleByCoord(-2,-2);
        p3.irriguerSide1();
        p3.irriguerSide2();
        p3.irriguerSide3();
        p3.irriguerSide4();
        if(p3.getTabSegIrrigues().size() != 3){
            expected = false;
        }


        // parcelle avec 2 seg irrigués
        pl.addParcelle(10,10,new Parcelle("Green"));
        Parcelle p4 = pl.getParcelleByCoord(10,10);
        p4.irriguerSide1();
        p4.irriguerSide2();
        p4.irriguerSide3();
        if(p4.getTabSegIrrigues().size() != 2){
            expected = false;
        }


        // parcelle avec 1 seg irrigués
        pl.addParcelle(-10,-10,new Parcelle("Green"));
        Parcelle p5 = pl.getParcelleByCoord(-10,-10);
        p5.irriguerSide1();
        p5.irriguerSide2();
        if(p5.getTabSegIrrigues().size() != 1){
            expected = false;
        }

        // parcelle avec 1 seg irrigués
        pl.addParcelle(50,50,new Parcelle("Green"));
        Parcelle p6 = pl.getParcelleByCoord(50,50);
        p6.irriguerSide6();
        p6.irriguerSide1();
        if(p6.getTabSegIrrigues().size() != 1){
            expected = false;
        }

        // parcelle avec 1 seg irrigués
        pl.addParcelle(60,60,new Parcelle("Green"));
        Parcelle p7 = pl.getParcelleByCoord(60,60);
        p7.irriguerSide5();
        p7.irriguerSide6();
        if(p7.getTabSegIrrigues().size() != 1){
            expected = false;
        }




        assertTrue(expected);




    }


    /* ------------------------------- TESTS DE BAMBO -----------------------------DONE*/
    /**
     * Test pour savoir les parcelles ou on peut faire pousser bambo suite à la météo
     */
    @Test
    public void testPossibilityPousseBambo(){

        init();
        boolean expected = true;
        Possibility poss = new Possibility(pl);

        pl.addParcelle(5,5,new Parcelle("Green"));
        pl.addParcelle(7,7,new Parcelle("Green"));

        Parcelle p1 = pl.getParcelleByCoord(5,5);
        Parcelle p2 = pl.getParcelleByCoord(7,7);

        p1.irriguerParcelle();
        // ajoute 5 bambo
        p1.add2Bamboo();
        p1.add1Bamboo();
        p1.add2Bamboo();

        p2.irriguerParcelle();



        // test dans le cas ou on a 4 bambo, si il y aura pas 5
        if(p1.getBamboo()>4){
            expected = false;
        }

        // test si la parcelle est dans la liste
        if(poss.possibilityListPousserBambo().size()!=2){
            expected = false;
        }
        assertTrue(expected);
    }


    /* ------------------------------- TESTS DE AMENAGEMNT -----------------------DONE*/

    /**
     * Test la liste des parcelles ou on peut poser des aménagements
     * - On test les parcelles ayant deja un aménagement (ne doivent pas apparaitre)
     * - On test les parcelles ayant un bambo, qui ne sera pas aménageable
     *
     */
    @Test
    public void testPossiblityAmenagements() {
        ArrayList<Parcelle> actualParcellesAmenegable;
        Boolean expected= true;

        Plateau pltt = new Plateau();
        Possibility po =new Possibility(pltt);

        // création de 3 parcelles
        Parcelle p11 = new Parcelle("Jaune");
        Parcelle p22 = new Parcelle("Jaune");
        Parcelle p33 = new Parcelle("Jaune");
        pltt.addParcelle(5,5,p11); // la seule qui sera aménageable
        pltt.addParcelle(4,4,p22); // ne sera pas aménageble
        pltt.addParcelle(3,3,p33); // ne sera pas aménageble

        // on ne peut poser aménagement sur la p2 parce qu'il y a deja un aménagement
        Amenagement am = new Amenagement("bassin");
        p22.setAmenagement(am);

        // on ne peut poser aménagement sur la p3 parce qu'il y a un bambo
        p33.add1Bamboo();


        // récuperer la liste des parcelles possibles a aménager
        actualParcellesAmenegable = po.possibilityAmenagement();

        // vérifier qu'on a seulement une parcelle aménegable comme prévu
        if(actualParcellesAmenegable.size() != 1){
            expected = false;
        }


        // vérifier si c'est la bonne parcelle
        if(!actualParcellesAmenegable.get(0).equals(p11)){
            expected = false;
        }

        // vérifer si notre variable a été changée
        assertTrue(expected);

    }
}


