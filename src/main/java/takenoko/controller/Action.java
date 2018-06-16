package takenoko.controller;

import takenoko.inventory.board.Amenagement;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.board.Segement;
import takenoko.inventory.characters.Gardener;
import takenoko.inventory.characters.Panda;
import takenoko.inventory.objective.ObjectifsTypeJardinier;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.player.Fiche;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;
import takenoko.referee.Validation;
import takenoko.utils.Log;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;

public class Action {

    private Plateau plateau;
    private Generator generator;
    private Validation validation;
    private Possibility poss;


    public Action(Plateau pl,Generator gen){
        plateau = pl;
        poss=new Possibility(pl);
        generator=gen;
        validation=new Validation(plateau);
    }


    /**
     * prend un segment, et irrigue la parcelle + les 2 points.
     * @param s segment
     */
    public void poserIrrigation(Segement s, Joueur joueur,Fiche fiche){
        fiche.enleveIrrigation();
        Set<Integer> set = s.getSet();
        Parcelle p = s.getParcelle();
        for(Integer n : set){
            p.irriguerSide(n);
        }
        String toPrint = joueur.getNomJoueur() + " pose une irrigation en " + s.printSegement();
        Log.logger.log(Level.INFO, toPrint);
        plateau.irriguerParcellesSiPossible();
        for (Parcelle prc : plateau.getListParcelle()){
            prc.majParcelle(plateau);
        }
    }

    public void prendreIrrigation(Fiche f){
        if(generator.getOneIrrigation()){
            f.addIrrigation();

            String toPrint = f.getjName() + " prends une irrigation";
            Log.logger.log(Level.INFO, toPrint);
        }
    }

    /**
     * Method to put a parcel in the plateau
     * @param p parcel to put on plateau
     * @param x x postion for plateau
     * @param y y postion for plateau
     * @param name player's name who played this action
     */
    public void putParcel(Parcelle p,int x , int y, String name){
        String toPrint = name + " pose une parcelle en " + x + " , " + y + " de couleur " + p.getColor();
        Log.logger.log(Level.INFO, toPrint);
        plateau.addParcelle(x,y,p);
    }

    /**
     * Get objectif Parcelle from generator stack
     */
    public ObjectifsTypeParcelle getObjectifParcelle(){
        return generator.getObjectifTypeParcelle();
    }

    /**
     * Get objectif Panda from generator stack
     */
    public ObjectifsTypePanda getObjectifPanda(){

        return generator.getObjectifTypePanda();
    }
    public ObjectifsTypeJardinier getObjectifJardinier(){
        return generator.getObjectifTypeJardinier();
    }
    public boolean possibleTirePanda(){
        return generator.possibleTirePanda();
    }
    public boolean possibleTireParcelle(){
        return generator.possibleTireParcelle();
    }
    public boolean possibleTireJardinier(){
        return generator.possibleTireJardinier();
    }


    /**
     * Validate objectif already done by player
     * @param fiche players fiche
     */
    public void validate(Fiche fiche){
        validation.checkValidation(fiche);
    }


    /**
     * function that allow the panda to move somewhere on the board
     * @param x coordinate where the panda must moved
     * @param y coordinate where the panda must moved
     */
    public  void deplacementPanda(int x,int y,Panda panda,Joueur joueur){
        panda.setXY(x,y);
        Log.logger.log(Level.INFO,joueur.getNomJoueur()+" Déplace le Panda en  ["+ x +","+ y +"]");
        Parcelle p = plateau.getParcelleByCoord(x,y);
        if (p.getBamboo() != 0){
            switch (p.getColor()){
                case "Pink":{
                    p.remove1BambooEaten();
                    joueur.getjFiche().addPinkBamboToFiche();
                    break;
                }
                case "Green": {
                    p.remove1BambooEaten();
                    joueur.getjFiche().addGreenBamboToFiche();
                    break;
                }
                default: {
                    p.remove1BambooEaten();
                    joueur.getjFiche().addYellowBamboToFiche();
                    break;
                }
            }

        }
    }

    public  void deplacementJardinier(int x, int y, Gardener jardinier){
        jardinier.setXY(x,y);
        Log.logger.log(Level.INFO,"Le jardinier est déplacé en  ["+ x +","+ y +"]");
        GardenerCultivateBamboo();
    }


