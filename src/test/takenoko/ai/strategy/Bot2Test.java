package takenoko.ai.strategy;

import takenoko.ai.Bot2;
import takenoko.controller.Action;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.referee.Possibility;

public class Bot2Test {
    Plateau pl = new Plateau();
    AnalyseParcelle analyse;
    Possibility possibility;
    Action action;
    ObjectifsTypeParcelle obj1 = new ObjectifsTypeParcelle("Curve", "Green");
    ObjectifsTypeParcelle obj2 = new ObjectifsTypeParcelle("Straight", "Yellow");
    ObjectifsTypeParcelle obj3 = new ObjectifsTypeParcelle("Triangle", "Green");
    ObjectifsTypeParcelle obj4 = new ObjectifsTypeParcelle("Diamond", "Green");

    public Bot2 initialize(Plateau pl,Action action,Possibility possibility) {
        Bot2 bot = new Bot2(pl, action, possibility);
        return bot;
    }

}