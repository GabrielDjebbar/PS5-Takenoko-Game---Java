package takenoko;

import org.junit.Before;
import org.junit.Test;
import takenoko.ai.RandomBot;
import takenoko.controller.Action;
import takenoko.controller.Generator;
import takenoko.inventory.objective.ObjectifsTypeJardinier;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.player.Fiche;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;
import takenoko.referee.Validation;
import takenoko.utils.Log;

import java.util.logging.Level;

import static org.junit.Assert.*;

public class ValidationTest {
    
    Plateau pl;
    Generator gn;
    Possibility poss;
    Action act;
    
    @Before
    public void initialize(){
        pl=new Plateau();
        gn=new Generator();
        poss= new Possibility(pl);
        act=new Action(pl,gn);
    }



    // ---------------------- TESTS VALIDATION OBJECTIF PANDA --------------------------  //

    /**
     * Test le cas que l'objectif bambo vert n'est pas validé
     */
    @Test
    public void checkValidationNoObjPandaValidated() {
        ObjectifsTypePanda obj =new ObjectifsTypePanda(2,0,0,5);
        Joueur j= new Joueur("j1",act,new RandomBot(pl,act,poss));
        j.getjFiche().addObjectifTypePanda(obj);
        Validation vald =new Validation(pl);
        vald.checkValidation(j.getjFiche());
        boolean expected = (!obj.IsValidated());
        assertTrue(expected);
    }

    /**
     * Vérifier le fonctionnement du processus de validation de l'objectif bambo vert
     * - vérifier la validation de l'objectif bambo
     * - vérifier que les bambo concernés ont été retirés après le processus
     * - vérifier que le score est bien incrémenté
     */
    @Test
    public void checkValidationObjPandaGreen()  {
        Joueur j= new Joueur("j1",act,new RandomBot(pl,act,poss));

        // validation objectif 2 green bambo
        ObjectifsTypePanda obj =new ObjectifsTypePanda(2,0,0,5);
        j.getjFiche().addObjectifTypePanda(obj);
        j.getjFiche().addGreenBamboToFiche();
        j.getjFiche().addGreenBamboToFiche();

        // lancer le processus de validation
        Validation vald =new Validation(pl);
        vald.checkValidation(j.getjFiche());

        // vérifier que l'objectif a bien été validé
        boolean expected = obj.IsValidated();

        // vérifier que les bambo ont bien été consommés en gardant le premier boolean.
        expected = expected && (j.getjFiche().getNbGreenBambo() == 0) && (j.getjFiche().getScore() == 5);

        // vérifier si notre variable expected est toujours vraie
        assertTrue(expected);
    }

    /**
     * Vérifier le fonctionnement du processus de validation de l'objectif bambo rose
     * - vérifier la validation de l'objectif bambo
     * - vérifier que les bambo concernés ont été retirés après le processus
     * - vérifier que le score est bien incrémenté
     */
    @Test
    public void checkValidationObjPandaPink()  {
        Joueur j= new Joueur("j1",act,new RandomBot(pl,act,poss));

        // validation objectif 2 pink bambo
        ObjectifsTypePanda obj =new ObjectifsTypePanda(0,2,0,5);
        j.getjFiche().addObjectifTypePanda(obj);
        j.getjFiche().addPinkBamboToFiche();
        j.getjFiche().addPinkBamboToFiche();

        // lancer le processus de validation
        Validation vald =new Validation(pl);
        vald.checkValidation(j.getjFiche());

        // vérifier que l'objectif a bien été validé
        boolean expected = obj.IsValidated();

        // vérifier que les bambo ont bien été consommés en gardant le premier boolean.
        expected = expected && (j.getjFiche().getNbPinkBambo() == 0) && (j.getjFiche().getScore() == 5);

        // vérifier si notre variable expected est toujours vraie
        assertTrue(expected);
    }

    /**
     * Vérifier le fonctionnement du processus de validation de l'objectif bambo jaune
     * - vérifier la validation de l'objectif bambo
     * - vérifier que les bambo concernés ont été retirés après le processus
     * - vérifier que le score est bien incrémenté
     */
    @Test
    public void checkValidationObjPandaYellow()  {
        Joueur j= new Joueur("j1",act,new RandomBot(pl,act,poss));

        // validation objectif 2 pink bambo
        ObjectifsTypePanda obj =new ObjectifsTypePanda(0,0,2,5);
        j.getjFiche().addObjectifTypePanda(obj);
        j.getjFiche().addYellowBamboToFiche();
        j.getjFiche().addYellowBamboToFiche();

        // lancer le processus de validation
        Validation vald =new Validation(pl);
        vald.checkValidation(j.getjFiche());

        // vérifier que l'objectif a bien été validé
        boolean expected = obj.IsValidated();

        // vérifier que les bambo ont bien été consommés et le score est monté en gardant le premier boolean.
        expected = expected && (j.getjFiche().getNbYellowBambo() == 0) && (j.getjFiche().getScore() == 5);

        // vérifier si notre variable expected est toujours vraie
        assertTrue(expected);
    }

