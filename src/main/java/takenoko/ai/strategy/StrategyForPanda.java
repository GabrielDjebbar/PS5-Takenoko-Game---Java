package takenoko.ai.strategy;

import takenoko.inventory.board.Parcelle;
import takenoko.controller.Action;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.board.Segement;
import takenoko.inventory.objective.Objectif;
import takenoko.inventory.objective.ObjectifsTypeJardinier;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;

import java.awt.*;
import java.util.*;
import java.util.List;


public class StrategyForPanda {
    Plateau plateau;
    Action action;
    Possibility possibility;
    Joueur joueur;
    private HashMap<Objectif,Integer> objRank;  // approximate number of moves to acomplish objective
    public StrategyForPanda(Plateau plateau, Action action,Joueur joueur){
        this.joueur=joueur;
        this.plateau=plateau;
        this.action=action;
        possibility= new Possibility(plateau);
    }


    public boolean deplacerJardinierForObjPanda(ObjectifsTypePanda objectifsTypePanda){
        String colorObj =objectifsTypePanda.getColor();
        this.plateau.getGardener();
        for(Parcelle pColor : possibility.getListOfParcelByColor(colorObj)){
            if(!pColor.isParcelleIrrigue())
                continue;
            for(Parcelle pGardener:
                    possibility.possibilityCharacter(this.plateau.getGardener().getPosX(),this.plateau.getGardener().getPosY())){
                if(pGardener.equals(pColor)){
                    this.action.deplacementJardinier(pGardener.getX(),pGardener.getY(),this.plateau.getGardener());
                    return true;
                }
            }
        }
        for(Parcelle p:possibility.possibilityCharacter(this.plateau.getGardener().getPosX(),this.plateau.getGardener().getPosY())){
            action.deplacementJardinier(p.getX(),p.getY(),plateau.getGardener());
            return true;
        }
        return false;
    }


    public boolean deplacerPandaForObjPanda(ObjectifsTypePanda objP){
        for (Parcelle parcelle: possibility.getListOfParcelByColor(objP.getColor())){
            for(Parcelle pandaParcel:
                    possibility.possibilityCharacter(plateau.getPanda().getPosX(),plateau.getPanda().getPosY())){
                // get bambou
                if(parcelle.equals(pandaParcel) && parcelle.getBamboo()>0){
                    action.deplacementPanda(parcelle.getX(),parcelle.getY(),plateau.getPanda(),joueur);
                    return true;
                }
            }
        }
       for (Parcelle pp: possibility.possibilityCharacter(plateau.getPanda().getPosX(),plateau.getPanda().getPosY())){
            if(pp.getBamboo()>0){
                action.deplacementPanda(pp.getX(),pp.getY(),plateau.getPanda(),joueur);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return true if it's possible to put an irrigation on a parcel of the same color of given objectif and return false
     * if it's not possible. If it's possible it will also put the irrigation on the board.
     */
    public boolean poserIrrigationForObjPanda(ObjectifsTypePanda objectifsTypePanda){
        String colorObj =objectifsTypePanda.getColor();
        if(possibility.putIrrigationisPossible(joueur)){
            for(Segement seg:possibility.getListSegementsPossible()){
                if(true){//seg.getParcelle().getColor().equals(colorObj)
                    action.poserIrrigation(seg,joueur,joueur.getjFiche());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Function that will allow the player searching for a parcel  with the same color as his current Panda objectif.
     * @return true if it's possible to put a parcel  ( and put the parcel )
     *
     * @return false if it's not possible to put any parcel at all
     */
    public boolean poserParcelleForObjPanda(ObjectifsTypePanda objectifsTypePanda){
        String colorObj = objectifsTypePanda.getColor();
        ArrayList<Parcelle>  listParcelsTakenFromDeck = new ArrayList<Parcelle>();
        Parcelle parcelChoosen ;
        if(action.getGenerator().nbParcellesDispo()>0 && possibility.availablePositions().size()>0){
            listParcelsTakenFromDeck=action.getGenerator().getPiocheParcelle();
            parcelChoosen=getParcelWithRighColor(listParcelsTakenFromDeck,colorObj);
            listParcelsTakenFromDeck.remove(parcelChoosen);
            action.GiveBackToDeck(listParcelsTakenFromDeck);
            Point pt = possibility.availablePositions().iterator().next();
            action.putParcel(parcelChoosen,pt.x,pt.y,joueur.getNomJoueur());
            return true;
        }
        return false;}

    /**
     * @return the parcel with the same as the objectif panda that the player is trying to realize.
     * If no objectif with the same color  has been found, the function will return the first parcel of the ArrayList.
     *
     *
     *
     */
    public Parcelle getParcelWithRighColor(ArrayList<Parcelle> ParcelsFromPioche, String colorObj){
        for(Parcelle p: ParcelsFromPioche){
            if (p.getColor().equals(colorObj)){
                return p;
            }
        }
        return ParcelsFromPioche.get(0);
    }

    /**
     * choose the best objective from fiche to play
     */
    public Objectif chooseObjectif(){
        objRank=new LinkedHashMap<>();
        Iterator<ObjectifsTypePanda> itPanda= joueur.getjFiche().getObjectifsTypePanda().iterator();
        Iterator<ObjectifsTypeJardinier> itGardener= joueur.getjFiche().getObjectifsTypeJardinier().iterator();
        Iterator<ObjectifsTypeParcelle> itParcel= joueur.getjFiche().getObjectifsTypeParcelle().iterator();
        while (true){
            if(itPanda.hasNext()){
                rankObjectivePanda(itPanda.next());
                continue;
            }
            break;
        }
        return getBestObjFromRank();

    }

     Objectif getBestObjFromRank() {
        if(objRank.size()>1){
            return Collections.min(objRank.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        }
        else{
            return joueur.getjFiche().getObjectifsTypePanda().get(0);}
    }

     void rankObjectivePanda(ObjectifsTypePanda next) {
        if(next.IsValidated()) return;
        int rank=0;
        if(next.getColor().equals("multicolor")){
            if(joueur.getjFiche().getNbGreenBambo()>=next.getNbGreenBambo()) rank++;
            if(joueur.getjFiche().getNbPinkBambo()>=next.getNbPinkBambo()) rank++;
            if(joueur.getjFiche().getNbYellowBambo()>=next.getNbYellowBambo()) rank++;
            rank=3- rank;
        }
        else if(next.getColor().equals("Green"))rank=2- joueur.getjFiche().getNbGreenBambo();
        else if(next.getColor().equals("Yellow"))rank=2-joueur.getjFiche().getNbYellowBambo();
        else if(next.getColor().equals("Pink"))rank= 2 - joueur.getjFiche().getNbPinkBambo();
        objRank.put(next,rank);

    }

    public void growBambouMeteo(List<ObjectifsTypePanda> objL){
        for(ObjectifsTypePanda obj: objL){
            if (obj.IsValidated()) continue;
            for(Parcelle p:plateau.getListParcelle()){
                if(p.getColor().equals(obj.getColor()) && p.isParcelleIrrigue() && p.getBamboo()<4){
                    p.add1Bamboo();
                    break;
                }
            }

        }

    }


    public void movePandaMeteo(ArrayList<ObjectifsTypePanda> objectifsTypePanda) {
        for (Parcelle p: plateau.getListParcelle()){
            if(p.getBamboo()>0){
                action.deplacementPanda(p.getX(),p.getY(),plateau.getPanda(),joueur);
                break;
            }
        }
    }
}