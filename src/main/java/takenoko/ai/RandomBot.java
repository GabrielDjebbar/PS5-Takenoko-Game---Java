package takenoko.ai;

import takenoko.controller.Action;
import takenoko.controller.Meteo;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;
import takenoko.utils.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class RandomBot implements Intelligence {
    private Plateau plateau;
    private Possibility possibility;
    private Action action;
    private ArrayList<Parcelle> piocheParcelle;
    private Parcelle parcelleJouee;
    private Meteo meteo;

    public RandomBot(Plateau pl, Action act, Possibility p){
        plateau =pl;
        action=act;
        possibility=p;

    }

    public RandomBot(){

    }

    @Override
    public void setParamaeters(Plateau plateau, Action action, Possibility possibility) {
        this.plateau=plateau;
        this.possibility=possibility;
        this.action=action;
    }

    /**
     * @param joueur
     * this function will allow the player to make a random choice to put a parcelle on the board.
     */
    public void makeChoice(Joueur joueur, Meteo meteo) {
        this.meteo=meteo;
        if (this.plateau.getTurnPlayed() < 2) {
            if(this.setPiocheParcelle()){
                putParcelRandom(joueur);
                resetPiocheParcelle();
            }
        }

        if(true){
            switch (meteo.getType()) {
                case "Pluie": {
                    putBambooSomewhere();
                break;
            }
            case "Eclair": {
                movePandaRandom();
                meteo.jouePanda();
                meteo.decrementNbCoupsLeft();
                break;
            }
            case "Nuage": {

                if(action.getSizeAmenagement("bassin")>0){
                    Log.logger.log(Level.INFO,"Un aménagement No Panda est pris");
                    action.prendreAmenagement("bassin",joueur.getjFiche());
                    if(!possibility.possibilityAmenagement().isEmpty()){
                        action.poserAmenagement(joueur.getjFiche().useAmenagementFromFiche("bassin"),possibility.possibilityAmenagement().get(0));
                    }
                }

                else if(action.getSizeAmenagement("nopanda")>0){
                    Log.logger.log(Level.INFO,"Un aménagement No Panda est pris");
                    action.prendreAmenagement("nopanda",joueur.getjFiche());
                    if(!possibility.possibilityAmenagement().isEmpty()){
                        action.poserAmenagement(joueur.getjFiche().useAmenagementFromFiche("nopanda"),possibility.possibilityAmenagement().get(0));
                    }
                }

               else if(action.getSizeAmenagement("doublebambo")>0){
                   Log.logger.log(Level.INFO,"Un aménagement No Panda est pris");
                   action.prendreAmenagement("doublebambo",joueur.getjFiche());
                   if(!possibility.possibilityAmenagement().isEmpty()){
                   action.poserAmenagement(joueur.getjFiche().useAmenagementFromFiche("doublebambo"),possibility.possibilityAmenagement().get(0));
                   }
               }


                break;
            }
            case "?":{
                meteo.choisirMeteo("Nuage");
                break;
            }
        }

        }

        playRandom(meteo,joueur);
    }

    /**
     * choose randomly an action ( put parcel, move panda,move gardener , or put irrigation ) when possible
     * , and play it.
     * @param joueur le joueur
     */

    private void playRandom(Meteo meteo,Joueur joueur){

            if(joueur.getjFiche().getObjectifs().size()<3&&meteo.getCoupsPossibles().get("Objectif")>0){
                int a=(int) (Math.random()*3);
                switch (a) {
                    case 0:
                    {       if(action.getGenerator().getPileObjectifsTypeParcelle().size()>0){
                            joueur.addObjectifParcelleToFiche();
                            meteo.joueObjectif();
                            break;}
                        }
                    case 1:{
                        if(action.getGenerator().getPileObjectifsTypePanda().size()>0){
                            joueur.addObjectifPandaToFiche();
                            meteo.joueObjectif();
                            break;}
                    }
                    case 2:{
                        if(action.getGenerator().getPileObjectifsTypeJardinier().size()>0){
                            joueur.addObjectifJardinierToFiche();
                            meteo.joueObjectif();
                            break;}
                    }
                }

            }

            else{
                    int b=(int) (Math.random()*4);
                    switch (b){
                        case 0:{
                            if(meteo.getCoupsPossibles().get("Parcelle")>0){
                                if (this.setPiocheParcelle()) {
                                    putParcelRandom(joueur);
                                    this.resetPiocheParcelle();
                                    meteo.joueParcelle();
                                }
                            }
                            break;
                        }
                        case 1:{
                            if(meteo.getCoupsPossibles().get("Irrigation")>0){
                                if(joueur.getjFiche().getNbIrrigations()==0){
                                    action.prendreIrrigation(joueur.getjFiche());//risque ?
                                    meteo.joueIrrigation();
                                }
                            }
                            break;
                        }

                        case 2:{
                            if(meteo.getCoupsPossibles().get("Panda")>0 ){
                            //eat bambou
                            pandaMoveRandomly(joueur);
                            meteo.jouePanda();
                            }
                            break;

                        }

                        case 3:{
                            if(meteo.getCoupsPossibles().get("Jardinier")>0){
                                //move gardener to grow bambou
                                GardenerMoveRandomly();
                                meteo.joueJardinier();

                            }
                            break;
                        }
                    }
                }

    }







    private void putBambooSomewhere() {
        ArrayList<Parcelle> listP = new ArrayList<>();
        for(Parcelle p:plateau.getListParcelle()){
            if(p.getBamboo()<4 && p.isParcelleIrrigue()&& !p.getColor().equals("Etang")){
            listP.add(p);
            }
        }
        int sizeList=listP.size();
        if(sizeList>0){
        int randomNumber = (int) ((Math.random())*sizeList);
        listP.get(randomNumber).add1Bamboo();}
    }

    /**
     * put a parcel at random on the board
     * @param joueur joueur
     */
    private void putParcelRandom(Joueur joueur){
        parcelleJouee =piocheParcelle.get(0);
        int randNbParcel = (int)(Math.random() * ((possibility.availablePositions().size())));
        int i = 0;
        for(Point p: possibility.availablePositions()) {
            if (i == randNbParcel) {
                possibility.majTabSegementsPossible();
                action.putParcel(parcelleJouee,p.x,p.y,joueur.getNomJoueur());

            }
            i++;
        }

    }





    /**
     * return a random position on which the panda can be moved.
     *
     */
    private Point movePandaRandom(){
        ArrayList<Parcelle> listDeplacementPossible=new ArrayList(possibility.possibilityCharacter(plateau.getPanda().getPosX(),
                plateau.getPanda().getPosY()));
        int a = listDeplacementPossible.size();
        int b = (int) (Math.random() * ((a)));
        if(a>0) return new Point(listDeplacementPossible.get(b).getX(),listDeplacementPossible.get(b).getY());
        else return null;
    }


    /**
     * The panda moves at random on the board
     * @param joueur joueur
     */
    private void pandaMoveRandomly(Joueur joueur){
        Point pandaRandom = movePandaRandom();
        if(pandaRandom!= null) action.deplacementPanda(pandaRandom.x, pandaRandom.y, this.plateau.getPanda(), joueur);
    }

    /**
     * @return a random position(x,y) on which the gardener can be moved.
     */
    private Point randomPointGardener(){
        ArrayList<Parcelle> listDeplacementPossible=possibility.possibilityCharacter(plateau.getGardener().getPosX(),plateau.getGardener().getPosY());
        int a = listDeplacementPossible.size();
        int b = (int) (Math.random() * ((a)));
        if(a>0) {
            return new Point(listDeplacementPossible.get(b).getX(), listDeplacementPossible.get(b).getY());
        }
        return new Point (0,0);
    }

    /**
     * gardener moves at random on the board.
     */
    private void GardenerMoveRandomly(){
        Point gardenerRandom= randomPointGardener();
        if(gardenerRandom.x!=0 || gardenerRandom.y!=0) {
            action.deplacementJardinier(gardenerRandom.x, gardenerRandom.y, this.plateau.getGardener());
        }
    }


    /**
     * give back to deck the parcels not chosen.
     */
    private void resetPiocheParcelle() {
        this.piocheParcelle.remove(parcelleJouee);
        action.GiveBackToDeck(piocheParcelle);
    }

     public boolean setPiocheForOneParcelle() {
        this.piocheParcelle = action.getOneRandomParcelFromPioche();
        if (this.piocheParcelle.size() == 0) {
            return false;
        }
        return true;
    }

    private boolean setPiocheParcelle() {
        this.piocheParcelle = action.getPiocheParcelle();
        if (this.piocheParcelle.size() == 0) {
            return false;
        }
        return true;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Possibility getPossibility() {
        return possibility;
    }

    public Action getAction() {
        return action;
    }
}