    /**
     * @return la liste des parcelles données par le generateur aux bot
     */
    public ArrayList<Parcelle> getPiocheParcelle() {
        return generator.getPiocheParcelle();}

    public ArrayList<Parcelle> getOneRandomParcelFromPioche() {
        return generator.getOneRandomParcelFromDeck();}
    /**
     * @param piocheRestante
     * envoie la donnée du bot a generator
     */
    public void  GiveBackToDeck(ArrayList<Parcelle> piocheRestante){
        generator.GiveBackToDeck(piocheRestante);
        //System.out.println("taille de la pioche restante : "+piocheRestante.size());
    }

    /**
     * pose un aménagement sur une parcelle
     * @param amenagement l'aménagement a poser
     * @param parcelle la parcelle a aménager
     */
    public void poserAmenagement(Amenagement amenagement, Parcelle parcelle){
        Log.logger.log(Level.INFO,"Un aménagement de type "+amenagement.getType()+" est posé en "+parcelle.getX()+" , "+parcelle.getY());
        parcelle.setAmenagement(amenagement);
    }

    /**
     * ATTENTION AVANT D'UTILISER CA, VERIFIER LE NB D'AMENAGEMENTS RESTANTS
     * DU GENERATOR !!! POUR EVITER NULL POINTER EXCEPTION
     * Prend un aménagement du type choisi du generator
     * @param type bassin, nopanda, doublebambo
     * @param fiche mettre référence a la fiche du joueur
     */
    public void prendreAmenagement(String type, Fiche fiche){
        Log.logger.log(Level.INFO,"Un aménagement de type "+type+" est pris par "+fiche.getjName());
        switch (type) {
            case "bassin": {
                fiche.addAmenagement(generator.getBassinAmenagemnt());
                break;
            }
            case "nopanda": {
                fiche.addAmenagement(generator.getNoPandaAmenagemnt());
                break;
            }
            default: {
                fiche.addAmenagement(generator.getDoubleBamboAmenagemnt());
                break;
            }
        }
    }


    /**
     * Une fonction qui pose un bambo dans une parcelle
     * VERIFICATIONS A FAIRE AVANT
     * # SI ON A LE DROIT DE POUSSER UN BAMBO AVEC POSSIBILITY
     * @param p
     */
    public void pousserBambo(Parcelle p){
        if(p.getAmenagement().getType().equals("doubleBambo")){
            if(p.getBamboo() == 3){ // si il est a 3, faire pousser un seul bambo
                p.add1Bamboo();
            }
            p.add2Bamboo(); // si il est a moins que 3, faire pousser 2 bambo
        }
        p.add1Bamboo(); // si pas d'aménagement doubleBambo, faire pousser 1 bambo
    }

    public void GardenerCultivateBamboo() {
        int x=plateau.getGardener().getPosX();
        int y=plateau.getGardener().getPosY();
        String color =plateau.getParcelleByCoord(x,y).getColor();

        ArrayList<Parcelle> parcelsForBamboo = poss.possibilityListPousserBambo();
        for (Parcelle p : parcelsForBamboo) {
            if (p.isADroiteDeLaParcelleDeLaPosition(x, y)&& p.getColor().equals(color))
                p.add1Bamboo();
            if (p.isAGaucheDeLaPosition(x, y)&& p.getColor().equals(color))
                p.add1Bamboo();
            if (p.isEnBasDroiteDeLaPosition(x, y)&& p.getColor().equals(color))
                p.add1Bamboo();
            if (p.isEnBasGaucheDeLaPosition(x, y)&& p.getColor().equals(color))
                p.add1Bamboo();
            if (p.isEnHautGaucheDeLaPosition(x, y)&& p.getColor().equals(color))
                p.add1Bamboo();
            if (p.isEnHautDroiteDeLaPosition(x, y)&& p.getColor().equals(color))
                p.add1Bamboo();
            if ((p.getX()==x)&&(p.getY()==y)&& p.getColor().equals(color))
                p.add1Bamboo();
        }
    }

    public int getSizeAmenagement(String type) {
        int res=0;
        switch(type){
            case "nopanda":
                res = generator.getSizeNoPandaAmenagement();
                break;
            case "bassin":
                res=generator.getSizeBassinAmenagement();
                break;
            case "doublebambo":
                res=generator.getSizDoubleBamboAmenagement();
                break;
        }
    return res; }

    public Generator getGenerator() {
        return generator;
    }
}
