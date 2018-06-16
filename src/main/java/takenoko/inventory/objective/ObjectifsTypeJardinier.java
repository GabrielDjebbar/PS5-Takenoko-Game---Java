package takenoko.inventory.objective;

import takenoko.inventory.characters.Gardener;

public class ObjectifsTypeJardinier extends Objectif {
    protected int numberOfBamboo;
    protected int lengthOfBamboo;


    public ObjectifsTypeJardinier(String color, int numberOfBamboo, int lengthOfBamboo) {
        super("Gardener", color);
        this.numberOfBamboo = numberOfBamboo;
        this.lengthOfBamboo = lengthOfBamboo;
        this.nbDePoints = this.calculPoints();
    }

    private int calculPoints() {

        if (this.color.equals("Pink") && this.numberOfBamboo==1&& this.lengthOfBamboo == 4 )  {
            return 5;
        }

        if (this.color.equals("Pink")&& this.numberOfBamboo==2 && this.lengthOfBamboo==3){
            return 6;
        }

        if (this.color.equals("Pink") && this.numberOfBamboo==3 && this.lengthOfBamboo == 3 ) {
            return 7;
        }
        if (this.color.equals("Yellow") && this.numberOfBamboo==1 && this.lengthOfBamboo == 4 ) {
            return 5;
        }

        if (this.color.equals("Yellow")&& this.numberOfBamboo==2 && this.lengthOfBamboo==3){
            return 6;
        }

        if (this.color.equals("Yellow") && this.numberOfBamboo==3 && this.lengthOfBamboo == 3 ) {
            return 7;
        }

        if (this.color.equals("Green") && this.numberOfBamboo==1 && this.lengthOfBamboo == 4 ) {
            return 5;
        }

        if (this.color.equals("Green")&& this.numberOfBamboo==2 && this.lengthOfBamboo==3){
            return 6;
        }

        if (this.color.equals("Green") && this.numberOfBamboo==3 && this.lengthOfBamboo == 3 ) {
            return 7;
        }

        if (this.color.equals("Green")&&this.numberOfBamboo==3&&this.lengthOfBamboo==4){
            return 8;
        }
    return 0;}

    public int getNumberOfBamboo() {
        return numberOfBamboo;
    }

    public int getLengthOfBamboo() {
        return lengthOfBamboo;
    }

}