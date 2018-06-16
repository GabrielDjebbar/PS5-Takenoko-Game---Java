package takenoko;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import takenoko.ai.RandomBot;
import takenoko.controller.Action;
import takenoko.controller.Generator;
import takenoko.inventory.board.Amenagement;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.board.Segement;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class ParcelleTest {


    static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Generator generator;
    Plateau plateau;
    Action action;
    Joueur j;
    Possibility possibility;

    @Before
    public void initialize() {
        generator = new Generator();
        plateau = new Plateau();
        possibility = new Possibility(plateau);
        action = new Action(plateau, generator);
        j = new Joueur("j1", action, new RandomBot(plateau, action, possibility));
    }


    /* ------------------------------- TESTS DES ORIENTATIONS  ----------------------*/

    @Test
    public void testEnHautGaucheTrue() {
        Parcelle p = new Parcelle("Green");
        plateau.addParcelle(1, -1, p);
        Parcelle pEtang = plateau.getParcelleByCoord(0, 0);
        boolean actual = pEtang.isEnHautGaucheDe(p);
        assertTrue(actual);
    }

    @Test
    public void testEnHautDroiteTrue() {
        Parcelle p = new Parcelle("Green");
        plateau.addParcelle(-1, -1, p);
        Parcelle pEtang = plateau.getParcelleByCoord(0, 0);
        boolean actual = pEtang.isEnHautDroiteDe(p);
        assertTrue(actual);
    }

    @Test
    public void testEnBasDroiteTrue() {
        Parcelle p = new Parcelle("Green");
        plateau.addParcelle(-1, 1, p);
        Parcelle pEtang = plateau.getParcelleByCoord(0, 0);
        boolean actual = pEtang.isEnBasDroiteDe(p);
        assertTrue(actual);
    }

    @Test
    public void testEnBasGaucheTrue() {
        Parcelle p = new Parcelle("Green");
        plateau.addParcelle(1, 1, p);
        Parcelle pEtang = plateau.getParcelleByCoord(0, 0);
        boolean actual = pEtang.isEnBasGaucheDe(p);
        assertTrue(actual);
    }

    @Test
    public void testisAGaucheTrue() {
        Parcelle p = new Parcelle("Green");
        plateau.addParcelle(2, 0, p);
        Parcelle pEtang = plateau.getParcelleByCoord(0, 0);
        boolean actual = pEtang.isAGaucheDeLaParcelleDe(p);
        assertTrue(actual);
    }

    @Test
    public void testisADroiteTrue() {
        Parcelle p = new Parcelle("Green");
        plateau.addParcelle(-2, 0, p);
        Parcelle pEtang = plateau.getParcelleByCoord(0, 0);
        boolean actual = pEtang.isADroiteDeLaParcelleDe(p);
        assertTrue(actual);
    }


    /* ------------------------------- TESTS DE IRRIGATIONS  ----------------------DONE*/

    /* TODO
        ADD JAVA DOC HERE
    */

    @Ignore
    public void debugIrriguePoints() throws Exception {
        Parcelle.logger.setLevel(Level.INFO);

        Parcelle p1 = new Parcelle("Green");
        Parcelle p2 = new Parcelle("Green");
        Parcelle p3 = new Parcelle("Green");
        Parcelle p4 = new Parcelle("Green");
        Parcelle p5 = new Parcelle("Green");
        Parcelle p6 = new Parcelle("Green");


        plateau.addParcelle(2, 0, p1);
        logger.log(Level.FINE, "tests for p1 (2,0) sides if irrigués-----");
        p1.printDataParcelle();
        p1.printDataSegments();


        plateau.addParcelle(1, 1, p6);
        logger.log(Level.FINE, "tests for p6 (1,1) sides if irrigués-----");
        p6.printDataParcelle();
        p6.printDataSegments();

        plateau.addParcelle(-1, 1, p2);
        logger.log(Level.FINE, "tests for p2 (-1,1) sides if irrigués-----");
        p2.printDataParcelle();
        p2.printDataSegments();

        plateau.addParcelle(-2, 0, p3);
        logger.log(Level.FINE, "tests for p3 (-2,0) sides if irrigués-----");
        p3.printDataParcelle();
        p3.printDataSegments();

        plateau.addParcelle(-1, -1, p4);
        logger.log(Level.FINE, "tests for p4 (-1,-1) sides if irrigués-----");
        p4.printDataParcelle();
        p4.printDataSegments();

        plateau.addParcelle(1, -1, p5);
        logger.log(Level.FINE, "tests for p5 (1,-1) sides if irrigués-----");
        p5.printDataParcelle();
        p5.printDataSegments();


        logger.log(Level.FINE, "Liste de parcelles : ");
        plateau.printListParcelle();


        logger.log(Level.FINE, "---------------------------");
        possibility.majTabSegementsPossible();
        logger.log(Level.FINE, "---------------------------");


        boolean actual = (p1.isSideIrrigue(2) && p1.isSideIrrigue(3));
        assertTrue(actual);
    }

    /**
     * Vérifie que la fonction irriguerSide fonctionne correctement
     */
    @Test
    public void testIsSideIrriguer() {
        Parcelle parcelleForTest = new Parcelle("Jaune");
        parcelleForTest.irriguerSide2();
        parcelleForTest.irriguerSide6();
        assertEquals(parcelleForTest.isSideIrrigue(1), false);
        assertEquals(parcelleForTest.isSideIrrigue(2), true);
        assertEquals(parcelleForTest.isSideIrrigue(3), false);
        assertEquals(parcelleForTest.isSideIrrigue(4), false);
        assertEquals(parcelleForTest.isSideIrrigue(5), false);
        assertEquals(parcelleForTest.isSideIrrigue(6), true);
    }

    /**
     * Test qui vérifie que les points correspondants sont bien irrigués :
     * premier test où l'on doit trouvé que si l'on pose une parcelle en (3,-1) à coté d'une parcelle en (1,-1)
     * qui  à ses points 5 et 6 irrigués, on aura les points 3 et 2 de la parcelle (3,-1).
     */
    @Test
    public void testIrriguePtsIfPossible() {
        Parcelle p1 = new Parcelle("Jaune");
        Parcelle p2 = new Parcelle("Jaune");
        plateau.addParcelle(1, -1, p1);
        plateau.addParcelle(2, 0, p2);
        p1.irriguerSide6();
        p1.irriguerSide5();
        Parcelle parcelleForTest = new Parcelle("Jaune");
        plateau.addParcelle(3, -1, parcelleForTest);
        assertEquals(parcelleForTest.isADroiteDeLaParcelleDe(p1), true);
        parcelleForTest.irriguePtsIfPossible(plateau);

        assertEquals(parcelleForTest.isSideIrrigue(2), true);
        assertEquals(parcelleForTest.isSideIrrigue(3), true);
        assertEquals(parcelleForTest.isSideIrrigue(4), false);

        parcelleForTest.majParcelle(plateau);
        assertEquals(parcelleForTest.isParcelleIrrigue(), true);
    }

    /**
     * premier test : Test qui vérifie que si 2 points consécutifs sont irrigués
     * alors la parcelle aura le status Irriguée
     * <p>
     * premier test où l'on doit trouvé que si l'on pose une parcelle en (3,-1) à coté d'une parcelle en (1,-1)
     * qui  à ses points 5 et 6 irrigués, on aura les points 3 et 2 de la parcelle (3,-1) qui seront irrigués aussi
     * et de fait la parcelle en (3,-1) sera irriguée.
     */
    @Test
    public void test1IsParcelleIrriguePossible() {
        Parcelle p1 = new Parcelle("Jaune");
        plateau.addParcelle(1, -1, p1);
        p1.irriguerSide6();
        p1.irriguerSide5();

        Parcelle parcelleForTest = new Parcelle("Jaune");
        plateau.addParcelle(3, -1, parcelleForTest);

        // voir si la parcelle est à droite de l'autre
        assertEquals(parcelleForTest.isADroiteDeLaParcelleDe(p1), true);

        // mise à jour des points de la parcelle
        parcelleForTest.majParcelle(plateau);

        // doit vérifier que la parcelle a été irriguée grâce a sa voisine
        assertEquals(parcelleForTest.isParcelleIrrigue(), true);
    }

    /**
     * @throws Exception troisième test : Aucun points irrigués
     *                   Explication :
     *                   La parcelle étudiée n'a aucun point irrigué et on doit vérifier que cette parcelle ne peut donc pas obtenir le status Irriguée.
     */
    @Test
    public void testParcelleNonIrrigue() throws Exception {
        Parcelle p1 = new Parcelle("Jaune");
        Parcelle p2 = new Parcelle("Jaune");
        plateau.addParcelle(1, -1, p1);
        plateau.addParcelle(2, 0, p2);
        p1.irriguerSide1();
        p2.irriguerSide1();

        Parcelle parcelleForTest = new Parcelle("Jaune");
        plateau.addParcelle(3, -1, parcelleForTest);

        parcelleForTest.majParcelle(plateau);

        assertEquals(parcelleForTest.isParcelleIrrigue(), false);
    }

    @Test
    public void testSegmentsUneParcelle() throws Exception {
        Parcelle p1 = new Parcelle("Green");
        plateau.addParcelle(2, 0, p1);
        p1.irriguerSide1();
        p1.irriguerSide2();


        Set<Integer> set1Expected = new HashSet<>();
        set1Expected.add(1);
        set1Expected.add(6);

        Set<Integer> set2Expected = new HashSet<>();
        set2Expected.add(3);
        set2Expected.add(4);

        Set<Set> setExpected = new HashSet<>();
        setExpected.add(set1Expected);
        setExpected.add(set2Expected);

        Set<Segement> setSegment = p1.getTabSegementsAIrriguer();
        Set<Set> setActual = new HashSet<>();

        for (Segement s : setSegment) {
            setActual.add(s.getSet());
        }

        System.out.println("setActual : " + setActual.toString());
        System.out.println("setExpected : " + setExpected.toString());

        assertTrue(setExpected.size() == setActual.size());
        assertTrue(setExpected.equals(setActual));
    }

    @Test
    public void irriguerSideBesideEtangTrue() {
        Parcelle p = new Parcelle("Green");
        System.out.println("(2,0) : pt 2 irriguée : " + p.isSideIrrigue(2));
        plateau.addParcelle(2, 0, p);
        System.out.println("(2,0) : pt 2 irriguée : " + p.isSideIrrigue(2));
        boolean actual = p.isSideIrrigue(2);
        assertTrue(actual);
    }


    /* ------------------------------- TESTS DE BAMBO -----------------------------DONE*/


    /**
     * --------- Fonction qui test si le pousse de bambo automatique
     * Test si quand on pose à coté de l'étang il y a un bambo qui pousse, et seulement 1
     * Test si les bambo qui sont collés a d'autre parcelles irrigués n'ont pas un bambo
     * Test si les bambo isolés n'ont aucun bambo qui pousse.
     * Test après irrigation de tout les parcelles qu'il y a seulement un bambo auto dans chaque
     */
    @Test
    public void testPousseOneBamboAutomatique() {

        Plateau plateau = new Plateau();
        boolean assertBambo = true;

        // --- LES PARCELLES OU IL DOIT Y AVOIR UN BAMBO AUTOMATIQUE ---

        // Créer 6 parcelles qui seront autour de l'étang
        Parcelle p1 = new Parcelle("Jaune");
        Parcelle p2 = new Parcelle("Jaune");
        Parcelle p3 = new Parcelle("Jaune");
        Parcelle p4 = new Parcelle("Jaune");
        Parcelle p5 = new Parcelle("Jaune");
        Parcelle p6 = new Parcelle("Jaune");
        // placement des 6 parcelles autour de l'étang
        plateau.addParcelle(1, 1, p1);
        plateau.addParcelle(2, 0, p2);
        plateau.addParcelle(1, -1, p3);
        plateau.addParcelle(-1, -1, p4);
        plateau.addParcelle(-2, 0, p5);
        plateau.addParcelle(-1, 1, p6);

        // --- LES PARCELLES OU IL DOIT PAS Y AVOIR UN BAMBO AUTOMATIQUE ---

        // Créer 2 parcelles en (2,2), en (0,2) > un rayon plus loin
        Parcelle p7 = new Parcelle("Jaune");
        Parcelle p8 = new Parcelle("Jaune");
        plateau.addParcelle(0, 2, p7);
        plateau.addParcelle(2, 2, p8);

        // Créer 1 parcelle isolée (5,1)
        Parcelle p9 = new Parcelle("Jaune");
        plateau.addParcelle(5, 1, p9);

        //-----------------------------------------------------

        // initialisation des listes a vérifier
        ArrayList<Parcelle> parcellesOneBambo = new ArrayList<>();
        parcellesOneBambo.add(p1);
        parcellesOneBambo.add(p2);
        parcellesOneBambo.add(p3);
        parcellesOneBambo.add(p4);
        parcellesOneBambo.add(p5);
        parcellesOneBambo.add(p6);

        ArrayList<Parcelle> parcellesWithoutBambo = new ArrayList<>();
        parcellesWithoutBambo.add(p7);
        parcellesWithoutBambo.add(p8);
        parcellesWithoutBambo.add(p9);

        // CONTROLES
        for (Parcelle p : parcellesOneBambo) {
            if (p.getBamboo() != 1) {
                assertBambo = false;
            }
        }

        for (Parcelle p : parcellesWithoutBambo) {
            if (p.getBamboo() != 0) {
                assertBambo = false;
            }
        }


        // controler que tout les parcelles (sauf l'Etang) ont seulement un seul bambo après une irrigation
        for (Parcelle p : plateau.getListParcelle()) {
            p.irriguerParcelle();
            if ((p.getBamboo() != 1) && !(p.getColor().equals("Etang"))) {
                assertBambo = false;
            }
        }
        // checks if assertBambo wasn't changed after an error
        assertTrue(assertBambo);


    }


    /* ------------------------------- TESTS DE AMENAGEMNT -----------------------DONE*/

    /**
     * Test la pose d'un aménagement sur la parcelle
     */
    @Test
    public void testSiAmenagementPose() {
        Amenagement amenagement = new Amenagement("bassin");

        generator = new Generator();
        plateau = new Plateau();
        possibility = new Possibility(plateau);
        action = new Action(plateau, generator);
        j = new Joueur("j1", action, new RandomBot(plateau, action, possibility));
        Parcelle parcelle = new Parcelle("Green");
        plateau.addParcelle(2, 0, parcelle);

        action.poserAmenagement(amenagement, parcelle);

        boolean expected = parcelle.getAmenagement().getType().equals("bassin");

        assertTrue(expected);
    }
}