package takenoko.referee;

import takenoko.inventory.objective.ObjectifsTypeJardinier;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.player.Fiche;
import takenoko.utils.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * class to validate for every type of objectif
 */
public class Validation {
    private Plateau plateau;
    private Possibility possibility;


    public Validation(Plateau pl) {
        plateau = pl;
        possibility = new Possibility(plateau);
    }

    /**
     * check validation for every objectif in fiche
     */
    public void checkValidation(Fiche fiche) {
        for (ObjectifsTypeParcelle obj : fiche.getObjectifsTypeParcelle()) {
            for (Parcelle parc : possibility.getListOfParcelByColor(obj.getColor())) {
                if (obj.IsValidated()) continue;
                if (parc.getX() == 0 && parc.getY() == 0) continue;
                if (parc.isParcelleIrrigue() && isObectifValid(obj, parc,fiche)){
                    validate(obj, fiche);
                    //System.out.println("OBJECTIF PARCEL VALIDE");
                }
            }
        }

        for (ObjectifsTypePanda objPanda : fiche.getObjectifsTypePanda()) {
            if (!objPanda.IsValidated()) {
                checkValidationPanda(objPanda,fiche);
            }
        }


        for (ObjectifsTypeJardinier obj : fiche.getObjectifsTypeJardinier()) {
            int a = obj.getLengthOfBamboo();
            int b = obj.getNumberOfBamboo();
            int count = 0;
            for (Parcelle parc : possibility.getListOfParcelByColor(obj.getColor())) {
                if (parc.getBamboo() == a)
                    count += 1;}
            if ((count==b)&&!obj.IsValidated()){

                //System.out.println("OBJECTIF JARDINIER VALIDE");
                validate(obj,fiche);
                Log.logger.log(Level.INFO, "TEST VALIDATION OBJECTIF JARDINIER");
            }
        }
    }


    /**
     * @param obj validate a given objectif
     */
     public void validate(ObjectifsTypeParcelle obj,Fiche fiche) {
         obj.validate();
         fiche.addScore(obj.getNbDePoints());
         fiche.incrementNbObjectifValider();
         //fiche.removeObjectifParcelle(obj);
     }

     public void validate(ObjectifsTypePanda obj,Fiche fiche){
         String toPrint = "Objectif Panda with color " + obj.getColor() +
                 ", and with Green Bamboo : "+obj.getNbGreenBambo() +
                 ", and with Yeloow Bamboo : "+obj.getNbYellowBambo()+
                 ", and with Pink Bamboo : "+obj.getNbPinkBambo() +" has been validated with " + obj.getNbDePoints() + " points par " + fiche.getjName();
         Log.logger.log(Level.INFO, toPrint);
         obj.validate();
         fiche.addScore(obj.getNbDePoints());
         fiche.incrementNbObjectifValider();
         //fiche.removeObjectifPanda(obj);

     }

    public void validate(ObjectifsTypeJardinier obj, Fiche fiche) {
        String toPrint = "Objectif Jardinier with color " + obj.getColor() +"and"+obj.getNumberOfBamboo()+ "bamboo of length "+
                obj.getLengthOfBamboo() +"has been validated with " + obj.getNbDePoints() + " points par " + fiche.getjName();
        Log.logger.log(Level.INFO, toPrint);
        obj.validate();
        fiche.addScore(obj.getNbDePoints());
        fiche.incrementNbObjectifValider();
        //fiche.removeObjectifJardi(obj);
    }


    public void checkValidationPanda(ObjectifsTypePanda objPanda, Fiche fiche){
        if (objPanda.getColor().equals("multicolor")) {
            if ((fiche.getNbPinkBambo() >= 1) && (fiche.getNbGreenBambo() >= 1) && (fiche.getNbYellowBambo() >= 1)) {
                validate(objPanda,fiche);

                //System.out.println("OBJECTIF PANDA VALIDE");
                fiche.substractNbGreenBambo(1);
                fiche.substractNbPinkBambo(1);
                fiche.substractNbYellowBambo(1);
            }
        }
        else{
            if (fiche.getNbGreenBambo() >= 2) {
                fiche.substractNbGreenBambo(2);
                validate(objPanda,fiche);
            }
            if (fiche.getNbPinkBambo() >= 2) {
                fiche.substractNbPinkBambo(2);
                validate(objPanda,fiche);
        }
            if (fiche.getNbYellowBambo() >= 2) {
                fiche.substractNbYellowBambo(2);
                validate(objPanda,fiche);
            }
        }

    }

