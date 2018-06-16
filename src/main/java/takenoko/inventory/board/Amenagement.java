package takenoko.inventory.board;

/**
 * Un aménagement est un objet, qui aura un type précis
 * Les types sont autorisés sont : nopanda, bassin, doublebambo
 */
public class Amenagement {

    String type;

    public Amenagement(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
