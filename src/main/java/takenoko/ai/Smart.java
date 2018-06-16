package takenoko.ai;

import com.sun.media.jfxmedia.logging.Logger;
import takenoko.ai.strategy.StrategyForPanda;
import takenoko.controller.Action;

import takenoko.controller.Meteo;
import takenoko.inventory.board.Segement;
import takenoko.inventory.objective.Objectif;
import takenoko.inventory.objective.ObjectifsTypeJardinier;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;
import takenoko.utils.Log;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;

/**
 * bot chooses his action in order to accomplish an objective
 */
public class Smart implements Intelligence {
    private Plateau plateau;
    private Possibility possibility;
    private Action action;
    private StrategyForPanda strategy;
    private Joueur joueur;

    private List<List<Point>> parcelObjectiveMoveRank;

    public Smart(Plateau pl, Action act, Possibility poss){
        plateau=pl;
        action=act;
        possibility=poss;

    }
    public Smart(){

    }

    @Override
    public void setParamaeters(Plateau plateau, Action action, Possibility possibility) {
        this.plateau=plateau;
        this.possibility=possibility;
        this.action=action;
    }

    public void makeChoice(Joueur joueur, Meteo meteo){
        this.joueur=joueur;
        strategy=new StrategyForPanda(plateau,action,joueur);
        //TODO add meteo
      switch (meteo.getType()) {
            case "Pluie": {
                strategy.growBambouMeteo(joueur.getjFiche().getObjectifsTypePanda());
                break;
            }
            case "Eclair": {
                strategy.movePandaMeteo(joueur.getjFiche().getObjectifsTypePanda());
                meteo.jouePanda();
                meteo.decrementNbCoupsLeft();
                break;
            }
            case "Nuage": {
               /* if(action.getSizeAmenagement("nopanda")>0){
                    Log.logger.log(Level.INFO,"Un aménagement");
                    action.prendreAmenagement("nopanda",joueur.getjFiche());
                }*/
                break;
            }
            case "?":{
               meteo.choisirMeteo("Vent");
               meteo.decrementNbCoupsLeft();
               break;
            }
        }
        play(meteo);


    }

    private void play(Meteo meteo) {
            ObjectifsTypePanda obj = (ObjectifsTypePanda) strategy.chooseObjectif();
            if (joueur.getjFiche().sizeOfNoneValidatedPandaObj() < 3 && meteo.getCoupsPossibles().get("Objectif") > 0 && joueur.getjFiche().sizeOfNoneValidatedObjTotal()<5  ) {
                //draw objectif
                //TODO move to stratedgy
                if (action.getGenerator().getPileObjectifsTypePanda().size() > 0) {
                    joueur.addObjectifPandaToFiche();

                }
                else if(action.getGenerator().getPileObjectifsTypeParcelle().size()>0){
                    joueur.addObjectifParcelleToFiche();
                }
                meteo.joueObjectif();
            }

            else if (meteo.getCoupsPossibles().get("Panda") > 0 && strategy.deplacerPandaForObjPanda(obj)) {
                //eat bambou
                meteo.jouePanda();

            } else if (meteo.getCoupsPossibles().get("Jardinier") > 0 && strategy.deplacerJardinierForObjPanda(obj)) {
                //move gardener to grow bambou
                meteo.joueJardinier();
            } else if (meteo.getCoupsPossibles().get("Irrigation") > 0 && joueur.getjFiche().getNbIrrigations()<1) {
                if (this.joueur.getjFiche().getNbIrrigations() == 0) {
                    action.prendreIrrigation(joueur.getjFiche());
                    meteo.joueIrrigation();
                }
            }else if(action.getSizeAmenagement("doublebambo")>0){
                Log.logger.log(Level.INFO,"Un aménagement No Panda est pris");
                action.prendreAmenagement("doublebambo",joueur.getjFiche());
                if(!possibility.possibilityAmenagement().isEmpty()){
                    action.poserAmenagement(joueur.getjFiche().useAmenagementFromFiche("doublebambo"),possibility.possibilityAmenagement().get(0));
                }
            }


            else if (meteo.getCoupsPossibles().get("Parcelle") > 0 && strategy.poserParcelleForObjPanda(obj)) {
                //poser parcel
                meteo.joueParcelle();
            }
        strategy.poserIrrigationForObjPanda(obj);


       /* // Smart player has exhausted his ideas !
        else if(plateau.getTurnPlayed()>600)
            joueur.setRandomIntelligence();*/
    }









    public void realizeObjectifParcel(ObjectifsTypeParcelle obj){
       List<List<Point>> objState=  checkIfConfigurationExists(obj);
       HashMap<Integer,List<List<Point>>> stateHash=transformListToHash(objState);
       List<Integer> sortedKey=new ArrayList(stateHash.keySet());
       Collections.sort(sortedKey, Collections.reverseOrder());
       for(Integer i:sortedKey){
           //obj
       }
    }


    private HashMap transformListToHash(List<List<Point>> list){
        HashMap<Integer,List<Point>> hash = new HashMap<>();
        for (List ls: list){
            if (hash.containsKey(ls.size())) hash.get(ls.size()).addAll(ls);
            else hash.put(ls.size(),ls);
        }
        return hash;
    }

    /**
     *
     * @param obj the objective
     * @return list of different combinaisions of objective realisation state
     */
    public List checkIfConfigurationExists(ObjectifsTypeParcelle obj){
        List<List<Point>> objState= new ArrayList<>();
        List<Point> objG =getObjCoordinateToList(obj.getCoordinatesObj()[0]);
        for(Parcelle parc: possibility.getListOfParcelByColor(obj.getColor())){
            List<Point> existingConfig =new ArrayList<>();
            existingConfig.add(new Point(parc.getX(),parc.getY()));
            for(Point pt: objG){
                int x=parc.getX()+pt.x;
                int y=parc.getY()+pt.y;
                if(pt.x==0 && pt.y==0) continue;
                if(!(plateau.isEmpty(x, y)) && plateau.getParcelleByCoord(x,y).getColor().equals(obj.getColor()))
                    existingConfig.add(new Point(x,y));
            }
            objState.add(existingConfig);
        }
        return objState;

    }


    /**
     * @param cord array of coordinates of objective geometry
     * @return list of points of the array of coordinates given in the parameter
     */
  public  ArrayList<Point> getObjCoordinateToList(int [][] cord){
        ArrayList<Point> list= new ArrayList<>();
        for (int i=1;i<cord.length;i++){
            list.add(new Point(cord[i][0],cord[i][1]));
        }
        return list;
    }

    public Action getAction() {
        return action;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Possibility getPossibility() {
        return possibility;
    }
}
