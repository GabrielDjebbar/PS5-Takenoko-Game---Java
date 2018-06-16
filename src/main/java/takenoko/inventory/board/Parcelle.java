package takenoko.inventory.board;

import takenoko.referee.Possibility;
import takenoko.utils.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parcelle {
    public static final Logger logger= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Point point;
    private String couleur;
    int bamboo;
    ArrayList<Integer> tabIrrigation = new ArrayList<>();
    Set<Segement> tabSegementsAIrriguer = new HashSet<>();
    Set<Segement> tabSegementsIrrigues = new HashSet<>();
    private boolean parcelleIrrigue;
    private Amenagement amenagement = new Amenagement("noamg");


    /**
     * Le constructeur d'une parcelle prend comme paramètre la couleur
     * de la parcelle, et l'initialisation des X et Y, se feront lors de addParcel()
     * on initialise la table d'Irrigation de la parcelle à 0 au début (pts non irrigués)
     * @param couleurp
     */
    // CONSTRUCTEUR
    public Parcelle(String couleurp){
        point=new Point();
        this.couleur=couleurp;
        this.bamboo=0;
        this.parcelleIrrigue=false;

        for(int i = 0; i<6; i++) {tabIrrigation.add(0);}
    }
    public Parcelle(Parcelle p){
        point=p.point;
        couleur=p.couleur;
        bamboo=p.bamboo;
        tabIrrigation=p.tabIrrigation;
        tabSegementsAIrriguer=p.tabSegementsAIrriguer;
        tabSegementsIrrigues=p.tabSegementsIrrigues;
        parcelleIrrigue=p.parcelleIrrigue;
        amenagement=p.amenagement;
    }


    /**
     * changer le statue de la parcelle en irriguée
     */
    public void irriguerParcelle(){
        if (! this.isParcelleIrrigue()){
            this.add1Bamboo();
        }
        this.parcelleIrrigue = true;
    }

    /**
     * irrigue le side 1 dans le tableau d'irrigation
     */
   public void irriguerSide1(){this.tabIrrigation.set(0,1);}

    /**
     * irrigue le side 2 dans le tableau d'irrigation
     */
    public void irriguerSide2(){this.tabIrrigation.set(1,1);}

    /**
     * irrigue le side 3 dans le tableau d'irrigation
     */
   public void irriguerSide3(){this.tabIrrigation.set(2,1);}

    /**
     * irrigue le side 4 dans le tableau d'irrigation
     */
    public void irriguerSide4(){this.tabIrrigation.set(3,1);}

    /**
     * irrigue le side 5 dans le tableau d'irrigation
     */
   public void irriguerSide5(){this.tabIrrigation.set(4,1);}

    /**
     * irrigue le side 6 dans le tableau d'irrigation
     */
   public void irriguerSide6(){this.tabIrrigation.set(5,1);}

    /**
     * Fonction pour savoir si un point de la parcelle est irrigué
     * @param numero du côté de la parcelle
     * @return true or false
     */
    public boolean isSideIrrigue(int numero){
        if (tabIrrigation.get(numero - 1) == 0) {
            return false;
        }
        return true;
    }

    /**
     * La fonction qui assigne a la parcelle une position dans le plateau
     * elle prend en paramètre x, y et les met dans les attributs de la parcelle
     * @param x
     * @param y
     */
    public void setXY(int x, int y){
        point.x = x;
        point.y = y;
    }

    /**
     * @return un String, qui est la couleur de la parcelle
     */
   public String getColor(){
        return couleur;
    }

    /**
     * @return la valeur x de la parcelle
     */
    public int getX(){
        return point.x;
    }

    /**
     * @return la valeur y de la parcelle
     */
    public int getY(){
        return point.y;
    }


    // Fonctions pour savoir si la position de la parcelle
    // actuelle en fonction d'une parcelle donnée en paramètre.


    /**
     * @param p, prend une parcelle e
     * @return rend True si la parcelle actuelle est
     * à droite de la parcelle P, sinon False
     */
    public boolean isADroiteDeLaParcelleDe(Parcelle p){
        if(((this.getX() - 2) == (p.getX()))&&(this.getY() == p.getY())){
            return true;
        }
        return false;
    }
    public boolean isADroiteDeLaParcelleDeLaPosition(int x,int y){
        if(((this.getX() - 2) == x)&&(this.getY() == y)){
            return true;
        }
        return false;
    }

    /**
     * @param p, prend une parcelle e
     * @return rend True si la parcelle actuelle est
     * à gauche de la parcelle P, sinon False
     */
   public boolean isAGaucheDeLaParcelleDe(Parcelle p){
        if(((this.getX() + 2) == (p.getX()))&&(this.getY() == p.getY())){
            return true;
        }
        return false;
    }
    public boolean isAGaucheDeLaPosition(int x,int y){
        if(((this.getX() + 2) == x)&&(this.getY() == y)){
            return true;
        }
        return false;
    }

    /**
     * @param p, prend une parcelle e
     * @return rend True si la parcelle actuelle est
     * en haut à droite de la parcelle P, sinon False
     */
   public boolean isEnHautDroiteDe(Parcelle p){
        if(((this.getX() - 1) == (p.getX()))&&((this.getY() - 1) == p.getY())){
            return true;
        }
        return false;
    }
    public boolean isEnHautDroiteDeLaPosition(int x,int y){
        if(((this.getX() - 1) == x)&&((this.getY() - 1) == y)){
            return true;
        }
        return false;
    }

    /**
     * @param p, prend une parcelle e
     * @return rend True si la parcelle actuelle est
     * en haut gauche de la parcelle P, sinon False
     */
   public boolean isEnHautGaucheDe(Parcelle p){
        if(((this.getX() + 1) == (p.getX()))&&((this.getY() - 1) == p.getY())){
            return true;
        }
        return false;
    }
    public boolean isEnHautGaucheDeLaPosition(int x,int y){
        if(((this.getX() + 1) == x)&&((this.getY() - 1) == y)){
            return true;
        }
        return false;
    }

    /**
     * @param p, prend une parcelle e
     * @return rend True si la parcelle actuelle est
     * en bas gauche de la parcelle P, sinon False
     */
   public boolean isEnBasGaucheDe(Parcelle p){
        if(((this.getX() + 1) == (p.getX()))&&((this.getY() + 1) == p.getY())){
            return true;
        }
        return false;
    }
    public boolean isEnBasGaucheDeLaPosition(int x,int y){
        if(((this.getX() + 1) == x)&&((this.getY() + 1) == y)){
            return true;
        }
        return false;
    }

    /**
     * @param p, prend une parcelle e
     * @return rend True si la parcelle actuelle est
     * en bas droite de la parcelle P, sinon False
     */
   public boolean isEnBasDroiteDe(Parcelle p){
        if(((this.getX() - 1) == (p.getX()))&&((this.getY() + 1) == p.getY())){
            return true;
        }
        return false;
    }
    public boolean isEnBasDroiteDeLaPosition(int x,int y){
        if(((this.getX() - 1) == x)&&((this.getY() + 1) == y)){
            return true;
        }
        return false;
    }

    /**
     * @return true or false, si la parcelle est irriguée ou non.
     */
   public boolean isParcelleIrrigue(){
        return parcelleIrrigue;
    }

    /** Fonction qui irrigue un side de la parcelle par son numéro
     * et irrigue les points communs des parcelles voisins
     * @param numero compris entre 1 et 6. qui est le point concerné de la parcelle
     */
    public void irriguerSide(int numero) {
        if (numero == 1) {
            this.irriguerSide1();
        }
        if (numero == 2) {
            this.irriguerSide2();
        }

        if (numero == 3) {
            this.irriguerSide3();
        }

        if (numero == 4) {
            this.irriguerSide4();
        }
        if (numero == 5) {
            this.irriguerSide5();
        }
        if (numero == 6) {
            this.irriguerSide6();
        }
    }

    /**
     * Une fonction qui irrigue l'étang, et ses points par default.
     */
   void initializeEtang(){
        this.parcelleIrrigue = true;
        this.irriguerSide1();
        this.irriguerSide2();
        this.irriguerSide3();
        this.irriguerSide4();
        this.irriguerSide5();
        this.irriguerSide6();
    }

    /**
     * Une fonction qui irrigue les points possibles de la parcelle
     * appelée à chaque fois qu'une parcelle est déposée sur le plateau
     */
   public void irriguePtsIfPossible(Plateau pl){
        Possibility possibility = new Possibility(pl);
        ArrayList<Parcelle> res = possibility.getNeighboorParcellesByCoord(this.getX(),this.getY());
        for (Parcelle p : res) {
            if(this.isADroiteDeLaParcelleDe(p)){
                if(p.isSideIrrigue(5)){this.irriguerSide3();}
                if(p.isSideIrrigue(6)){this.irriguerSide2();}
            }

            if(this.isAGaucheDeLaParcelleDe(p)){
                if(p.isSideIrrigue(3)){this.irriguerSide5();}
                if(p.isSideIrrigue(2)){this.irriguerSide6();}
            }

            if(this.isEnBasDroiteDe(p)){
                if(p.isSideIrrigue(4)){this.irriguerSide2();}
                if(p.isSideIrrigue(5)){this.irriguerSide1();}
            }

            if(this.isEnBasGaucheDe(p)){
                if(p.isSideIrrigue(3)){this.irriguerSide1();}
                if(p.isSideIrrigue(4)){this.irriguerSide6();}
            }

            if(this.isEnHautGaucheDe(p)){
                if(p.isSideIrrigue(1)){this.irriguerSide5();}
                if(p.isSideIrrigue(2)){this.irriguerSide4();}
            }

            if(this.isEnHautDroiteDe(p)){
                if(p.isSideIrrigue(6)){this.irriguerSide4();}
                if(p.isSideIrrigue(1)){this.irriguerSide3();}
            }
        }
    }

    /**
     * Vérifier qu'il ya deux points conséqutives irrigués
     * pour changer l'état de la parcelle en irriguée.
     */
    private void irriguerParcelleSiPossible(){
        boolean res = false;
        for (int i = 0; i<6; i++){
            if(((tabIrrigation.get(0) == 1)) && (tabIrrigation.get(1)==1)){
                res = true;
            }

            if(((tabIrrigation.get(0) == 1)) && (tabIrrigation.get(5)==1)){
                res = true;
            }

            if(((tabIrrigation.get(5) == 1)) && (tabIrrigation.get(4)==1)){
                res = true;
            }

            if(((tabIrrigation.get(5) == 1)) && (tabIrrigation.get(0)==1)){
                res = true;
            }


            if(((tabIrrigation.get(i) == 1) && ((i>0) && (i<5))) && ((tabIrrigation.get(i+1) == 1))){
                res = true;
            }

            if(((tabIrrigation.get(i) == 1) && ((i>0) && (i<5))) && ((tabIrrigation.get(i-1) == 1))){
                res = true;
            }
        }
        if (res){
            this.irriguerParcelle();
        }
    }

    /**
     * Une fonction qui transforme les points de la parcelle
     * en segments possibles à irrigués, et les met dans un set
     */
    private void pointsEnSegemntsAIrriguer(){
        tabSegementsAIrriguer.clear();
        Segement s;
        for (int i = 0; i<6; i++){
            if((tabIrrigation.get(0) == 1)&&(i==0)){
                if (tabIrrigation.get(1)== 0){
                    s = new Segement(1, 2,this);
                    tabSegementsAIrriguer.add(s);
                }

                if (tabIrrigation.get(5) == 0){
                    s = new Segement(1, 6,this);
                    tabSegementsAIrriguer.add(s);
                }
            }

            if((tabIrrigation.get(5) == 1)&&(i==5)){
                if (tabIrrigation.get(4)== 0){
                    s = new Segement(6, 5,this);
                    tabSegementsAIrriguer.add(s);
                }
                if (tabIrrigation.get(0)== 0){
                    s = new Segement(6, 1,this);
                    tabSegementsAIrriguer.add(s);
                }
            }

            if(((tabIrrigation.get(i) == 1) && ((i>0) && (i<5))) && ((tabIrrigation.get(i+1) == 0))){
                s = new Segement(i+1, i+2,this);
                tabSegementsAIrriguer.add(s);
            }

            if(((tabIrrigation.get(i) == 1) && ((i>0) && (i<5))) && ((tabIrrigation.get(i-1) == 0))){
                s = new Segement(i+1, i,this);
                tabSegementsAIrriguer.add(s);
            }
        }
    }


    /**
     * Une fonction qui transforme les points de la parcelles
     * en segements irrigués, let met dans un set
     */
    private void pointsEnSegemntsIrrigues(){
        tabSegementsIrrigues.clear();
        Segement s;
        for (int i = 0; i<6; i++){
            if((tabIrrigation.get(0) == 1)&&(i==0)){
                if (tabIrrigation.get(1)== 1){
                    s = new Segement(1, 2,this);
                    tabSegementsIrrigues.add(s);
                }

                if (tabIrrigation.get(5) == 1){
                    s = new Segement(1, 6,this);
                    tabSegementsIrrigues.add(s);
                }
            }


            if(((tabIrrigation.get(i) == 1) && ((i>0) && (i<5))) && ((tabIrrigation.get(i+1) == 1))){
                s = new Segement(i+1, i+2,this);
                tabSegementsIrrigues.add(s);
            }
        }
    }


    /**
     * un getter de TabDeSegements possibles de la parcelle
     * @return un set des segments possibles a irriguer
     */
   public Set<Segement> getTabSegementsAIrriguer(){
       pointsEnSegemntsAIrriguer();
       return tabSegementsAIrriguer;
   }


    /**
     * Un getter de TabSegemnts irrigués de la parcelle
     * @return un set de segments irrigués
     */
   public Set<Segement> getTabSegIrrigues(){
       pointsEnSegemntsIrrigues();
       return tabSegementsIrrigues;
   }


    /**
     * Une fonction qui met à jour la parcelle en irriguant
     * les points si possible, et transformer les points en segments
     */
    public void majParcelle(Plateau pl){
        irriguePtsIfPossible(pl);
        irriguerParcelleSiPossible();
        pointsEnSegemntsAIrriguer();
        pointsEnSegemntsIrrigues();
    }

    /**
     * Une fonction qui log les segments de la parcelle
     * pour pouvoir faire les DEBUG.
     */
    public void printDataSegments(){
        String data = "\n--------------ETUDE DE SEGMENTS----------------" ;
        for(Segement s : this.getTabSegementsAIrriguer()){
            data += ("\n " + s.printSegement());
        }
        logger.log(Level.INFO,data);
    }

    /**
     * Une fonction qui print un log de tout ses attributs
     * pour savoir dans quel état elle est. Pour DEBUG.
     */
    public void printDataParcelle(){
        String data = "\n-------------PRINT DATA OF PARCELLE-----------------" +
                "\nréférence:" + this.toString() +
                "\ncolor:" + this.getColor() +
                "\ncoordinates: x:" + this.getX() + " y:" + this.getY() +
                "\npoint1:" + this.isSideIrrigue(1) +
                "\npoint2:" + this.isSideIrrigue(2) +
                "\npoint3:" + this.isSideIrrigue(3) +
                "\npoint4:" + this.isSideIrrigue(4) +
                "\npoint5:" + this.isSideIrrigue(5) +
                "\npoint6:" + this.isSideIrrigue(6) +
                "\nparcelle irriguée? : " + this.isParcelleIrrigue()+
                "\nliste pts d'irrigation de la parcelle :" + this.tabIrrigation.toString()+
                "\nsize de la liste pts d'irrigation de la parcelle: " + this.tabIrrigation.size() +
                "\nliste segements possible a irriguer :" + this.getTabSegementsAIrriguer().toString()+
                "\nsize de la liste de segments possible à irriguer: " + this.getTabSegementsAIrriguer().size();
        logger.log(Level.INFO,data);
    }

    public void add1Bamboo(){
        if(getBamboo()<4){
            this.bamboo++;
            String toPrint =  "/!\\ ACTION /!\\ UN BAMBOO POUSSE DANS LA PARCELLE [" + this.point.x + "," + this.point.y + "]";
            Log.logger.log(Level.FINE, toPrint);
        }
    }

    public void add2Bamboo(){
        if (this.bamboo<=2){
            this.bamboo=this.bamboo+2;
        }
        else if (this.bamboo==3) {
            this.bamboo=4; }
    }

    /**
     * function useful for tests, and namely the Validation Class tests.
     */
    public void setBamboo(int choosenNumber){
        this.bamboo=choosenNumber;
    }

    public int getBamboo(){
        return this.bamboo;
    }

    public void remove1BambooEaten(){
        this.bamboo+=-1;
    }


    /**
     * Une fonction qui set un amenagement a la parcelle
     * @param amenagement prend un amenagemnt et le met dans parcelle
     */
    public void setAmenagement(Amenagement amenagement){
        this.amenagement = amenagement;
    }


    /**
     * @return retourne l'Aménagement de la parcelle
     */
    public Amenagement getAmenagement(){
        return this.amenagement;
    }

    public void printCoord(){
        System.out.println("coordonnées de la parcelle (" + this.getX() + "," + this.getY() + ")");
    }

}


