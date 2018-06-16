package takenoko.ai.strategy;

import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.objective.Objectif;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.player.Fiche;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;

import java.awt.*;
import java.util.ArrayList;

public class AnalysePanda {
    private int[] bambousAManger=new int[]{0,0,0};
    public ObjectifsTypePanda objectif;
    private Plateau plateau;
    private Fiche fiche;
    private Possibility possibility;
    private Point direction;
    private Point directionJardinier;

    public AnalysePanda(ObjectifsTypePanda objectif, Plateau pl,Fiche fiche) {
        this.objectif = objectif;
        this.plateau = pl;
        this.fiche=fiche;
        this.possibility=new Possibility(plateau);
        trouveBambousAManger();
        this.direction=trouveParcellePanda();
        this.directionJardinier=trouveParcelleJardinier();

    }
    public void trouveBambousAManger(){
        bambousAManger[0]=objectif.getNbGreenBambo()-fiche.getNbGreenBambo();
        bambousAManger[1]=objectif.getNbYellowBambo()-fiche.getNbYellowBambo();
        bambousAManger[2]=objectif.getNbPinkBambo()-fiche.getNbPinkBambo();
    }

    private Point trouveParcellePanda(){
        ArrayList<Parcelle> listDeplacementPossible=new ArrayList(possibility.possibilityCharacter(plateau.getPanda().getPosX(),
                plateau.getPanda().getPosY()));
        Point nextMove=new Point(0,0);
        for(Parcelle parcelle : listDeplacementPossible){
            switch(parcelle.getColor()){
                case "Green":
                    if (bambousAManger[0]>0&&parcelle.getBamboo()>0){
                        nextMove=new Point(parcelle.getX(),parcelle.getY());
                        break;
                    }
                case "Yellow":
                    if (bambousAManger[1]>0&&parcelle.getBamboo()>0){
                        nextMove=new Point(parcelle.getX(),parcelle.getY());
                        break;
                    }
                case "Pink":
                    if (bambousAManger[2]>0&&parcelle.getBamboo()>0){
                        nextMove=new Point(parcelle.getX(),parcelle.getY());
                        break;
                    }
            }
        }
        return nextMove;
    }

    public int[] getBambousAManger() {
        return bambousAManger;
    }

    public Point getDirection() {
        return direction;
    }

    private Point trouveParcelleJardinier(){
        ArrayList<Parcelle> listDeplacementPossible=new ArrayList(possibility.possibilityCharacter(plateau.getGardener().getPosX(),
                plateau.getGardener().getPosY()));
        Point nextMove=new Point(0,0);
        for(Parcelle parcelle : listDeplacementPossible){
            switch(parcelle.getColor()){
                case "Green":
                    if (bambousAManger[0]>0&&parcelle.getBamboo()==0){
                        nextMove=new Point(parcelle.getX(),parcelle.getY());
                        break;
                    }
                case "Yellow":
                    if (bambousAManger[1]>0&&parcelle.getBamboo()==0){
                        nextMove=new Point(parcelle.getX(),parcelle.getY());
                        break;
                    }
                case "Pink":
                    if (bambousAManger[2]>0&&parcelle.getBamboo()==0){
                        nextMove=new Point(parcelle.getX(),parcelle.getY());
                        break;
                    }
            }
        }
        return nextMove;
    }

    public Point getDirectionJardinier() {
        return directionJardinier;
    }
}
