package takenoko.inventory.objective;


/**
 * Sous-classe d'objectif, un objectif type panda est un objet
 * qui est composé d'un certain nombre de bambos, et un score
 * l'objectif aidera le joueur a déterminer les endroits ou il faut manger
 * les Bambo.
 */
public class ObjectifsTypePanda extends Objectif {

    private int greenBambo;
    private int pinkBambo;
    private int yellowBambo;


    /**
     * Constructeur des instances ObjectifsTypePanda
     * @param green nb de bambos verts demandés
     * @param pink nb de bambos roses demandés
     * @param yellow nb de bambos jaunes demandés
     * @param score le score gagné par le lors de la validation de l'objectif
     * Initialisera les fields de l'object, et le score.
     */
    public ObjectifsTypePanda(int green, int pink, int yellow, int score){
        super("Panda","multicolor");
        this.greenBambo = green;
        this.pinkBambo = pink;
        this.yellowBambo = yellow;
        if(this.greenBambo == 2){this.color = "Green";}
        if(this.pinkBambo == 2){this.color = "Pink";}
        if(this.yellowBambo == 2){this.color = "Yellow";}
        this.nbDePoints = score;
    }



    /**
     * @return le nombre de bambo verts demandés pour valider l'objectif
     */
    public int getNbGreenBambo(){
        return greenBambo;
    }

    /**
     * @return le nombre de bambo roses demandés pour valider l'objectif
     */
    public int getNbPinkBambo(){
        return pinkBambo;
    }

    /**
     * @return le nombre de bambo jaunes demandés pour valider l'objectif
     */
    public int getNbYellowBambo(){
        return yellowBambo;
    }

}