    /**
     * Vérifier le fonctionnement du processus de validation de l'objectif bambo jaune
     * - vérifier la validation de l'objectif bambo
     * - vérifier que les bambo concernés ont été retirés après le processus
     * - vérifier que le score est bien incrémenté
     */
    @Test
    public void checkValidationObjPandaMulticolor()  {
        Joueur j= new Joueur("j1",act,new RandomBot(pl,act,poss));

        // validation objectif 2 pink bambo
        ObjectifsTypePanda obj =new ObjectifsTypePanda(1,1,1,10);
        j.getjFiche().addObjectifTypePanda(obj);
        j.getjFiche().addYellowBamboToFiche();
        j.getjFiche().addPinkBamboToFiche();
        j.getjFiche().addGreenBamboToFiche();

        // lancer le processus de validation
        Validation vald =new Validation(pl);
        vald.checkValidation(j.getjFiche());

        // vérifier que l'objectif a bien été validé
        boolean expected = obj.IsValidated();


        // vérifier que les bambo ont bien été consommés et le score est monté en gardant le premier boolean.
        expected = expected && (j.getjFiche().getScore() == 10);
        expected = expected && (j.getjFiche().getNbGreenBambo() == 0);
        expected = expected && (j.getjFiche().getNbPinkBambo() == 0);
        expected = expected && (j.getjFiche().getNbYellowBambo() == 0);

        // vérifier si notre variable expected est toujours vraie
        assertTrue(expected);
    }



    // ---------------------- TESTS VALIDATION OBJECTIF PARCELLE  --------------------------  //


    /* TODO : Javadoc + Doc */
    @Test
    public void checkValidationNoObjParcelleValidated()  {
        ObjectifsTypeParcelle obj =new ObjectifsTypeParcelle("Diamond","green");
        Joueur j= new Joueur("j1",act,new RandomBot(pl,act,poss));
        j.getjFiche().addObjectifTypeParcelle(obj);
        Validation vald =new Validation(pl);
        vald.checkValidation(j.getjFiche());
        int expected =0;
        int actual = j.getNbObjValider();
        assertEquals(expected,actual);
    }

