package takenoko.graphics;

import takenoko.controller.Moteur;
import takenoko.inventory.board.Plateau;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Frame {
    private final int WIDTH=1000;
    private final int HEIGTH=600;
    private JScrollPane scroller;
    public DisplayPanel panel;


    public Frame(Moteur moteur,Plateau pl){
        panel=new DisplayPanel(moteur,pl);
        JFrame frame = new JFrame("Plateau");
        frame.setSize(WIDTH, HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scroller = new JScrollPane(panel);
        frame.add(scroller, BorderLayout.CENTER);
        //frame.add(panel);
        frame.setVisible(true);

    }


}
