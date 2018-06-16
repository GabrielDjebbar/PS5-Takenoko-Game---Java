package takenoko.player;

import takenoko.ai.Intelligence;
import takenoko.ai.RandomBot;
import takenoko.ai.Smart;
import takenoko.controller.Action;
import takenoko.controller.Meteo;
import takenoko.controller.Moteur;
import takenoko.graphics.Frame;
import takenoko.inventory.objective.Objectif;
import takenoko.inventory.objective.ObjectifsTypeJardinier;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.utils.Log;

import java.awt.*;
import java.util.ArrayList;

import java.util.EmptyStackException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  this class represents a player
 */
public class Joueur {
    private  final Logger logger= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private String nomJoueur;
    private Fiche jFiche;
    private int nbToursAutorises = 0;
    private Action action;
    private Intelligence strategy;
    private Meteo meteo;
    private boolean graphicState;
    private Frame frame;

    /**
     * default constructor
     * @param name
     */
    public Joueur(String name){
        this.jFiche = new Fiche(name);
        this.nomJoueur = name;
        this.meteo=new Meteo();
    }
    public Joueur(String name,Action act,Intelligence intelig){
        strategy=intelig;
        this.jFiche = new Fiche(name);
        this.nomJoueur = name;
        action=act;
        this.meteo=new Meteo();
    }
    /**
     * This is the constructor
     * @param name
     */
    public Joueur(String name,Action act,Intelligence intelig,boolean graphicState,Frame frame){
        strategy=intelig;
        this.jFiche = new Fiche(name);
        this.nomJoueur = name;
        action=act;
        this.meteo=new Meteo();
        this.frame=frame;
        this.graphicState=graphicState;

    }

    /**
     * Function that allow a player to play with a given strategy
     */
    public void play(Meteo meteo){
        // la meteo
        while(meteo.play()) {
            paint(nomJoueur);
            strategy.makeChoice(this, meteo);
        }
        action.validate(jFiche);

    }
    private void paint(String name) {
        if(graphicState){
            try {
                Thread.sleep(500);
               // frame.panel.draw();
                frame.panel.repaint();
            } catch (InterruptedException e) {
            }
        }
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.drawString(nomJoueur,15,15);

    }


    public Fiche getjFiche(){
        return jFiche;
    }



    /**
     * add a random Objectif, of type Parcelle for now, to the player's fiche.
     */
    public void addObjectifParcelleToFiche(){
        // get a random objectif
        try {
            ObjectifsTypeParcelle obj = action.getObjectifParcelle();
            // Print correctly what the player get as Objectif
            String toPrint = "Le joueur " + nomJoueur + " tire un objectif de type " + obj.getType() +
                    " et de geometry " + obj.getGeometry() + " de couleur " + obj.getColor();
            logger.log(Level.INFO, toPrint);

            // adding objectif to Fiche
            jFiche.addObjectifTypeParcelle(obj);
        }catch(EmptyStackException e){
            throw e;
        }
    }

    /**
     * add a random Objectif, of type Panda for now, to the player's fiche.
     */
    public void addObjectifPandaToFiche(){
        // get a random objectif
        ObjectifsTypePanda obj = action.getObjectifPanda();
        // Print correctly what the player get as Objectif
        String toPrint = "Le joueur "+ nomJoueur +" tire un objectif de type " + obj.getType() +
                " de couleur " + obj.getColor();
        logger.log(Level.INFO,toPrint);

        // adding objectif to Fiche
        jFiche.addObjectifTypePanda(obj);
    }

    public void addObjectifJardinierToFiche(){
        // get a random objectif
        ObjectifsTypeJardinier obj = action.getObjectifJardinier();
        // Print correctly what the player get as Objectif
        String toPrint = "Le joueur "+ nomJoueur +" tire un objectif de type " + obj.getType() +
                " de couleur " + obj.getColor() + "avec un nombre de Bamboo de :"+obj.getNumberOfBamboo()+"et de longueur" +
                obj.getLengthOfBamboo();
        logger.log(Level.INFO,toPrint);

        // adding objectif to Fiche
        jFiche.addObjectifTypeJardinier(obj);
    }

