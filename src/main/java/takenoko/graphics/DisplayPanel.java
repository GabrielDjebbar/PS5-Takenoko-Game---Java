package takenoko.graphics;

import takenoko.controller.Moteur;
import takenoko.inventory.board.Plateau;
import takenoko.inventory.characters.Panda;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel  {
    //Joueur moteur;
    Plateau plateau;

    public DisplayPanel(Moteur m,Plateau pl){
        super();
        super.setPreferredSize(new Dimension(1000,600));
        //moteur=m;
        plateau=pl;
        repaint();

    }
    public void  update(Graphics g){
        plateau.draw(g);
        //moteur.draw(g);
        paint(g);
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        setBackground(Color.WHITE);
        g.setColor(Color.BLACK);
        plateau.draw(g);
        //moteur.draw(g);

        /*Font font = new Font("Courier", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.red);
        g.drawString("Score joueur 1 :"+,10,20);*/
    }

}
