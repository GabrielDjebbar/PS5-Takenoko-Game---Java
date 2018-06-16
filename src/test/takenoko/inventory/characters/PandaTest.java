package takenoko.inventory.characters;

import org.junit.Test;
import takenoko.ai.RandomBot;
import takenoko.controller.Action;
import takenoko.controller.Generator;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;

import static org.junit.Assert.*;

public class PandaTest {

    Possibility poss = new Possibility();
    Plateau plateau = new Plateau();
    Generator generator = new Generator();
    Action act = new Action(plateau, generator);
    Panda panda = new Panda();
    Joueur j = new Joueur("j1",act,new RandomBot(plateau,act,poss));
    boolean assertBambo = true;


    /**
     * Vérifie le déplacement du panda, mange et vérifie que les bambo sur les parcelles ont bien été mangés
     * et vérifie que les bambos mangés ont été ajoutés à la fiche du joueur concerné
     */
        @Test
        public void checkPandaEatingProcess() {

            // Créer 5 parcelle en ligne ou le panda se déplacera
            Parcelle p1 = new Parcelle("Jaune");
            Parcelle p2 = new Parcelle("Jaune");
            Parcelle p3 = new Parcelle("Jaune");
            Parcelle p4 = new Parcelle("Jaune");
            Parcelle ptDepart = new Parcelle("Jaune");

            plateau.addParcelle(0,-2,p1);
            plateau.addParcelle(1,-1,p2);
            plateau.addParcelle(2,0,p3);
            plateau.addParcelle(3,1,p4);
            plateau.addParcelle(4,2,ptDepart);



            // irriguer les 5 parcelles pour y pousser un bambo
            for(Parcelle p : plateau.getListParcelle()){
                p.irriguerParcelle();

            }



            // vérifer qu'on a un bambo partout
            for(Parcelle p : plateau.getListParcelle()){
                if((p.getBamboo() != 1) && (!p.getColor().equals("Etang"))){
                    assertBambo = false;
                }
            }

            // positionner le panda sur une parcelle de départ
            act.deplacementPanda(4,2, panda,j);



            act.deplacementPanda(3,1, panda,j);
            act.deplacementPanda(2,0, panda,j);
            act.deplacementPanda(1,-1, panda,j);
            act.deplacementPanda(0,-2,panda,j);
            act.deplacementPanda(4,2, panda,j);


            // vérifer qu'on a aucun bambo restant
            for(Parcelle p : plateau.getListParcelle()){
                if((p.getBamboo() != 0) && (!p.getColor().equals("Etang"))){
                    assertBambo = false;
                }
            }


            // vérifier que les bambo ont été ajoutés à la fiche
            if(j.getjFiche().getNbYellowBambo() != 5){
                assertBambo = false;
            }
            assertTrue(assertBambo);
        }
    }