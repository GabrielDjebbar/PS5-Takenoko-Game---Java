package takenoko.referee;


import javafx.geometry.Pos;
import sun.rmi.runtime.Log;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.board.Segement;
import takenoko.inventory.characters.Gardener;
import takenoko.inventory.characters.Panda;
import takenoko.player.Joueur;

import java.awt.*;


import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/*
 * Class to calculate different possibilities for player
 *      * possible parcelle positions
 */
public class Possibility {
    static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    // list to store different possible parcel positions for a given play
    private Set<Point> possibleCoordinates;
    private final Set<Point> neighbourCoordinates;
    private Plateau plateau;
    private ArrayList<Segement> listSegementsPossible = new ArrayList<>();


    /**
     * constructor to get neighbourhood parcel
     */
    public Possibility() {
        neighbourCoordinates = new HashSet<>(Arrays.asList(new Point(2, 0), new Point(-2, 0),
                new Point(1, 1), new Point(1, -1), new Point(-1, 1), new Point(-1, -1)));
    }


    /**
     * constructor to get different possibilities given a state of the plateau
     *
     * @param pl
     */
    public Possibility(Plateau pl) {
        plateau = pl;
        possibleCoordinates = new HashSet<>();
        neighbourCoordinates = new HashSet<>(Arrays.asList(new Point(2, 0), new Point(-2, 0),
                new Point(1, 1), new Point(1, -1), new Point(-1, 1), new Point(-1, -1)));
    }


    /**
     * @param x pos x of parcel
     * @param y pos y of parcel
     * @return set of neighborhood parcels
     */
    public Set<Point> getNeighbourCoordinates(int x, int y) {
        Set<Point> res = new HashSet<>(neighbourCoordinates);
        for (Point p : res) {
            p.setLocation(p.getX() + x, p.getY() + y);
        }
        return res;
    }



    /**
     * @param parc1
     * @param parc2
     * @return true if parc1 is a neighbour of parc2
     */
    public boolean isNeighbour(Parcelle parc1, Parcelle parc2){
        return getNeighbourCoordinates(parc1.getX(),parc1.getY()).stream().filter(
                pt-> pt.x==parc2.getX() && pt.y==parc2.getY()).count()>0;
    }

    public List<Parcelle> getListOfParcelByColor(String color){

        return plateau.getListParcelle().stream().filter(
                parcelle -> parcelle.getColor().equals(color)
        ).collect(Collectors.toList());
    }

    public ArrayList<Parcelle> getNeighboorParcellesByCoord(int x, int y) {
        ArrayList<Parcelle> result = new ArrayList<>();
        for (Parcelle parcelle : plateau.getListParcelle()) {
            if (((parcelle.getX() == x + 2) && ((parcelle.getY()) == y)) || ((parcelle.getX() == x - 2) && ((parcelle.getY()) == y)) ||
                    ((parcelle.getX() == x - 1) && ((parcelle.getY()) == y + 1)) || ((parcelle.getX() == x - 1) && ((parcelle.getY()) == y - 1)) ||
                    ((parcelle.getX() == x + 1) && ((parcelle.getY()) == y + 1)) || ((parcelle.getX() == x + 1) && ((parcelle.getY()) == y - 1))) {
                result.add(parcelle);
            }
        }
        return result;
    }

    public Parcelle getParcelleByCoord(int x, int y) {
        for (Parcelle parc : plateau.getListParcelle()) {
            if (parc.getX() == x && parc.getY() == y) {
                return parc;
            }
        }
        return null;
    }


    /* function that returns list of possible position
       for player to put a parcel in, for a given play
     */
    public Set<Point> availablePositions() {
        possibleCoordinates = new HashSet<>();
        for (Parcelle p : plateau.getListParcelle()) {
            possibleCoordinates.addAll(checkAvailablePosForPoint(p.getX(), p.getY()));
        }
        //System.out.println(possibleCoordinates);
        return possibleCoordinates;
    }


    //check if neighbouring parcel contain a parcel or not
    // and return those who are empty
    public Set<Point> checkAvailablePosForPoint(int x, int y) {
        Set<Point> list = new HashSet<>();
        for (Point p : neighbourCoordinates) {
            Point tmp = new Point((x + p.x), (y + p.y));
            if (plateau.isEmpty(tmp.x, tmp.y) && hasTwoNeighbours(tmp)) {
                list.add(tmp);
            }
        }
        return list;
    }