    /* TODO : Javadoc + Doc */
    @Test
    public void checkValidation1ObjParcelleValidated()  {

        ObjectifsTypeParcelle obj =new ObjectifsTypeParcelle("Diamond","Green");
        Joueur j= new Joueur("j1",act,new RandomBot(pl,act,poss));
        j.getjFiche().addObjectifTypeParcelle(obj);
        Validation vald =new Validation(pl);
        pl.addParcelle(2,0,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        pl.addParcelle(3,1,new Parcelle("Green"));
        pl.addParcelle(2,2,new Parcelle("Green"));
        for(Parcelle p : pl.getListParcelle()){
            p.irriguerParcelle();
        }
        vald.checkValidation(j.getjFiche());
        int expected =1;
        int actual = j.getNbObjValider();
        assertEquals(expected,actual);
    }

    /* TODO : Javadoc + Doc */
    @Test
    public void checkValidation4ObjParcelleValidated()  {

        ObjectifsTypeParcelle obj =new ObjectifsTypeParcelle("Diamond","Green");
        ObjectifsTypeParcelle obj2 =new ObjectifsTypeParcelle("Curve","Pink");
        ObjectifsTypeParcelle obj3 =new ObjectifsTypeParcelle("Triangle","Green");
        ObjectifsTypeParcelle obj4 =new ObjectifsTypeParcelle("Straight","Yellow");
        Joueur j= new Joueur("j1",act,new RandomBot(pl,act,poss));
        j.getjFiche().addObjectifTypeParcelle(obj);
        j.getjFiche().addObjectifTypeParcelle(obj2);
        j.getjFiche().addObjectifTypeParcelle(obj3);
        j.getjFiche().addObjectifTypeParcelle(obj4);
        Validation vald =new Validation(pl);
        pl.addParcelle(2,0,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        pl.addParcelle(2,2,new Parcelle("Green"));
        pl.addParcelle(3,1,new Parcelle("Green"));
        pl.addParcelle(-2,0,new Parcelle("Pink"));
        pl.addParcelle(-3,1,new Parcelle("Pink"));
        pl.addParcelle(-2,2,new Parcelle("Pink"));
        pl.addParcelle(-1,-1,new Parcelle("Yellow"));
        pl.addParcelle(-2,-2,new Parcelle("Yellow"));
        pl.addParcelle(-3,-3,new Parcelle("Yellow"));
        pl.addParcelle(-1,1,new Parcelle("Yellow"));
        pl.addParcelle(0,2,new Parcelle("Yellow"));
        pl.addParcelle(1,-1,new Parcelle("Yellow"));
        pl.addParcelle(0,-2,new Parcelle("Yellow"));
        pl.addParcelle(-1,-3,new Parcelle("Yellow"));
        for(Parcelle p : pl.getListParcelle()){
            p.irriguerParcelle();
        }


        vald.checkValidation(j.getjFiche());
        int expected =4;
        int actual = j.getNbObjValider();
        assertEquals(expected,actual);
    }

    /* TODO : Javadoc + Doc */
    @Test
    public void checkValidationParcelWithOrientation() {

        ObjectifsTypeParcelle obj =new ObjectifsTypeParcelle("Straight","Green");
        Joueur j= new Joueur("j1",act,new RandomBot(pl,act,poss));
        j.getjFiche().addObjectifTypeParcelle(obj);
        Validation vald =new Validation(pl);
        pl.addParcelle(3,1,new Parcelle("Green"));
        pl.addParcelle(-1,1,new Parcelle("Green"));
        pl.addParcelle(1,1,new Parcelle("Green"));
        for(Parcelle p : pl.getListParcelle()){
            p.irriguerParcelle();
        }
        vald.checkValidation(j.getjFiche());
        int expected =1;
        int actual = j.getNbObjValider();
        assertEquals(expected,actual);
    }


    // ---------------------- TESTS VALIDATION OBJECTIF GARDENER  --------------------------  //

    /**
     * verify if with the right number of bamboo,the right lenghth and the right color the objectiv is validated
     * and if the right number of points were given to the player.
     */
    @Test
    public void  checkValidationObjectivGardener(){
        ObjectifsTypeJardinier obj = new ObjectifsTypeJardinier("Green",3,3);
        Joueur j1 = new Joueur("NumberOne");
        j1.addGivenObjectifJardinierToFiche(obj);
        Plateau pL = new Plateau();
        Parcelle p1 = new Parcelle("Green");
        Parcelle p2 = new Parcelle("Green");
        Parcelle p3 = new Parcelle("Green");
        pL.addParcelle(1,1,p1);
        pL.addParcelle(1,2,p2);
        pL.addParcelle(1,2,p3);
        p1.setBamboo(3);
        p2.setBamboo(3);
        p3.setBamboo(3);
        Validation validation1 = new Validation(pL);
        validation1.checkValidation(j1.getjFiche());
        assertTrue(obj.IsValidated());
        assertEquals(j1.getjFiche().getScore(),7);
    }

    /**
     * verify if with the right number of bamboo,the right lenghth and the wrong color the objectiv is not validated
     */
    @Test
    public void  checkValidationWrongColorObjectivGardener(){
        ObjectifsTypeJardinier obj = new ObjectifsTypeJardinier("Pink",3,3);
        Joueur j1 = new Joueur("NumberOne");
        j1.addGivenObjectifJardinierToFiche(obj);
        Plateau pL = new Plateau();
        Parcelle p1 = new Parcelle("Green");
        Parcelle p2 = new Parcelle("Green");
        Parcelle p3 = new Parcelle("Green");
        pL.addParcelle(1,1,p1);
        pL.addParcelle(1,2,p2);
        pL.addParcelle(1,2,p3);
        p1.setBamboo(3);
        p2.setBamboo(3);
        p3.setBamboo(3);
        Validation validation1 = new Validation(pL);
        validation1.checkValidation(j1.getjFiche());
        assertFalse(obj.IsValidated());
    }
    /**
     * verify if with the right number of bamboo,the wrong length and the right color the objectiv is not validated
     */
    @Test
    public void  checkValidationWrongLengthObjectivGardener(){
        ObjectifsTypeJardinier obj = new ObjectifsTypeJardinier("Green",3,3);
        Joueur j1 = new Joueur("NumberOne");
        j1.addGivenObjectifJardinierToFiche(obj);
        Plateau pL = new Plateau();
        Parcelle p1 = new Parcelle("Green");
        Parcelle p2 = new Parcelle("Green");
        Parcelle p3 = new Parcelle("Green");
        pL.addParcelle(1,1,p1);
        pL.addParcelle(1,2,p2);
        pL.addParcelle(1,2,p3);
        p1.setBamboo(3);
        p2.setBamboo(2);
        p3.setBamboo(3);
        Validation validation1 = new Validation(pL);
        validation1.checkValidation(j1.getjFiche());
        assertFalse(obj.IsValidated());
    }

    /**
     * verify if with the wrong number of bamboo,the right lenghth and the right color the objectiv is not validated
     */
    @Test
    public void  checkValidationObjectivGardenerWrongNumber(){
        ObjectifsTypeJardinier obj = new ObjectifsTypeJardinier("Green",3,3);
        Joueur j1 = new Joueur("NumberOne");
        j1.addGivenObjectifJardinierToFiche(obj);
        Plateau pL = new Plateau();
        Parcelle p1 = new Parcelle("Green");
        Parcelle p2 = new Parcelle("Green");
        Parcelle p3 = new Parcelle("Green");
        pL.addParcelle(1,1,p1);
        pL.addParcelle(1,2,p2);
        pL.addParcelle(1,2,p3);
        p1.setBamboo(3);
        p3.setBamboo(3);
        Validation validation1 = new Validation(pL);
        validation1.checkValidation(j1.getjFiche());
        assertFalse(obj.IsValidated());
    }
}