package takenoko.controller;

import org.junit.Test;
import takenoko.ai.RandomBot;
import takenoko.inventory.board.Plateau;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;

import static org.junit.Assert.*;

public class ActionTest {

    /**
     * Prendre un aménagement de type demandé
     * du generator et le met dans la fiche du joueur
     */
    @Test
    public void prendreAmenagement() throws Exception {
        boolean expected = true;
        Generator generator=new Generator();
        Plateau plateau=new Plateau();
        Possibility possibility = new Possibility(plateau);
        Action action=new Action(plateau,generator);
        Joueur j=new Joueur("j1",action,new RandomBot(plateau,action,possibility));


        //  un controle doit être fait avant l'appelation de prendre amenagement
        //  comme ce que je fais ici !
        if(generator.getSizeBassinAmenagement() > 1){
            action.prendreAmenagement("bassin",j.getjFiche());
        }

        // Controler si dans la fiche on trouve bien un aménagement de type bassin
        if(j.getjFiche().getNbAmenagemnts("bassin") != 1){
            expected = false;
        }

        // vérifier que il n y a pas eu une erreur lors de l'execution
        assertTrue(expected);
    }

}