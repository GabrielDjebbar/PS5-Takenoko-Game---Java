package takenoko.controller;


import java.util.*;

public class Meteo {
    boolean premierCoup=false;
    private String type;
    private Map<String,Integer> coupsPossibles = new HashMap<>();
    private int nbCoupsLeft;


    public Meteo(){
        this.nbCoupsLeft=2;
        setType();
        setCoupsPossibles();
    }
    public boolean play(){
        this.nbCoupsLeft--;
        if (nbCoupsLeft<0){
            return false;
        }
        return true;
    }
    public void decrementNbCoupsLeft(){
        nbCoupsLeft--;
    }

    public int getNbCoupsLeft(){
        return this.nbCoupsLeft;
    }

    /**
     * cette fonction sert a mettre l'objet meteo dans son etat initial
     * avec un nouveau type et les coupsPossibles remis a 0
     */
    public void resetMeteo(){
        nbCoupsLeft=2;
        setType();
        coupsPossibles.clear();
        setCoupsPossibles();
    }

    public void setType() {
        Stack<String> meteo = new Stack<>();
        meteo.push("Soleil");
        meteo.push("Pluie");
        meteo.push("Vent");
        meteo.push("Eclair");
        meteo.push("Nuages");
        meteo.push("?");
        Collections.shuffle(meteo);
        this.type = meteo.peek();
    }

    public void setCoupsPossibles(){

        switch(type){
            case "Soleil": // On a 3 coups
                nbCoupsLeft=3;
                leJoueurPeuxToutFaire(1);
                break;
            case "Pluie":  // Poser un bambou=
                leJoueurPeuxToutFaire(1);

                break;
            case "Vent" :  // Peux faire deux actions identiques
                leJoueurPeuxToutFaire(2);
                break;
            case "Eclair": // Deplace le panda
                //nbCoupsLeft=3;
                leJoueurPeuxToutFaire(1);
                coupsPossibles.put("Panda",2);
                break;
            case "Nuages" :// Prendre 1 aménagement de la réserve. SOIT le stocker sur son plateau, SOIT le poser.
                leJoueurPeuxToutFaire(1);
                break;
            case "?" :     // On choisit une meteo au hasard
                leJoueurPeuxToutFaire(1);
                break;
        }

    }
    private void leJoueurPeuxToutFaire(int i){
        coupsPossibles.put("Parcelle",i);
        coupsPossibles.put("Irrigation",i);
        coupsPossibles.put("Panda",i);
        coupsPossibles.put("Jardinier",i);
        coupsPossibles.put("Objectif",i);


    }
    public void choisirMeteo(String type){
        this.type=type;
        nbCoupsLeft=2;
        setCoupsPossibles();
    }
    public String getType(){
        return type;
    }


    public void resetType(){
        this.type="does not matter";
    }

    public void joueParcelle(){
        coupsPossibles.put("Parcelle",coupsPossibles.get("Parcelle")-1);
    }

    public void prendAmenagement(){
        coupsPossibles.put("Amenagement",coupsPossibles.get("Amenagement")-1);
    }


    public void joueIrrigation(){
        coupsPossibles.put("Irrigation",coupsPossibles.get("Irrigation")-1);
    }
    public void jouePanda(){
        coupsPossibles.put("Panda",coupsPossibles.get("Panda")-1);
    }
    public void joueJardinier(){
        coupsPossibles.put("Jardinier",coupsPossibles.get("Jardinier")-1);
    }
    public void joueObjectif(){
        coupsPossibles.put("Objectif",coupsPossibles.get("Objectif")-1);
    }
    public Map<String,Integer> getCoupsPossibles(){
        return coupsPossibles;
    }
    public void jouePremierCoup(){
        premierCoup=true;
    }
    public boolean getPremierCoup(){
        return premierCoup;
    }
    public void ajouteUnCoupPossible(){this.nbCoupsLeft++;}
}