    // check if point has two neighbouring points
    public boolean hasTwoNeighbours(Point point) {
        int count = 0;
        for (Point p : neighbourCoordinates) {
            Point tmp = new Point((point.x + p.x), (point.y + p.y));
            // position (0,0) doesn't need two neighbours
            if (tmp.equals(new Point(0, 0))) {
                return true;
            }
            if (!plateau.isEmpty(tmp.x, tmp.y)) {
                count++;
            }
        }
        return count > 1;
    }

    /**
     * function useful to do J-Unit test for GetNeighboourParcelle
     */
    public void setPlateaufromPossibility(int x, int y) {
        Parcelle objParcelle = new Parcelle("couleurTest");
        objParcelle.setXY(x, y);
        plateau.addParcelle(x, y, objParcelle);
    }

    public void majTabSegementsPossible() {
        listSegementsPossible.clear();
        for (Parcelle p : plateau.getListParcelle()) {
            p.irriguePtsIfPossible(plateau);


            for(Segement s : p.getTabSegementsAIrriguer()){
                Integer x = 1;
                Integer y = 2;
                if((s.getSet().contains(x))&&(s.getSet().contains(y))&&(!plateau.isEmpty(p.getX()-1,p.getY()+1))){
                    listSegementsPossible.add(s);
                }
                x = 2;
                y = 3;
                if((s.getSet().contains(x))&&(s.getSet().contains(y))&&(!plateau.isEmpty(p.getX()-2,p.getY()))){
                    listSegementsPossible.add(s);
                }

                x = 3;
                y = 4;
                if((s.getSet().contains(x))&&(s.getSet().contains(y))&&(!plateau.isEmpty(p.getX()-1,p.getY()-1))){
                    listSegementsPossible.add(s);
                }

                x = 4;
                y = 5;
                if((s.getSet().contains(x))&&(s.getSet().contains(y))&&(!plateau.isEmpty(p.getX()+1,p.getY()-1))){
                    listSegementsPossible.add(s);
                }

                x = 5;
                y = 6;
                if((s.getSet().contains(x))&&(s.getSet().contains(y))&&(!plateau.isEmpty(p.getX()+2,p.getY()))){
                    listSegementsPossible.add(s);
                }

                x = 1;
                y = 6;
                if((s.getSet().contains(x))&&(s.getSet().contains(y))&&(!plateau.isEmpty(p.getX()+1,p.getY()+1))){
                    listSegementsPossible.add(s);
                }
            }
        }

    }

    public ArrayList<Segement> getListSegementsPossible() {
        majTabSegementsPossible();
        return listSegementsPossible;
    }

    public void printListSegemntsPossible() {
        for (int i = 0; i < listSegementsPossible.size(); i++) {
            logger.log(Level.FINE, listSegementsPossible.get(i).printSegement());
        }
    }


    public Plateau getPlateauFromPossibility() {
        return this.plateau;
    }




    private void possibilityCharacterObliqueRightHigh(int x, int y,ArrayList<Parcelle> listPossibilityCharacter) {
        boolean notEmpty = true;
        int i = 1;
        while (notEmpty) {
            notEmpty = !plateau.isEmpty(x +i, y+i);
            if (notEmpty) {
                listPossibilityCharacter.add(plateau.getParcelleByCoord(x + i, y+i));
                i += 1;
            }
        }
    }
    private void possibilityCharacterObliqueLeftDown(int x, int y,ArrayList<Parcelle> listPossibilityCharacter) {
        boolean notEmpty = true;
        int i = 1;
        while (notEmpty) {
            notEmpty = !plateau.isEmpty(x -i, y-i);
            if (notEmpty) {
                listPossibilityCharacter.add(plateau.getParcelleByCoord(x - i, y-i));
                i += 1;
            }
        }
    }
    private void possibilityCharacterObliqueLefttHigh(int x, int y,ArrayList<Parcelle> listPossibilityCharacter) {
        boolean notEmpty = true;
        int i = 1;
        while (notEmpty) {
            notEmpty = !plateau.isEmpty(x -i, y+i);
            if (notEmpty) {
                listPossibilityCharacter.add(plateau.getParcelleByCoord(x - i, y+i));
                i += 1;
            }
        }
    }
    private void possibilityCharacterObliqueRightDown(int x, int y,ArrayList<Parcelle> listPossibilityCharacter) {
        boolean notEmpty = true;
        int i = 1;
        while (notEmpty) {
            notEmpty = !plateau.isEmpty(x +i, y-i);
            if (notEmpty) {
                listPossibilityCharacter.add(plateau.getParcelleByCoord(x +i, y-i));
                i += 1;
            }
        }
    }
    private void possibilityCharacterHorizontalRight(int x, int y,ArrayList<Parcelle> listPossibilityCharacter) {
        boolean notEmpty = true;
        int i = 1;
        while (notEmpty) {
            notEmpty = !plateau.isEmpty(x + 2 * i, y);
            if (notEmpty) {
                listPossibilityCharacter.add(plateau.getParcelleByCoord(x + 2 * i, y));
                i += 1;
            }
        }
    }
    private void possibilityCharacterHorizontalLeft(int x, int y,ArrayList<Parcelle> listPossibilityCharacter) {
        boolean notEmpty = true;
        int i = 1;
        while (notEmpty) {
            int xpos=x-2 *i;
            notEmpty = !plateau.isEmpty(xpos, y);
            if (notEmpty) {
                listPossibilityCharacter.add(plateau.getParcelleByCoord(xpos, y));
                i += 1;
            }
        }
    }



