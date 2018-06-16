package takenoko.ai.strategy;

import takenoko.inventory.board.Plateau;

import takenoko.inventory.board.Segement;
import takenoko.referee.Possibility;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class ScoreObjectif {
    private ArrayList<Point> parcellesAIrriguer = new ArrayList<>();
    private ArrayList<Point> parcellesAPoser = new ArrayList<>();
    private Plateau plateau;
    private Possibility possibility;
    private int score=0;
    private int[] bambousAManger;
    private String couleurParcelle="";
    private Point direction;
    private Point directionJardinier;
    private String type;

    public ScoreObjectif(ArrayList<Point> parcellesAIrriguer,ArrayList<Point> parcellesAPoser,Plateau plateau,Possibility possibility,String couleurParcelle){
        this.parcellesAIrriguer=parcellesAIrriguer;
        this.parcellesAPoser=parcellesAPoser;
        this.plateau=plateau;
        this.couleurParcelle=couleurParcelle;
        this.possibility=possibility;
        this.bambousAManger=new int[]{0,0,0};

        this.type="Parcelle";
        calculeScore();
    }
    public ScoreObjectif(int[] bambousAManger,Point direction,Plateau plateau,Possibility possibility,Point directionJardinier){
        this.parcellesAIrriguer=new ArrayList<>();
        this.parcellesAPoser=new ArrayList<>();
        this.plateau=plateau;
        this.possibility=possibility;
        this.bambousAManger=bambousAManger;
        this.direction=direction;
        this.directionJardinier=directionJardinier;
        this.type="Panda";
        calculeScore();
    }
    private void calculeScore(){
        if (this.type=="Parcelle") {
            for (Point parcelle : parcellesAPoser) {
                //System.out.println(scoreParcelle(parcelle));
                score += scoreParcelle(parcelle);
            }
            for (Point parcelle : parcellesAIrriguer) {
                //System.out.println(scoreIrrigation(parcelle));
                score += scoreIrrigation(parcelle);
            }
        }
        if (this.type=="Panda") {
            if ((direction.x != 0 || direction.y != 0) && sommeTab(bambousAManger) != 0) {
                score += scorePanda();
            } else {
                score = 100;
            }
        }
    }

    private int scoreParcelle(Point parcelle){
        int parcellesACote=0;
        parcellesACote=possibility.getNeighboorParcellesByCoord(parcelle.x,parcelle.y).size();
        if(isAcoteEtang(parcelle)){
            return 1;
        }
        if (parcellesACote==0){
            return 3;
        }
        if(parcellesACote==1){
            return 2;
        }
        return 1;
    }
    private int scoreIrrigation(Point parcelle){
        double distance=distanceParcelleIrrigation(parcelle);
        if(isAcoteEtang(parcelle)){
            return 0;
        }
        //System.out.println(distance);
        if(distance<=1){
            return 0;
        } else if(distance<=1.8){
            return 1;
        } else if(distance<=2.8){
            return 2;
        }
        return 3;
    }
    private double distanceParcelleIrrigation(Point parcelle){
        double distance=20;
        double distanceTemporaire=20;
        for(Segement s:possibility.getListSegementsPossible()){
            distanceTemporaire=distance(s.getPointcentral(),parcelle);
            //System.out.println(s.getPointcentral()[0]+" , "+s.getPointcentral()[1]);
            if(distanceTemporaire<distance){
                distance=distanceTemporaire;
                //System.out.println(s.getPointcentral()[0]+" , "+s.getPointcentral()[1]);
            }
        }
        return distance;
    }
    private double distance(float a[],Point b){
        return Math.sqrt((b.x - a[0]) * (b.x - a[0]) + (b.y - a[1]) * (b.y - a[1]));
    }
    public int getScore(){
        return score;
    }
    void printArrayList(ArrayList<Point> liste){
        int i=0;
        for(Point p:liste){
            System.out.println(i+++" x="+p.x+" y="+p.y);
        }
        System.out.println("\n");
    }
    boolean isAcoteEtang(Point parcelle){
        int x=parcelle.x;
        int y=parcelle.y;
        if((abs(x)+abs(y)==2)&&(abs(x)<=2)&&(abs(y)<=2)&&(abs(x)!=0)){
            return true;
        }
        return false;
    }

    public ArrayList<Point> getParcellesAIrriguer() {
        return parcellesAIrriguer;
    }

    public ArrayList<Point> getParcellesAPoser() {
        return parcellesAPoser;
    }

    private int sommeTab(int[] tab){
        int i=0;
        for (int j = 0; j < tab.length; j++) {
            i+=tab[j];
        }
        return i;
    }
    private int scorePanda(){
        return sommeTab(bambousAManger)+3;
    }

    public Point getDirection() {
        return direction;
    }

    public String getCouleurParcelle() {
        return couleurParcelle;
    }

    public String getType() {
        return type;
    }

    public String toSring(){
        String result ="L'objectif choisi est de type "+this.getType();
        if(this.getType()=="Parcelle"){
            result+=" de couleur " +this.couleurParcelle;
        }
        if(this.getType()=="Panda"){
            result+=". Doit manger "+bambousAManger[0]+" Green "+bambousAManger[1]+" Yellow "+bambousAManger[2]+" Pink.";
        }
        return result;
    }
    public Point getDirectionJardinier(){
        return directionJardinier;
    }
}
