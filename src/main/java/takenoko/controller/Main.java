package takenoko.controller;

import com.sun.org.apache.xpath.internal.SourceTree;
import takenoko.ai.Bot2;
import takenoko.ai.RandomBot;
import takenoko.ai.Smart;
import takenoko.utils.Log;

import java.util.SortedMap;
import java.util.logging.Level;

/**
 * La classe qui fait office de point d'entrée au jeu.
 * Lance plusieurs parties et compare les résultats
 * @author Natan BEKELE
 */
public class Main {

    private final static int NBOFGAME =1000;
    /**
     * Crée le log, appele le Moteur
     * Elle initialise le niveau du log
     * et lance 1000 parties entre deux joueurs.
     * à chaque tour, on compare les résultats et on les sauvegarde
     * pour calculer et imprimer les statistiques à la suite
     */
    //static final Logger logger= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String [] args){
        Log.logger.setLevel(Level.OFF);
        Log.logger.log(Level.INFO, "Le Jeu commence.");
        System.out.println();

        System.out.println("#### Le joueur 1: RandomBot joue contre joueur 2: SmartBot1 avec Strategie Panda. ###\n");
        runMultipleRounds(new Moteur(false,new RandomBot(), new Smart()));
        System.out.println("\n#### Le joueur 1: SmartBot1 avec Strategie Panda joue contre joueur 2: SmartBot1 avec Strategie Panda. ###\n");
        runMultipleRounds(new Moteur(false,new Smart(), new Smart()));
        System.out.println("\n#### Le joueur 1: RandomBot joue contre joueur 2: SmartBot2 avec Strategie Parcelle. ###\n");
        runMultipleRounds(new Moteur(false,new RandomBot(), new Bot2()));
        System.out.println("\n#### Le joueur 1: SmartBot1 avec Strategie Panda joue contre joueur 2: SmartBot2 avec Strategie Parcelle. ###\n");
        runMultipleRounds(new Moteur(false,new Smart(), new Bot2()));

    }



    /**
     * La fonction qui calcule le pourcentage des gains, et l'imprime à l'écran.
     * @param j1, prend le nombre de parties gagnés par le joueur1
     * @param j2, prend le nombre de parties gagnés par le joueur2
     * @param ega prend le nombre de parties ou il y a égalité
     */
     private static void printStat(int j1, int j2, int ega) {
        String joueur1="Le joueur 1 gagne a : " + (j1*100)/(j1+j2+ega) +" %.";
        String joueur2="Le joueur 2 gagne a: " + (j2*100)/(j1+j2+ega) +" %.";
        String egalite="Il ya egalite a: " + (ega*100)/(j1+j2+ega) +" %.";
        System.out.println(joueur1 + " \n" + joueur2 + " \n" + egalite );
    }

    static void runMultipleRounds(Moteur moteur){
        String res;
        int j1=0;
        int j2=0;
        int ega=0;
        for(int i=0;i<NBOFGAME;i++){
            moteur.reset();
            moteur.runGame();
            res=moteur.comparateur();
            if(res.equals("JOUEUR 1"))j1++;
            else if(res.equals("JOUEUR 2"))j2++;
            else if(res.equals("Egalite"))ega++;
            Log.logger.log(Level.INFO,"GAGNANT : "+res);
        }
        printStat(j1,j2,ega);

    }

}