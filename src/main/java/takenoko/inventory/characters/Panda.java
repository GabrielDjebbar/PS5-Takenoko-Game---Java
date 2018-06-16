package takenoko.inventory.characters;

import takenoko.inventory.board.Parcelle;
import takenoko.inventory.board.Plateau;
import takenoko.player.Joueur;

import java.awt.*;

/**
 *
 */
public class Panda extends Character {
    private Point point;

    /**
     * Constructeur du Panda, qui l'initialise dans la parcelle (0,0)
     */
    public Panda(){
        super();
        }


//    /**
//     * function that makes the panda eat a bamboo and add it to the player's fiche ( the player who  moved the panda )
//     * @param joueur
//     */
//    public void mange(Joueur joueur, Plateau plateau){
//        Parcelle parcellePanda=plateau.getParcelleByCoord(this.getPosX(),this.getPosY());
//        if (plateau.isEmpty(this.getPosX(),this.getPosY())){ // si la position est vide alors rien ne passe
//
//        }
//
//        else{
//            if(parcellePanda.getBamboo()==0){ // s'il n'y a pas de bamboo on ne fait rien
//
//            }
//            else{
//        String color = parcellePanda.getColor();
//                parcellePanda.remove1BambooEaten();
//        joueur.getjFiche().addBambooEatenToFiche(color);
//        System.out.println("Le Panda a mangé un bamboo"+color);
//            }
//    }
//    }


    /**
     * Methode privée qui met à jour le point(x,y) du panda, pour qu'on puisse
     * le récuperer à jour. On l'appele quand on utilise le setter.
     */
    /*public void majPointPanda(){
        this.point.x = posX;
        this.point.y = posY;
    }*/
    // method public to do some tests.

    Point getPoint(){
        return point;
    }





}