    /**
     * @param x the coordinate of the Panda
     * @param y the coordinate of the Panda
     * @return the list of the moves possible for the panda
     */
    public  ArrayList<Parcelle> possibilityCharacter(int x, int y) {
        ArrayList<Parcelle> listPossibilityCharacter = new ArrayList<>();

        possibilityCharacterHorizontalLeft(x,y,listPossibilityCharacter);
        possibilityCharacterHorizontalRight(x,y,listPossibilityCharacter);
        possibilityCharacterObliqueRightHigh(x,y,listPossibilityCharacter);
        possibilityCharacterObliqueLeftDown(x,y,listPossibilityCharacter);
        possibilityCharacterObliqueLefttHigh(x,y,listPossibilityCharacter);
        possibilityCharacterObliqueRightDown(x,y,listPossibilityCharacter);

        return listPossibilityCharacter;
    }

    public  ArrayList<Parcelle> possibilityPanda(int x, int y) {
        ArrayList<Parcelle> listPossibilityCharacter = new ArrayList<>();

        possibilityCharacterHorizontalLeft(x,y,listPossibilityCharacter);
        possibilityCharacterHorizontalRight(x,y,listPossibilityCharacter);
        possibilityCharacterObliqueRightHigh(x,y,listPossibilityCharacter);
        possibilityCharacterObliqueLeftDown(x,y,listPossibilityCharacter);
        possibilityCharacterObliqueLefttHigh(x,y,listPossibilityCharacter);
        possibilityCharacterObliqueRightDown(x,y,listPossibilityCharacter);

        listPossibilityCharacter.removeIf(p -> p.getAmenagement().getType().equals("nopanda"));

        return listPossibilityCharacter;
    }


    /**
     * retourne la liste des parcelles ou on peut faire pousser un bambo
     * les contraintes est que la parcelle doit être irrigué, et que
     * le nombre de bambo soit inférieur a 4
     * @return la liste des parcelles ou on peut faire pousser un bambo
     */
    public ArrayList<Parcelle> possibilityListPousserBambo(){
        ArrayList<Parcelle> res = new ArrayList<>();
        for(Parcelle p : plateau.getListParcelle()){
            if(p.isParcelleIrrigue() && (!p.getColor().equals("Etang"))){
                res.add(p);
            }
        }
        return res;
    }

    /**
     * Une fonction qui parcours la liste des parcelles, les vérifie
     * et rend les parcelles ou c'est possible de poser un aménagement
     *   prend plateau comme argument
     * @return une liste de parcelles ou c'est possible de poser un aménagemnt
     */
    public ArrayList<Parcelle> possibilityAmenagement(){
        ArrayList<Parcelle> amenagementsPossibleDans = new ArrayList<>();
        for (Parcelle p : plateau.getListParcelle()){
            if((p.getBamboo() == 0) && (p.getAmenagement().getType().equals("noamg")) && (!p.getColor().equals("Etang"))){
                amenagementsPossibleDans.add(p);
            }
        }
        return amenagementsPossibleDans;
    }

    /**
     * PAY ATTENTION : NEED TO REMOVE THE SAME FUNCTION DEFINED IN RandomBot class !!!!
     *
     * @return true if  it's possible for the player  to put an irrigation somewhere on the board, false if it isn't.
     */
    public boolean putIrrigationisPossible(Joueur j) {
        if (getListSegementsPossible().size() > 0 && j.getjFiche().getNbIrrigations() > 0)
            return true;
        else
            return false;
    }
    public boolean possibleTireSegement(){
        if(this.getListSegementsPossible().size()==0){
            return false;
        }
        return true;
    }



}

