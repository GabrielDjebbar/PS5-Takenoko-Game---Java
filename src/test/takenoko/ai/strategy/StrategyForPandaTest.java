package takenoko.ai.strategy;

import org.junit.Before;
import org.junit.Test;
import takenoko.controller.Action;
import takenoko.controller.Generator;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.characters.Panda;
import takenoko.inventory.characters.Gardener;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.player.Joueur;

import static org.junit.Assert.*;

public class StrategyForPandaTest {

    @Before

    @Test
    /* test si dans le cas d'une stratégie de type StrategyPanda et pour un objectif Panda de couleurP , le jardinier sera bien déplacer sur
       sur une parcelle irriguée de couleurP*/
    public void deplacerJardinierForObjPanda() throws Exception {

        Panda panda = new Panda();
        Gardener gardener = new Gardener();
        Plateau plateau=new Plateau(panda,gardener);
        Generator generator=new Generator();
        ObjectifsTypePanda obj1 = new ObjectifsTypePanda(0,0,2,5);
        Parcelle parcelle1 = new Parcelle("Pink");
        Parcelle parcelle2 = new Parcelle("Pink");
        Parcelle parcelle3 = new Parcelle("Pink");
        Parcelle parcelle4 = new Parcelle("Green");
        Parcelle parcelle5 = new Parcelle("Green");
        Parcelle parcelle6 = new Parcelle("Yellow");
        // IRRIGUER PARCELLE
        parcelle6.irriguerParcelle();
        //Parcelle parcelle7 = new Parcelle("Yellow");
        //Parcelle parcelle8 = new Parcelle("Pink");
        plateau.addParcelle(-1,-1,parcelle1);
        plateau.addParcelle(1,-1,parcelle2);
        plateau.addParcelle(0,2,parcelle3);
        plateau.addParcelle(1,1,parcelle4);
        plateau.addParcelle(2,0,parcelle5);
        plateau.addParcelle(2,2,parcelle6);
        Action action = new Action(plateau,generator);
        Joueur j=new Joueur("j");
        StrategyForPanda sFP = new StrategyForPanda(plateau,action,j);
        boolean GardenerMoved = sFP.deplacerJardinierForObjPanda(obj1);
        assertTrue(GardenerMoved);
        assertEquals(2,plateau.getGardener().getPosX());
        assertEquals(2,plateau.getGardener().getPosY());
    }

}

