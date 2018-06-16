package takenoko.inventory.characters;

import takenoko.inventory.board.Parcelle;
import takenoko.referee.Possibility;

import java.util.ArrayList;

public class Gardener extends Character {

    public Gardener() {
        super();
    }

    /*public void GardenerCultivateBamboo() {
        Possibility poss = new Possibility();
        ArrayList<Parcelle> parcelsForBamboo = poss.possibilityListPousserBambo(Plateau plateau);
        for (Parcelle p : parcelsForBamboo) {
            int x = this.getPosX();
            int y = this.getPosY();
            if (p.isADroiteDeLaParcelleDeLaPosition(x, y))
                p.add1Bamboo();
            if (p.isAGaucheDeLaPosition(x, y))
                p.add1Bamboo();
            if (p.isEnBasDroiteDeLaPosition(x, y))
                p.add1Bamboo();
            if (p.isEnBasGaucheDeLaPosition(x, y))
                p.add1Bamboo();
            if (p.isEnHautGaucheDeLaPosition(x, y))
                p.add1Bamboo();
            if (p.isEnHautDroiteDeLaPosition(x, y))
                p.add1Bamboo();
            if ((p.getX()==x)&&(p.getY()==y))
                p.add1Bamboo();
        }
    }*/

}
