package takenoko.controller;

import takenoko.inventory.board.Amenagement;
import takenoko.inventory.objective.ObjectifsTypeJardinier;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.inventory.board.Parcelle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Generator {



    private ArrayList<ObjectifsTypeParcelle> listeObjectifsTypeParcelle = new ArrayList<>(); // Liste des objectifs
    private Stack<ObjectifsTypeParcelle> pileObjectifsTypeParcelle = new Stack<>(); // Pile des objectifs

    private ArrayList<ObjectifsTypePanda> listeObjectifsTypePanda = new ArrayList<>(); // Liste des objectifs type Panda
    private Stack<ObjectifsTypePanda> pileObjectifsTypePanda = new Stack<>();

    private ArrayList<ObjectifsTypeJardinier> listeObjectifsTypeJardinier = new ArrayList<>();
    private Stack<ObjectifsTypeJardinier> pileObjectifsTypeJardinier = new Stack<>();



    private ArrayList<Parcelle> listeParcelles = new ArrayList<>(); // Liste des parcelles
    private Stack<Parcelle> pileParcelles = new Stack<>(); // Pile des parcelles

    private Stack<Amenagement> nopandaAmenagemnt = new Stack<>(); // Pile des Aménagements no panda
    private Stack<Amenagement> bassinAmenagemnt = new Stack<>(); // Pile des Aménagemnts irrigation
    private Stack<Amenagement> doublebamboAmenagement = new Stack<>(); // Pile des Aménagements doublebambo


    private int nbIrrigations; // nombre d'irrigations possible.


    /**
     * Generate shuffeled objectifs and ParcellesList.
     */
   public Generator(){
        generateOurObjectifTypeParcelleList();
        shuffleObjectifsParcelleListAndStoreInStack();

        generateOurObjectifTypePandaList();
        shuffleObjectifsTypePandaListAndStoreInStack();

        generateOurObjectifTypeJardinierList();
        shuffleObjectifsTypeJardinierListAndStoreInStack();

        generateOurParcelleList();
        shuffleParcelleListAndStoreInStack();

        generateOurAmenagementStack();
        nbIrrigations = 20;
    }


    /**
     * return a random ObjectifTypeParcelle
     * @return and an object Objectif.
     */
    ObjectifsTypeParcelle getObjectifTypeParcelle(){
        return pileObjectifsTypeParcelle.pop();
    }
    ObjectifsTypePanda getObjectifTypePanda() { return pileObjectifsTypePanda.pop(); }
    ObjectifsTypeJardinier getObjectifTypeJardinier() { return pileObjectifsTypeJardinier.pop();}
    boolean possibleTirePanda(){
        if(pileObjectifsTypePanda.size()==0){
            return false;
        }
        return true;
    }
    boolean possibleTireParcelle(){
        if(pileObjectifsTypeParcelle.size()==0){
            return false;
        }
        return true;
    }
    boolean possibleTireJardinier(){
        if(pileObjectifsTypeJardinier.size()==0){
            return false;
        }
        return true;
    }



    /**
     * generate a List Of Objectifs as configured inside
     * the function, if we want to add, or remove certain
     * types of Objectifs, it's here.
     */
    private void generateOurObjectifTypeParcelleList(){
        String[] listGeometry=new String[] {"Straight","Triangle","Curve","Diamond"};
        String[] listColors=new String[] {"Pink","Green","Yellow"};
        for (String geo: listGeometry){
            for(String color: listColors){
                ObjectifsTypeParcelle obj = new ObjectifsTypeParcelle(geo,color);
                listeObjectifsTypeParcelle.add(obj);
            }
        }
    }

    /**
     * generate a List Of Objectifs as configured inside
     * the function, if we want to add, or remove certain
     * types of Objectifs, it's here.
     * 2 Green - 2 points (there are 5 cards total like this)
     * 2 Yellow - 4 pts (x4)
     * 2 Pink - 5 pts (x3)
     * 1 Green, 1 Yellow, 1 Pink - 6 pts (x3)
     */
    private void generateOurObjectifTypePandaList(){
        //2 Green - 2 points (there are 5 cards total like this)
        for(int i = 0; i < 5; i++){
            ObjectifsTypePanda obj = new ObjectifsTypePanda(2,0,0, 2);
            listeObjectifsTypePanda.add(obj);
        }

        //2 Yellow - 4 pts (x4)
        for(int i = 0; i < 4; i++){
            ObjectifsTypePanda obj = new ObjectifsTypePanda(0,0,2, 4);
            listeObjectifsTypePanda.add(obj);
        }

        //2 pink - 5 pts (x3)
        for(int i = 0; i < 3; i++){
            ObjectifsTypePanda obj = new ObjectifsTypePanda(0,2,0, 5);
            listeObjectifsTypePanda.add(obj);
        }

        // 1 green, 1 yellow, 1 pink 6 pts (x3)
        for(int i = 0; i < 3; i++){
            ObjectifsTypePanda obj = new ObjectifsTypePanda(1,1,1, 6);
            listeObjectifsTypePanda.add(obj);
        }

    }

    private void generateOurObjectifTypeJardinierList() {
        String[] listColors = new String[]{"Pink", "Green", "Yellow"};
        int[] numberOfBamBoo = {1, 2, 3};
        int[] BambooLength = {3, 4};
        for (int i = 0; i < listColors.length; i++) {
            String color = listColors[i];
            for (int j = 0; j < 3; j++) {
                if (numberOfBamBoo[j] == 1) {
                    ObjectifsTypeJardinier obj1 = new ObjectifsTypeJardinier(color, 1, 4);
                    listeObjectifsTypeJardinier.add(obj1);
                }
                if (numberOfBamBoo[j] == 2) {
                    ObjectifsTypeJardinier obj1 = new ObjectifsTypeJardinier(color, 2, 3);
                    listeObjectifsTypeJardinier.add(obj1);
                }

                if (numberOfBamBoo[j] == 3) {
                    ObjectifsTypeJardinier obj1 = new ObjectifsTypeJardinier(color, 3, 3);
                    ObjectifsTypeJardinier obj2 = new ObjectifsTypeJardinier(color, 3, 4);
                    listeObjectifsTypeJardinier.add(obj1);
                    listeObjectifsTypeJardinier.add(obj2);
                }
            }

        }
        ObjectifsTypeJardinier obj3 = new ObjectifsTypeJardinier("Green",4,4 );
        listeObjectifsTypeJardinier.add(obj3);

    }



    /**
     * Pour chaque pack de couleurs, générer les parcelles avec les aménagements selon les paramètres
     * @param color Green, Yellow, Pink
     * @param nbSansAm nombre de parcelles d'une couleur donneé sans aménagemnts
     * @param nbAmBassin nombre de parcelles d'une couleur donneé avec aménagement bassin
     * @param nbAmNoPanda nombre de parcelles d'une couleur donneé avec aménagement nopanda
     * @param nbAmDBambo nombre de parcelles d'une couleur donneé avec aménagement doublebambo
     */
    private void generateParcellePack(String color, int nbSansAm, int nbAmBassin, int nbAmNoPanda, int nbAmDBambo){
        int j;

        // generer les parcelles sans aménagement
        for (j = 0; j < nbSansAm; j++){
            Parcelle p = new Parcelle(color);
            // ajouter la parcelle traitée dans la liste des parcelles
            listeParcelles.add(p);
        }

        // assigner aménagement bassin aux parcelles
        for (j = 0; j < nbAmBassin; j++){
            Parcelle p = new Parcelle(color);
            Amenagement am = new Amenagement("bassin");
            p.setAmenagement(am);

            // ajouter la parcelle traitée dans la liste des parcelles
            listeParcelles.add(p);
        }

        // assigner aménagement nopanda aux parcelles
        for (j = 0; j < nbAmNoPanda; j++){
            Parcelle p = new Parcelle(color);
            Amenagement am = new Amenagement("nopanda");
            p.setAmenagement(am);

            // ajouter la parcelle traitée dans la liste des parcelles
            listeParcelles.add(p);
        }


        // assigner aménagement doublebambo aux parcelles
        for (j = 0; j < nbAmDBambo; j++){
            Parcelle p = new Parcelle(color);
            Amenagement am = new Amenagement("doublebambo");
            p.setAmenagement(am);

            // ajouter la parcelle traitée dans la liste des parcelles
            listeParcelles.add(p);
        }
    }

    /**
     * Generate a list of parcelles with different colors, and
     * add them into the list of Parcelle, to be shuffeled later
     */
    private void generateOurParcelleList(){
        generateParcellePack("Green", 6,2,2,1);
        generateParcellePack("Pink", 4,1,1,1);
        generateParcellePack("Yellow", 6,1,1,1);
    }


    private void generateOurAmenagementStack(){
        for (int i = 0; i < 3; i++){
            bassinAmenagemnt.add(new Amenagement("bassin"));
        }

        for (int i = 0; i < 3; i++){
            nopandaAmenagemnt.add(new Amenagement("nopanda"));
        }

        for (int i = 0; i < 3; i++){
            doublebamboAmenagement.add(new Amenagement("doublebambo"));
        }
    }

    /**
     * Takes the List of Objectifs, shuffle them with
     * Collections.shuffle(collection) method
     * and put the shuffeld list in a stack
     */
    private void shuffleObjectifsParcelleListAndStoreInStack(){
        Collections.shuffle(this.listeObjectifsTypeParcelle);
        for(int i = 0; i < listeObjectifsTypeParcelle.size(); i++){
            pileObjectifsTypeParcelle.push(listeObjectifsTypeParcelle.get(i));
        }
    }

    /**
     * Takes the List of Parcelles, shuffle them with
     * Collections.shuffle(collection) method
     * and put the shuffeld list in a Parcelle Pile.
     */
    private void shuffleParcelleListAndStoreInStack(){
        Collections.shuffle(this.listeParcelles);
        for(int i = 0; i < listeParcelles.size(); i++){
            pileParcelles.push(listeParcelles.get(i));
        }
    }

    /**
     * Takes the list of objectifs type panda, shuffle them with
     * Collections.shuffle(collection) method
     * and put the shuffeld list in a ObjectifTypePandaPile.
     */
    private void shuffleObjectifsTypePandaListAndStoreInStack() {
        Collections.shuffle(this.listeObjectifsTypePanda);
        for(int i = 0; i < listeObjectifsTypePanda.size(); i++){
            pileObjectifsTypePanda.push(listeObjectifsTypePanda.get(i));
        }
    }

    /**
     * Takes the list of objectifs type jardinier, shuffle them with
     * Collections.shuffle(collection) method
     * and put the shuffeld list in a ObjectifTypeJardinier.
     */
    private void shuffleObjectifsTypeJardinierListAndStoreInStack() {
        Collections.shuffle(this.listeObjectifsTypeJardinier);
        for(int i = 0; i < listeObjectifsTypeJardinier.size(); i++){
            pileObjectifsTypeJardinier.push(listeObjectifsTypeJardinier.get(i));
        }
    }

    /**
     * lors de l'appele de cette fonction, elle décrémente le nombre
     * d'irrigations proposés dans le jeu.
     * @return true, si on peut prendre une irrigation, false sinon.
     */
    boolean getOneIrrigation(){
        if(nbIrrigations>0){
            this.nbIrrigations--;
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * take randomly three parcels from the generator's stack of parcels.
     * @return an ArrayList of three random parcels.
     */
    public ArrayList<Parcelle> getPiocheParcelle() {
            ArrayList<Parcelle> pioche = new ArrayList();
            for (int i = 0; (i < nbParcellesDispo() && i<3); i++) {
                pioche.add(getRandomParcelle());
            }

        //System.out.println(pioche.toString());
        //System.out.println("Taille de la pile de parcelles : "+pileParcelles.size());
        return pioche;
    }

    /**
     * Used by the RandomBot, for whom it doesn't make any difference if you give it 3 or 1 parcel.
     * @return one Parcel at random from the deck
     */
    public ArrayList<Parcelle> getOneRandomParcelFromDeck() {
        ArrayList<Parcelle> pioche = new ArrayList();
        pioche.add(getRandomParcelle());
        return pioche;
    }

    /**
     * @param piocheRestante the two remaining parcels
     * put back piocheRestante  in the generator's stack and shuffle it.
     */
    public void  GiveBackToDeck(ArrayList<Parcelle> piocheRestante){
        //System.out.println(pileParcelles.toString());
        for (Parcelle p:piocheRestante){
            pileParcelles.push(p);
        }
        Collections.shuffle(pileParcelles);
    }
    /**
     * return a random Parcelle
     * @return and an object Parcelle.
     */
    Parcelle getRandomParcelle(){
        return pileParcelles.pop();
        /*if(! pileParcelles.empty() )
            return pileParcelles.pop();
        else{

        }*/
    }
    public int nbParcellesDispo(){
        return pileParcelles.size();
    }
    /**
     * return la liste des ObjectifsTypeParcelles (for debugging)
     * @return ArrayList of Liste of Objectifs
     */
    ArrayList<ObjectifsTypeParcelle> getListeObjectifsTypeParcelle() {
        return listeObjectifsTypeParcelle;
    }

    /**
     * return la liste des Parcelles (for debugging)
     * @return ArrayList of Liste of Parcelles
     */
    ArrayList<Parcelle> getListeParcelles() {
        return listeParcelles;
    }

    /**
     * Setter de list of Objectifs for debugging
     * @param listeObjectifsTypeParcelle  une ArrayList d'objectifs
     */
    public void setListeObjectifsTypeParcelle(ArrayList<ObjectifsTypeParcelle> listeObjectifsTypeParcelle) {
        this.listeObjectifsTypeParcelle = listeObjectifsTypeParcelle;
    }

    /**
     * Setter de list of Parcelles for debugging
     * @param listeParcelles une ArrayList de parcelles
     */
    void setListeParcelles(ArrayList<Parcelle> listeParcelles) {
        this.listeParcelles = listeParcelles;
    }

    /**
     * @return the Stack of shuffeled ObjectifsTypeParcelles cards.
     */
    public Stack<ObjectifsTypeParcelle> getPileObjectifsTypeParcelle() {
        return pileObjectifsTypeParcelle;
    }

    /**
     * @return the Stack of Parcelles shuffeled .
     */
    Stack<Parcelle> getPileParcelles() {
        return pileParcelles;
    }

    /**
     * Setter of Stack of the Objectifs for debbuging.
     */
    void setPileObjectifsTypeParcelle(Stack<ObjectifsTypeParcelle> pileObjectifsTypeParcelle) {
        this.pileObjectifsTypeParcelle = pileObjectifsTypeParcelle;
    }

    /**
     * Setter of Stack of the Parcelle for debbuging.
     */
    public void setPileParcelles(Stack<Parcelle> pileParcelles) {
        this.pileParcelles = pileParcelles;
    }


    /**
     * @return un aménagement bassin
     */
    public Amenagement getBassinAmenagemnt() {
        return bassinAmenagemnt.pop();
    }

    /**
     * @return un aménagement nopanda
     */
    public Amenagement getNoPandaAmenagemnt() {
        return nopandaAmenagemnt.pop();
    }

    /**
     * @return un aménagement doublebambo
     */
    public Amenagement getDoubleBamboAmenagemnt() {
        return doublebamboAmenagement.pop();
    }

    /**
     * @return nombre de bassin aménagement restants
     */
    public int getSizeBassinAmenagement(){
        return bassinAmenagemnt.size();
    }

    /**
     * @return nombre de nopanda aménagement restants
     */
    public int getSizeNoPandaAmenagement(){
        return nopandaAmenagemnt.size();
    }

    /**
     * @return nombre de doublebambo aménagement restants
     */
    public int getSizDoubleBamboAmenagement(){
        return doublebamboAmenagement.size();
    }

    public Stack<ObjectifsTypePanda> getPileObjectifsTypePanda() {
        return pileObjectifsTypePanda;
    }

    public Stack<ObjectifsTypeJardinier> getPileObjectifsTypeJardinier() {
        return pileObjectifsTypeJardinier;
    }

}

