package takenoko.controller;

import takenoko.ai.Bot2;
import takenoko.ai.Intelligence;
import takenoko.ai.RandomBot;
import takenoko.ai.Smart;
import takenoko.graphics.Frame;
import takenoko.inventory.characters.Gardener;
import takenoko.inventory.characters.Panda;
import takenoko.inventory.board.Plateau;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;
import takenoko.utils.Log;

import java.util.logging.Level;

import static java.lang.Thread.sleep;

/**
 * this class represents the game's engine, it will allow the players to interact through the board
 */
public class Moteur {
    private Action action;
    private Joueur joueur1;
    private Joueur joueur2;
    Intelligence bot1;
    Intelligence bot2;
    private Plateau lePlateau ;
    private Generator generate;
    private Possibility possibility;
    private Panda panda;
    private Gardener gardener;
    private Frame frame;
    private String currentPlayer;
    private boolean graphicState;

    private Meteo meteo;


    /**
     * constructor with that create two players and a plateau
     */


    Moteur(boolean graphicsState, Intelligence bot1, Intelligence bot2){

        panda  = new Panda();
        gardener = new Gardener();
        lePlateau=new Plateau(panda,gardener);
        generate=new Generator();
        action=new Action(lePlateau,generate);
        possibility=new Possibility(lePlateau);
        this.graphicState=graphicsState;
        bot1.setParamaeters(lePlateau,action,possibility);
        bot2.setParamaeters(lePlateau,action,possibility);
        this.bot1=bot1;
        this.bot2=bot2;

        if(graphicsState) frame = new Frame(this,lePlateau);
        joueur1 =new Joueur("JOUEUR 1",action,bot1,graphicState,frame);
        joueur2 =new Joueur("JOUEUR 2",action,bot2,graphicState,frame);

        addObjectifsDebutJeu();


        meteo=new Meteo();

    }



    void addObjectifsDebutJeu(){
        joueur1.addObjectifParcelleToFiche();
        joueur1.addObjectifPandaToFiche();
        joueur1.addObjectifJardinierToFiche();

        joueur2.addObjectifParcelleToFiche();
        joueur2.addObjectifPandaToFiche();
        joueur2.addObjectifJardinierToFiche();

    }
    void runGame(){
        while (joueur1.getNbObjValider()<9 && joueur2.getNbObjValider()<9 && lePlateau.getTurnPlayed()<100)  {
            currentPlayer=joueur1.getNomJoueur();
            lePlateau.setJoueurJouant(currentPlayer);
            lePlateau.setScoreJ2(joueur1.getjFiche().getScore());
            lePlateau.setScoreJ2(joueur2.getjFiche().getScore());

            meteo.resetMeteo();
            Log.logger.log(Level.INFO, "#########################################################     JOUEUR 1  JOUE , METEO :  "+meteo.getType());
            joueur1.play(meteo);
            //Log.logger.log(Level.INFO,"Nombre de parcelles dispo : "+generate.nbParcellesDispo());
            meteo.resetMeteo();
            lePlateau.addOneTurnToCount();


            currentPlayer=joueur2.getNomJoueur();
            lePlateau.setJoueurJouant(currentPlayer);

            Log.logger.log(Level.INFO, "#########################################################     JOUEUR 2  JOUE , METEO :  "+meteo.getType());
            joueur2.play(meteo);
            lePlateau.addOneTurnToCount();
            //Log.logger.log(Level.INFO,"Nombre de parcelles dispo : "+generate.nbParcellesDispo());
            lePlateau.setScoreJ2(joueur1.getjFiche().getScore());
            lePlateau.setScoreJ2(joueur2.getjFiche().getScore());
        }


    }



    /**
     * @return the name of player winning the game or "Equality" if no one has won
     */
    String comparateur(){
        String toPrint="SCORES \n"+"Joueur1 : "+joueur1.getjFiche().getScore()+" points.\nJoueur2 : "+joueur2.getjFiche().getScore()+" points";
        Log.logger.log(Level.INFO,toPrint);
        if(joueur1.getjFiche().getScore()>joueur2.getjFiche().getScore()){

            return joueur1.getNomJoueur();
        }
        else if(joueur2.getjFiche().getScore()>joueur1.getjFiche().getScore()){

            return joueur2.getNomJoueur();
        }
        else{
            return "Egalite";
        }
    }



    void reset(){
        panda  = new Panda();
        gardener = new Gardener();
        lePlateau=new Plateau(panda,gardener);
        generate=new Generator();
        action=new Action(lePlateau,generate);
        possibility=new Possibility(lePlateau);

        bot1.setParamaeters(lePlateau,action,possibility);
        bot2.setParamaeters(lePlateau,action,possibility);
        joueur1 =new Joueur("JOUEUR 1",action,bot1,graphicState,frame);
        joueur2 =new Joueur("JOUEUR 2",action,bot2,graphicState,frame);
        addObjectifsDebutJeu();


        meteo=new Meteo();
    }

}
