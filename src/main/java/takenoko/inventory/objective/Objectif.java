package takenoko.inventory.objective;

/**
 * this class represents the different type of objectives and is re-used through inheritance.
 */
public class Objectif {
    String type;
    String color;
    protected int nbDePoints;

    private boolean valid;
    /**
     * This is the constructor
     * @param type
     * @param color
     */
    public Objectif(String type, String color){
        this.type = type;
        this.color = color;
        valid=false;
    }

    public boolean IsValidated(){
        return valid;
    }

    public void validate(){
        valid=true;
    }


    /**
     * @return the type of the objective
     */
    public String getType() {
        return type;
    }

    /**
     * @return the color of the objective
     */
    public String getColor(){
        return color;
    }


    /**
     * @param obj1
     * @return true if two objectives are equal.
     */
    // no need actually - ISLEM  , NOT COMPLETE
    boolean ifTwoObjectifsEquals(Objectif obj1) {
        if ((obj1.getType().equals(this.getType())) ) {
            return true;
        }
        return false;
    }

    public int getNbDePoints() {
        return nbDePoints;
    }

}