    /**
     * @return la meteo
     */
    public Meteo getMeteo(){return this.meteo;}

    /**
     * add an objectiv given in paramater to the player's fiche
     */
    public void addGivenObjectifJardinierToFiche(ObjectifsTypeJardinier obj){
        String toPrint = "Le joueur "+ nomJoueur +" tire un objectif de type " + obj.getType() +
                " de couleur " + obj.getColor() + "avec un nombre de Bamboo de :"+obj.getNumberOfBamboo()+"et de longueur" +
                obj.getLengthOfBamboo();
        logger.log(Level.INFO,toPrint);
        jFiche.addObjectifTypeJardinier(obj);
    }

    /**
     * @return  the player's objectives.
     */
   public ArrayList<Objectif> getObjectifFromFiche(){
        return jFiche.getObjectifs();
    }

    /**
     * @return the objectives of type Parcelle.
     */
   public ArrayList<ObjectifsTypeParcelle> getObjectifsParcelleFromFiche(){
        return jFiche.getObjectifsTypeParcelle();
   }

    /**
     * @return the objectives of type Jardinier.
     */
    public ArrayList<ObjectifsTypeJardinier> getObjectifsJardinierFromFiche(){
        return jFiche.getObjectifsTypeJardinier();
    }

    /**
     * @return the objectives of type Panda.
     */
    public ArrayList<ObjectifsTypePanda> getObjectifsPandaFromFiche(){
        return jFiche.getObjectifsTypePanda();
    }
    public ArrayList<ObjectifsTypeParcelle> GetObParcelleAFaire(){
        ArrayList<ObjectifsTypeParcelle> newList=new ArrayList<>();
        for(ObjectifsTypeParcelle o:jFiche.getObjectifsTypeParcelle()){
            if(!o.IsValidated()){
                newList.add(o);
            }
        }
        return newList;
    }
    public ArrayList<ObjectifsTypePanda> GetObPandaAFaire(){
        ArrayList<ObjectifsTypePanda> newList=new ArrayList<>();
        for(ObjectifsTypePanda o:jFiche.getObjectifsTypePanda()){
            if(!o.IsValidated()){
                newList.add(o);
            }
        }
        return newList;
    }
    public ArrayList<ObjectifsTypeJardinier> GetObJardinierAFaire(){
        ArrayList<ObjectifsTypeJardinier> newList=new ArrayList<>();
        for(ObjectifsTypeJardinier o:jFiche.getObjectifsTypeJardinier()){
            if(!o.IsValidated()){
                newList.add(o);
            }
        }
        return newList;
    }
    /**
     * @return the player's name
     */
    public String getNomJoueur(){
        return nomJoueur;
    }


    /**
     * @return the number of turns a player is allowed to play
     */
    public int getNbToursAutorises() {
        return nbToursAutorises;
    }


    /**
     * allow the player to play 2 more turns
     */
    public void addOneNbTours(){
        this.nbToursAutorises += 1;
    }

    /**
     * lessen of one the number of turns a player is allowed to play
     */
    public void lessOneNbTours(){
        this.nbToursAutorises -= 1;
    }

    /**
     * reset the number of turns of a player to the standard number 2.
     */
   public void resetNbToursToTwo(){
        this.nbToursAutorises = 2;
    }

    /**
     * @return the number of objective that have been validated.
     */
   public int getNbObjValider(){
        return jFiche.getNbObjectifValider();
    }

    /**
     * the bot has a no more inspiration and can only play randomly
     */
  /*  public void  setRandomIntelligence(){
        Smart bot= (Smart)this.strategy;
        this.strategy=new RandomBot(bot.getPlateau(),bot.getAction(),bot.getPossibility());
    }*/
}
