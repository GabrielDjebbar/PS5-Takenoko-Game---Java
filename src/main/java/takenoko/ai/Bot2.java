package takenoko.ai;

import takenoko.ai.strategy.AnalysePanda;
import takenoko.ai.strategy.AnalyseParcelle;
import takenoko.ai.strategy.ScoreObjectif;
import takenoko.controller.Action;
import takenoko.controller.Meteo;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.board.Segement;
import takenoko.inventory.objective.ObjectifsTypePanda;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;
import takenoko.utils.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class Bot2 implements Intelligence{
    private Plateau plateau;
    private Possibility possibility;
    private Action action;
    private Meteo meteo;
    private Joueur joueur;
    private ArrayList<Parcelle> piocheParcelle=new ArrayList<>();
    private ArrayList<ScoreObjectif> analyseObjectifs=new ArrayList<>();
    private ScoreObjectif objectifChoisi;
    private Parcelle parcelle_jouee;
    private ArrayList<String> objectifInterdit=new ArrayList<>();

    public Bot2(Plateau pl, Action act, Possibility p) {
        plateau = pl;
        action = act;
        possibility = p;
        analyseObjectifs.clear();
        objectifInterdit.clear();
    }

    public Bot2() {

        analyseObjectifs.clear();
        objectifInterdit.clear();
    }
    @Override
    public void setParamaeters(Plateau plateau, Action action, Possibility possibility) {
        this.plateau=plateau;
        this.possibility=possibility;
        this.action=action;
    }

    public void makeChoice(Joueur joueur, Meteo meteo) {
        this.meteo = meteo;
        this.joueur = joueur;
        this.objectifInterdit.clear();
        objectifInterdit.clear();
        lanceAnalyseEtChoisitObjectif();

        if (!meteo.getPremierCoup()) {
            fairePremierCoup();
            meteo.jouePremierCoup();
        }
        if (coupPossible("Objectif")) {
            if (addObjectifs()) {
                return;
            }
        }
        while(!joueObjectif()){
        }
        return;
    }

    /**
     * @return false si on a pas tire d'objectifs
     * return true si on en a tiré un
     */
    private boolean addObjectifs() {
        if (joueur.GetObParcelleAFaire().size() < 3 && action.possibleTireParcelle()) { // si il y a moins de 3 objectifs
            joueur.addObjectifParcelleToFiche();
            meteo.joueObjectif();
            return true;
        } else if (joueur.GetObJardinierAFaire().size() < 0 && action.possibleTireJardinier()) {
            joueur.addObjectifJardinierToFiche();
            meteo.joueObjectif();
            return true;
        } else if (joueur.GetObPandaAFaire().size() < 2 &&     action.possibleTirePanda()) {
            joueur.addObjectifPandaToFiche();
            meteo.joueObjectif();
            return true;
        }
        return false;
    }

    /**
     * @param type c'est le type du coup a faire
     * @return si on peux ou non faire ce coup en fonction de la meteo
     */
    private boolean coupPossible(String type){
        if(meteo.getCoupsPossibles().get(type)>0) {
            return true;
        }
        return false;
    }

    /**
     * Au premier coup il faut choisir un objectif a réaliser
     * On prends celui avec le score le plus bas
     */
    private void lanceAnalyseEtChoisitObjectif(){
        analyseObjectifs.clear();
        if(coupPossible("Parcelle")){
            for (ObjectifsTypeParcelle objectif :joueur.getObjectifsParcelleFromFiche()) {
                AnalyseParcelle analyse = new AnalyseParcelle(objectif, plateau);
                ScoreObjectif score = new ScoreObjectif(analyse.getParcellesAIrriguer(), analyse.getParcellesAPoser(), plateau, possibility, objectif.getColor());
                analyseObjectifs.add(score);
            }
        }
        if(coupPossible("Panda")) {
            for (ObjectifsTypePanda objectif : joueur.getObjectifsPandaFromFiche()) {
                AnalysePanda analyse = new AnalysePanda(objectif, plateau, joueur.getjFiche());
                ScoreObjectif score = new ScoreObjectif(analyse.getBambousAManger(), analyse.getDirection(), plateau, possibility, analyse.getDirectionJardinier());
                analyseObjectifs.add(score);
            }
        }
        //System.out.println(joueur.getjFiche().getObjectifsTypeParcelle().size()+joueur.getjFiche().getObjectifsTypePanda().size());
        //System.out.println(analyseObjectifs.size());
        choisitObjectif();

    }

    /**
     * @return vrai si on a pu jouer
     * false sinon
     * cette methode joue un objectif en fonction de la meteo.
     */
    private boolean joueObjectif(){
        if(objectifChoisi.getType().equals("Parcelle")) {
            if (objectifChoisi.getParcellesAPoser().size() != 0) {
                if (joueur.getjFiche().getNbIrrigations() > 0 && (possibility.possibleTireSegement())) { // On a des irrigations dispo
                    //System.out.println("irrigation la");
                    action.poserIrrigation(findIrrigation(), joueur, joueur.getjFiche());
                }
                if(coupPossible("Parcelle")) {
                    meteo.joueParcelle();
                    setPiocheParcelle();
                    if (piocheParcelle.size() > 0){
                        joueParcelle(false);
                        return true;
                    }
                }
            }
            if (objectifChoisi.getParcellesAIrriguer().size() != 0 && coupPossible("Irrigation") && (possibility.possibleTireSegement())) {
                if(joueIrrigation()){
                    return true;
                }
            }
        }
        if(objectifChoisi.getType().equals("Panda")) {
            if ((objectifChoisi.getDirection().x != 0 || objectifChoisi.getDirection().y != 0) && coupPossible("Panda")) {
                meteo.jouePanda();
                jouePanda();
                return true;
            }
            if(coupPossible("Jardinier")){
                if((objectifChoisi.getDirectionJardinier().x != 0 || objectifChoisi.getDirectionJardinier().y != 0) && coupPossible("Panda")) {
                    meteo.joueJardinier();
                    joueJardinier();
                    return true;
                }
            }
        }

        if(coupPossible("Parcelle")){
            setPiocheParcelle();
            joueParcelle(true);
            return true;
        }
        if(!changeObjectif()){ // ici on ne joue pas
            return true;
        }

        return false;
    }

    /**
     * On envoie la panda sur la direction choisie
     */
    private void jouePanda() {
        action.deplacementPanda(objectifChoisi.getDirection().x,objectifChoisi.getDirection().y,plateau.getPanda(),joueur);
    }

    /**
     *Une fois l'objectif parcelle choisi on doit le jouer
     */
    private void joueParcelle(Boolean isRandom){
        choisitParcelle();
        if(!isRandom) {
            poseParcelle();
        }else{
            poseRandomParcelle();
        }
        resetPiocheParcelle();
    }
    /**
     * Recuperation de la pioche correspondant aux 3 cartes de types parcelles
     * que le jeu nous propose
     */
    private void setPiocheParcelle() {
        //System.out.println(piocheParcelle.toString());
        piocheParcelle = action.getPiocheParcelle();

    }

    /**
     * Remets les parcelles non utilisées dans la banque de parcelles dispo
     */
    private void resetPiocheParcelle() {
        piocheParcelle.remove(parcelle_jouee);
        action.GiveBackToDeck(piocheParcelle);
        parcelle_jouee=null;
    }
    private void choisitParcelle(){
        for(Parcelle p:piocheParcelle){
            if (p.getColor().equals(objectifChoisi.getCouleurParcelle())){
                parcelle_jouee=p;
            }
            break;
        }
        if(parcelle_jouee==null) {
            if (!piocheParcelle.isEmpty()){
                parcelle_jouee = piocheParcelle.get(0);
            }
        }
    }

    /**
     * On pose une parcelle de la bon ne couleur sur un point contenu dans parcelle a poser
     * sionon on le pose ailleurs
     */
    private void poseParcelle(){
        for(Point p1:objectifChoisi.getParcellesAPoser()){
            for(Point p2:possibility.availablePositions()){
                if((p1.x==p2.x)&&(p1.y==p2.y)&&(parcelle_jouee.getColor()==objectifChoisi.getCouleurParcelle())){
                    action.putParcel(parcelle_jouee,p1.x,p1.y,joueur.getNomJoueur());
                    return;
                }
            }
        }
        for(Point p2:possibility.availablePositions()){
            if(!objectifChoisi.getParcellesAPoser().contains(p2)){
                action.putParcel(parcelle_jouee,p2.x,p2.y,joueur.getNomJoueur());
                return;
            }
        }
    }
    private boolean changeObjectif(){
        if(!objectifInterdit.contains(objectifChoisi.getType())){
            objectifInterdit.add(objectifChoisi.getType());
        }
        return choisitObjectif();

    }
    private boolean choisitObjectif(){
        int score=100;
        //System.out.println("ici");
        String type1="";
        try {
            type1 = objectifChoisi.getType();
        }catch(java.lang.NullPointerException e){}
        for(ScoreObjectif s:analyseObjectifs) {
            if (!objectifInterdit.contains(s.getType())) {
                if ((s.getScore() < score) && (s.getScore() != -1)) {
                    objectifChoisi = s;
                    score = objectifChoisi.getScore();
                }
            }
        }
        String type2=objectifChoisi.getType();
        if(type1.equals(type2)){
            return false;
        }
        //String toPrint=objectifChoisi.toSring();
        //Log.logger.log(Level.INFO, toPrint);
        //System.out.println(objectifInterdit.toString());

        return true;
        //System.out.println(analyseObjectifs.size());
        //System.out.println("choix de l'objectif "+objectifChoisi.getType()+" de score "+objectifChoisi.getScore());
    }
    /**
     * Si on a pas d'irrigation on la tire
     * Sinon on la pose
     */
    private boolean joueIrrigation() {
        if (joueur.getjFiche().getNbIrrigations() ==0) { // On a pas d'irrigations dispo
            action.prendreIrrigation(joueur.getjFiche());
            meteo.joueIrrigation();
            return true;
        }
        return false;
    }
    /**
     * @return le segement selectionné
     * il est choisi en fonction de la distance entre celui ci et la parcelle que l'on veux irriguer
     */
    private Segement findIrrigation() {
        double distance = 100;
        Segement selected = null;
        ArrayList<Segement> listSeg = possibility.getListSegementsPossible();
        for (Segement s : listSeg) {
            if (distance(s) < distance) {
                distance = distance(s);
                selected = s;
            }
        }
        return selected;
    }
    /**
     * parametre : le segement de reference pour calculer la distance
     * @return la distance entre le point central de la parcelle que l'on veux irriguer et le segment s
     * la parcelle a irriguer est contenue dans la variable aIrriguer
     */
    private double distance(Segement s) {
        double distance_finale=100;
        double distance=0;
        double x2 = s.getPointcentral()[0];
        double y2 = s.getPointcentral()[1];
        for(Point p:objectifChoisi.getParcellesAIrriguer()){
            double x1=p.x;
            double y1=p.y;
            distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
            if(distance_finale>distance){
                distance_finale=distance;
            }
        }
        return distance;
    }
    private void fairePremierCoup() {
        boolean aJoue = false;
        switch (meteo.getType()) {

            case "Pluie":  // Poser un bambou
                poseBambou();
                aJoue = true;
                break;
            case "Eclair": // Deplace le panda
                jouePandaTour1();
                aJoue = true;
                break;
            case "Nuages":
                joueAmenagement();
                break;
            case "?":     // On choisit une meteo au hasard
                meteo.choisirMeteo("Soleil");
                Log.logger.log(Level.INFO, joueur.getNomJoueur() + " Choisir la météo : Soleil");
                break;

        }
        if (aJoue) {
            meteo.ajouteUnCoupPossible();
        }
    }
    private void poseBambou(){
        for(ScoreObjectif s:analyseObjectifs) {
            if(s.getType().equals("Panda")){
                Point p=s.getDirection();
                plateau.getParcelleByCoord(p.x,p.y).add1Bamboo();
                Log.logger.log(Level.INFO,"Un Bambou est posé en "+p.x+" , "+p.y+" par "+joueur.getNomJoueur());
                return;
            }
        }
    }
    private void jouePandaTour1(){
        for(ScoreObjectif s:analyseObjectifs) {
            if (s.getType().equals("Panda")) {
                objectifChoisi=s;
                jouePanda();
                return;
            }
        }
    }
    private void joueAmenagement(){

    }
    private Boolean poseRandomParcelle(){
        int b=(int) (Math.random()*possibility.availablePositions().size());
        meteo.joueParcelle();
        int i=0;
        for(Point parcelle:possibility.availablePositions()){
            i++;
            if(i==b){
                if(parcelle_jouee!=null) {
                    action.putParcel(parcelle_jouee, parcelle.x, parcelle.y, joueur.getNomJoueur());
                    return true;
                }
            }
        }
        return false;
    }
    private void joueJardinier(){
        action.deplacementJardinier(objectifChoisi.getDirectionJardinier().x,objectifChoisi.getDirectionJardinier().y,plateau.getGardener());
    }
}
