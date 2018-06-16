package takenoko;

import org.junit.Before;
import org.junit.Test;
import takenoko.ai.Smart;
import takenoko.controller.Action;
import takenoko.controller.Generator;

import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.objective.ObjectifsTypeParcelle;
import takenoko.player.Fiche;
import takenoko.player.Joueur;
import takenoko.referee.Possibility;
import takenoko.utils.Log;

import java.awt.*;
import java.util.List;
import java.util.logging.Level;

import static org.junit.Assert.*;

public class SmartTest {
    Plateau pla;
    Generator gn;
    Possibility poss;
    Action act;
    Joueur j;

    Fiche fiche;

    @Before
    public void initialize(){
        j= new Joueur("j1");
        fiche = new Fiche("j1");
        pla=new Plateau();
        gn=new Generator();
        poss= new Possibility(pla);
        act=new Action(pla,gn);


    }

}