    /**
     * checks if parcels are of same color,
     * checks if parcel are irrigated
     * checks if parcels are have the right geometry
     *
     * @param obj  objectif to be checked for validation
     * @param parc parcelle to be checked on
     * @return true if valid objectif
     */
    public Boolean isObectifValidInitial(ObjectifsTypeParcelle obj, int i, Parcelle parc) {
        int validationCoord[][][] = obj.getCoordinatesObj();
        String objColor = obj.getColor();
        for (int j = 0; j < validationCoord[i].length; j++) {
            int x = validationCoord[i][j][0] + parc.getX();
            int y = validationCoord[i][j][1] + parc.getY();
            Parcelle currentParcel = plateau.getParcelleByCoord(x, y);
            if ((x == 0 && y == 0) || plateau.isEmpty(x, y) || !currentParcel.isParcelleIrrigue() || !currentParcel.getColor().equals(obj.getColor())) {
                return false;
            }
        }
        return true;
    }


    public Boolean isObectifValid(ObjectifsTypeParcelle obj, Parcelle parc,Fiche fiche) {
        ArrayList<Point> objGeometryCoordToPrint=new ArrayList<Point>();
        for (int i = 0; i < obj.getCoordinatesObj().length; i++) {
            if(isObectifValidInitial(obj,i,parc) ){
                int tmp[][]= obj.getCoordinatesObj()[i];
                for(int j=0; j<tmp.length;j++)
                    objGeometryCoordToPrint.add(new Point(parc.getX()+tmp[j][0],parc.getY() + tmp[j][1]));
                toPrint(obj,objGeometryCoordToPrint,fiche);
                //objGeometryCoordToPrint.add(new Point(parc.getX(),parc.getY()));

                return true;

            }
        }
        return false;
    }

    public void toPrint(ObjectifsTypeParcelle obj, List<Point> geom,Fiche fiche){
        String toPrint = "Objectif " + obj.getGeometry() + "with color " + obj.getColor() + " " +
                "has been validated with " + obj.getNbDePoints() + " points par " + fiche.getjName() +
                " et avec les parcelle " + obj.getGeometry() + " ";
        for(Point p : geom)
            toPrint+= "(" + p.x + ", " + p.y + ") ";
        Log.logger.log(Level.INFO, toPrint);

    }


/*
    public Boolean isObectifValidBISBIS(ObjectifsTypeParcelle obj, Parcelle parc) {
        int validationCoord[][][] = obj.getCoordinatesObj();
        int compteurOrientation = 0; // compteur qui permettra de compter si toutes les orientations ont été testé
        int compteurOrientationbis = 0;
        String objColor = obj.getColor();

        for (int nbOrientation = 0; nbOrientation < validationCoord.length; nbOrientation++) { // va balayer les différentes coordonnées
            for (int i = 1; i < validationCoord[0].length; i++) { // pour balayer une orientation donnée en fonction du nombre de parcelles
                int x = validationCoord[nbOrientation][i][0] + parc.getX();
                int y = validationCoord[nbOrientation][i][1] + parc.getY();
                Parcelle neighbourParc = plateau.getParcelleByCoord(x, y);
                if ((x == 0 && y == 0) || plateau.isEmpty(x, y) || !neighbourParc.isParcelleIrrigue() || !neighbourParc.getColor().equals(obj.getColor())) {
                    compteurOrientationbis += 1; // cela veux dire que pour cette orientation donnée et pour ce point de départ donné ( par rapport à la géométrie), la parcelle ne complète pas l'obectif
                }
            }
            if (compteurOrientationbis == validationCoord[0].length) {
                compteurOrientation += 1;
                compteurOrientationbis = 0;
            } else {
                return true;

            }
        }
        if (compteurOrientation == validationCoord.length) {
            return false;
        }
        else {
            String toPrint= "Objectif to be validated geometry " + obj.getGeometry() + " ";
            for(Point p:objGeometryToPrint){
                toPrint += "("+p.x +","+ p.y+ ") ";
            }

            Log.logger.log(Level.INFO,toPrint);
        } else
            return true;

        }

    }*/


}