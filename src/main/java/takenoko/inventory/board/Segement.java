package takenoko.inventory.board;

import java.util.*;



public class Segement {
    private Set<Integer> set = new HashSet<Integer>();
    private Parcelle parcelle;
    private int p1;
    private int p2;
    private float[] pointcentral;

    /**
     * Constructeur
     * @param pt1 un point de la parcelle
     * @param pt2 un point de la parcelle
     * @param parcelle une référence de la parcelle contenant le segement
     */
    public Segement(int pt1, int pt2, Parcelle parcelle){
        this.parcelle = parcelle;
        set.add(pt1);
        set.add(pt2);
        p1=pt1;
        p2=pt2;
        this.setPointcentral();
    }

    /**
     * getter du set du segemnt
     * @return set of Integers
     */
   public Set<Integer> getSet(){
        return this.set;
    }

    public Parcelle getParcelle(){return this.parcelle;}

    /**
     * @return String du set de segement
     * et la référence pour debug
     */
    public String printSegement(){
        return set.toString()+" sur la parcelle "+parcelle.getX()+" "+parcelle.getY();
    }


    public int getP1() {
        return p1;
    }

    public int getP2() {
        return p2;
    }

    public void setPointcentral() {
        float[] endroit_point={0,0};
        endroit_point[0]=(float)this.getParcelle().getX();
        endroit_point[1]=(float)this.getParcelle().getY();
        if ((this.p2==1 && this.p1== 2)||(this.p1==2 && this.p2== 2)){
            endroit_point[0]-=0.5;
            endroit_point[1]+=0.5;
        }
        if ((this.p2==2 && this.p1== 3)||(this.p1==2 && this.p2== 3)){
            endroit_point[0]-=1;
            endroit_point[1]-=0;
        }
        if ((this.p2==3 && this.p1== 4)||(this.p1==3 && this.p2== 4)){
            endroit_point[0]-=0.5;
            endroit_point[1]-=0.5;
        }
        if ((this.p2==5 && this.p1== 4)||(this.p1==5 && this.p2== 4)){
            endroit_point[0]+=0.5;
            endroit_point[1]-=0.5;
        }
        if ((this.p2==5 && this.p1== 6)||(this.p1==5 && this.p2== 6)){
            endroit_point[0]+=1;
            endroit_point[1]+=0;
        }
        if ((this.p2==1 && this.p1== 6)||(this.p1==1 && this.p2== 6)){
            endroit_point[0]+=0.5;
            endroit_point[1]+=0.5;
        }
        this.pointcentral= endroit_point;
    }

    public float[] getPointcentral() {
        return pointcentral;
    }
}