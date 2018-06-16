package takenoko.player;

import takenoko.controller.Meteo;
import takenoko.inventory.board.Amenagement;
import takenoko.inventory.objective.Objectif;
import takenoko.inventory.objective.ObjectifsTypeJardinier;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.inventory.objective.ObjectifsTypeParcelle;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Fiche {
    private final Logger logger= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private String jName;
    private ArrayList<Objectif> jObjectifs;
    private ArrayList<ObjectifsTypeParcelle> jObjectifsTypeParcelle;
    private ArrayList<ObjectifsTypePanda> jObjectifsTypePanda;
    private ArrayList<ObjectifsTypeJardinier> jObjectifsTypeJardinier;
    private ArrayList<Amenagement> amenagements = new ArrayList<>();
    private int score;
    private int nbIrrigations;
    private int nbObjectifValider;
    private int nbBambooGreen;
    private int nbBambooYellow;
    private int nbBambooPink;




    /**
     * constructeur de la fiche
     * @param name, prend le nom du joueur pour l'initialisation
     * initialise les objectifs, nom, score, nb d'irrigations, et nb d'obj validées
     */
    public Fiche(String name){
        jObjectifs = new ArrayList<>();
        jObjectifsTypeParcelle= new ArrayList<>();
        jObjectifsTypePanda= new ArrayList<>();
        jObjectifsTypeJardinier= new ArrayList<>();
        this.jName = name;
        this.score = 0;
        this.nbIrrigations = 0;
        this.nbBambooGreen=0;
        this.nbBambooYellow=0;
        this.nbBambooPink=0;
        nbObjectifValider=0;

    }


    /**
     * @param jName, Setter du nom du joueur dans la fiche
     */
    public void setjName(String jName) {
        this.jName = jName;
    }


    /**
     * @return String, nom du joueur.
     */
    public String getjName() {
        return jName;
    }


    /**
     * @param score, définit le score du joueur (pour les tests)
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return integer, score du joueur.
     */
    public int getScore() {
        return score;
    }

    /**
     * ajoute le score du joueur
     * @param score, est le nombre de points a ajouter au score
     */
    public void addScore(int score) {
        this.score = this.score + score;
    }

    /**
     * Imprime le score du joueur.
     */
    public void printScore(){
        logger.log(Level.INFO,"The score is: " + this.score);
    }

    /**
     * @return ArrayList of the player's objectif.
     */
    public ArrayList<Objectif> getObjectifs() {
        return jObjectifs;
    }

    public int sizeOfNoneValidatedObjTotal(){
        return sizeOfNoneValidatedPandaObj()+sizeOfNoneValidatedGardenerObj()+sizeOfNoneValidatedParcelObj();
    }

    public int sizeOfNoneValidatedPandaObj(){
        return (int)jObjectifsTypePanda.stream().filter(obj -> !obj.IsValidated()).count();
    }
    public int sizeOfNoneValidatedGardenerObj(){
        return (int)jObjectifsTypeJardinier.stream().filter(obj -> !obj.IsValidated()).count();
    }
    public int sizeOfNoneValidatedParcelObj(){
        return (int)jObjectifsTypeParcelle.stream().filter(obj -> !obj.IsValidated()).count();
    }
    /**
     * @return une liste d'objet ObjectifTypeParcelle de la fiche du joueur.
     */
    public ArrayList<ObjectifsTypeParcelle> getObjectifsTypeParcelle(){
        return jObjectifsTypeParcelle;
    }

    /**
     * @return un Objet ObjectifTypePanda de la fiche du joueur.
     */
    public ArrayList<ObjectifsTypePanda> getObjectifsTypePanda(){
        return jObjectifsTypePanda;
    }

    public ArrayList<ObjectifsTypeJardinier> getObjectifsTypeJardinier(){
        return jObjectifsTypeJardinier;
    }

    /**
     * ajoute un Object Objectif in the Arraylist jObjectif
     * @param obj
     */
    public void addObjectifTypeParcelle(ObjectifsTypeParcelle obj){
        jObjectifsTypeParcelle.add(obj);
        jObjectifs.add(obj);
    }
    /**
     * ajoute un Object Objectif in the Arraylist jObjectif
     * @param obj
     */
    public void addObjectifTypePanda(ObjectifsTypePanda obj){
        jObjectifsTypePanda.add(obj);jObjectifs.add(obj);
    }
    public void addObjectifTypeJardinier(ObjectifsTypeJardinier obj){
        jObjectifsTypeJardinier.add(obj);jObjectifs.add(obj);
    }

    /**
     * une fonction qui incrémente les batons d'irrigation
     * si il choisit de prendre une irrigation
     */
   public  void addIrrigation(){
        this.nbIrrigations += 1;
    }

    public  void enleveIrrigation(){
        this.nbIrrigations -= 1;
    }

    /**
     * une fonction qui peut servir pour savoir combien
     * de batons d'irrigations le joueur possède.
     * @return le nombre de batons d'irrigation du joueur
     */
    public int getNbIrrigations(){
        return this.nbIrrigations;
    }


    /**
     * Incrémente le nombre d'objectifs validées
     */
    public void incrementNbObjectifValider() {
        nbObjectifValider++ ;
    }

    /**
     * @return un integer, le nombre d'objectifs validées par le joueur
     */
    public int getNbObjectifValider() {
        return nbObjectifValider;
    }

    /**
     * Fonction pour retirer un certain nombre de bambo roses de la fiche
     * @param n, int représentant le nombre de bambo a enlever de la fiche
     */
    public void substractNbPinkBambo(int n){
        this.nbBambooPink = this.nbBambooPink - n;
    }

    /**
     * Fonction pour retirer un certain nombre de bambo vertes de la fiche
     * @param n, int représentant le nombre de bambo a enlever de la fiche
     */
    public void substractNbGreenBambo(int n){
        this.nbBambooGreen = this.nbBambooGreen - n;
    }

    /**
     * Fonction pour retirer un certain nombre de bambo jaunes de la fiche
     * @param n, int représentant le nombre de bambo a enlever de la fiche
     */
    public void substractNbYellowBambo(int n){
        this.nbBambooYellow = this.nbBambooYellow - n;
    }


    /**
     * @return le nombre de bambo rose de la fiche
     */
    public int getNbPinkBambo(){
        return nbBambooPink;
    }

    /**
     * @return le nombre de bambo vert de la fiche
     */
    public int getNbGreenBambo(){
        return nbBambooGreen;
    }

    /**
     * @return le nombre de bambo jaunes de la fiche
     */
    public int getNbYellowBambo(){
        return nbBambooYellow;
    }

    public void addYellowBamboToFiche(){
        this.nbBambooYellow++;
    }

    public void addPinkBamboToFiche(){
        this.nbBambooPink++;
    }

    public void addGreenBamboToFiche(){
        this.nbBambooGreen = this.nbBambooGreen + 1;
    }

    /**
     * function that will add a bamboo to a given attribute depending on the parameter couleur
     * @param couleur
     */
    public void addBambooEatenToFiche(String couleur){
        if (couleur.equals("Yellow")){
            addYellowBamboToFiche();
        }
        if (couleur.equals("Pink")){
            addPinkBamboToFiche();
        }
        if (couleur.equals("Green")){
            addGreenBamboToFiche();
        }
    }

    /**
     * /!\ IMPORTANT
     * Une fonction a utiliser SEULEMNT après vérification
     * de l'existence de l'Aménagement dans la fiche avec
     * les fonctions nbAmBassin(), nbAmNoPanda()....
     * elle renvoit un aménagement et le supprime de la fiche
     * @param type
     * @return
     */
    public Amenagement useAmenagementFromFiche(String type){
        for(Amenagement amenagement : amenagements){
            if(amenagement.getType().equals(type)){
                amenagements.remove(amenagement);
                return amenagement;
            }
        }
        return null;
    }

    /**
     * Une fonction pour savoir combien le joueur
     * a d'aménagements de type entré en paramètre
     * @param type bassin, nopanda, doublebambo...
     * @return le nombre d'aménagements du type demandé.
     */
    public int getNbAmenagemnts(String type){
        int nb = 0;
        for(Amenagement amenagement : amenagements){
            if(amenagement.getType().equals(type)){
                nb++;
            }
        }
        return nb;
    }

    /**
     * Ajoute un amenagement à la fiche du joueur
     * @param amenagement
     */
    public void addAmenagement(Amenagement am){
        this.amenagements.add(am);
    }
}