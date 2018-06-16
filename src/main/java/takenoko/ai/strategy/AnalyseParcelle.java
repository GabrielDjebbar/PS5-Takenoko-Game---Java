package takenoko.ai.strategy;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.referee.Possibility;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AnalyseParcelle{
    private ArrayList<Point> parcellesAIrriguer = new ArrayList<>();
    private ArrayList<Point> parcellesAPoser = new ArrayList<>();
    private ObjectifsTypeParcelle objectif;
    private Plateau plateau;
    private Possibility possibility;

    public AnalyseParcelle(ObjectifsTypeParcelle objectif, Plateau pl) {
        this.objectif = objectif;
        this.plateau = pl;
        this.possibility=new Possibility(pl);
        trouveListeParcelle();


    }
    void trouveListeParcelle(){
        ArrayList<Point> parcellesOuChercher=iteration_parcelles();
        ArrayList<Point> resultat = new ArrayList<>();
        ArrayList<Point> resultatTemporaire;
        //System.out.println(parcellesOuChercher.size());
        for(Point p:parcellesOuChercher){

            resultatTemporaire=superposeObjectifSurPoint(p).get("parcelles");
            if(meilleurListe(resultatTemporaire,resultat)){
                resultat=recopieArrayList(resultatTemporaire);
                this.parcellesAIrriguer=superposeObjectifSurPoint(p).get("irrigations");
            }
        }
        this.parcellesAPoser=recopieArrayList(resultat);
    }

    /**
     * @return Les parcelles non etang et de la bonne couleur
     */
    ArrayList<Point> iteration_parcelles() {
        ArrayList<Point> parcellesBonneCouleur = new ArrayList<>();
        for (Parcelle parcelle : plateau.getListParcelle()) {
            if ((parcelle.getColor() == objectif.getColor())
                    && !(parcelle.getX() == 0 && parcelle.getY() == 0)) {
                Point p = new Point(parcelle.getX(), parcelle.getY());
                parcellesBonneCouleur.add(p);
            }
        }
        for(Point parcelle:possibility.availablePositions()){
            //parcellesBonneCouleur.add(parcelle);
        }
        return parcellesBonneCouleur;
    }

    /**
     * @param parcelle
     * @return la meilleure liste de parcelles a poser en fonction de la parcelle de référence
     * c'est aussi la liste de parcelle la plus proche du centre ( a irriguer plus facilement donc )
     */
    public Map<String,ArrayList<Point>> superposeObjectifSurPoint(Point parcelle) {
        //System.out.println("parcelle : "+parcelle.x+" , "+parcelle.y);
        ArrayList<Point> irrigationTemporaire = new ArrayList<>();
        ArrayList<Point> irrigation = new ArrayList<>();
        ArrayList<Point> parcelleTemporaire = new ArrayList<>();
        ArrayList<Point> ouPoserParcelles = new ArrayList<>();
        int[][][] points = objectif.getCoordinatesObj();
        for (int k = 0; k < points.length; k++) { //Pour toutes les orientations
            parcelleTemporaire.clear();
            irrigationTemporaire.clear();
            //System.out.println("autre orientation");
            for (int i = 0; i < points[k].length; i++) {// on regarde pour tout les points
                int c = points[k][i][0] + parcelle.x;
                int d = points[k][i][1] + parcelle.y;
                //System.out.println(c+","+d);
                if((c==0)&&(d==0)) {
                    parcelleTemporaire.clear();
                    irrigationTemporaire.clear();
                    break;
                }
                if (plateau.isEmpty(c, d)) {
                    parcelleTemporaire.add(new Point(c, d));
                    irrigationTemporaire.add(new Point(c, d));


                }else if (this.plateau.getParcelleByCoord(c, d).getColor() != objectif.getColor()) {
                    parcelleTemporaire.clear();
                    irrigationTemporaire.clear();
                    break;
                } else if (!this.plateau.getParcelleByCoord(c, d).isParcelleIrrigue()){
                    irrigationTemporaire.add(new Point(c, d));
                }

            }
            //printArrayList(parcelleTemporaire);
            if(meilleurListe(parcelleTemporaire,ouPoserParcelles)){
                ouPoserParcelles=recopieArrayList(parcelleTemporaire);
                irrigation=recopieArrayList(irrigationTemporaire);
            }
        }
        Map<String,ArrayList<Point>> map =new HashMap();
        map.put("parcelles",ouPoserParcelles);
        map.put("irrigations",irrigation);
        return map;

    }
    void printArrayList(ArrayList<Point> liste){
        int i=0;
        for(Point p:liste){
            System.out.println(i+++" x="+p.x+" y="+p.y);
        }
        System.out.println("\n");
    }

    /**
     * @param temporaire
     * @param actuel
     * @return true si il faut remplacer temporaire par actuel
     * false sinon
     */
    boolean meilleurListe(ArrayList<Point> temporaire,ArrayList<Point> actuel){
        if (temporaire.size()==0){
            return false;
        }
        if ((temporaire.size() != 0
                && (temporaire.size() < actuel.size()))
                || actuel.size()==0) {

            return true;
        }
        if(temporaire.size()==actuel.size()){
            return plusProcheDuCentre(temporaire,actuel);
        }

        return false;
    }

    boolean plusProcheDuCentre(ArrayList<Point> temporaire,ArrayList<Point> actuel){
        double distanceActuel=0;
        double distanceTemporaire=0;
        for (Point p:temporaire){
            distanceTemporaire+=distance(p);
        }
        for (Point p:actuel){
            distanceActuel+=distance(p);
        }
        distanceActuel/=3;
        distanceTemporaire/=3;

        if(distanceActuel>distanceTemporaire){
            return true;
        }

        return false;
    }

    public double distance(Point p) {
        double distance;
        float x1=p.x;
        float y1=p.y;
            distance = Math.sqrt((x1) * (x1) + (y1) * (y1));
        return distance;
    }

    public ArrayList<Point> recopieArrayList(ArrayList<Point> source){
        ArrayList<Point> result=new ArrayList<>();
        for(Point p:source){
            result.add(p);
        }
        return result;
    }

    public ArrayList<Point> getParcellesAIrriguer() {
        return parcellesAIrriguer;
    }
    public ArrayList<Point> getParcellesAPoser(){
        return parcellesAPoser;
    }
}


