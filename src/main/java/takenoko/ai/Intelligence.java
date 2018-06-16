package takenoko.ai;

import jdk.nashorn.internal.scripts.JO;
import takenoko.controller.Action;
import takenoko.controller.Meteo;
import takenoko.inventory.board.Plateau;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;

public interface  Intelligence {
      public void makeChoice(Joueur j,Meteo meteo);
      public void setParamaeters(Plateau plateau, Action action, Possibility possibility);
}
