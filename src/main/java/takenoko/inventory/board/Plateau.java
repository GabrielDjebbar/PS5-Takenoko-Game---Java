package takenoko.inventory.board;


import takenoko.graphics.Draw;
import takenoko.inventory.characters.Gardener;
import takenoko.inventory.characters.Panda;
import takenoko.utils.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class represents the game's board and will contain the "parcels".
 */
public class Plateau {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ArrayList<Parcelle> listParcelle;
    Panda panda;
    Gardener gardener;
    int turnPlayed;
    int scoreJ1;
    int scoreJ2;
    String joueurJouant="";
    private BufferedImage imgP ;
    private BufferedImage imgG ;

    /**
     * this is the constructor
     */
    public Plateau() {
        listParcelle = new ArrayList<>();
        Parcelle p = new Parcelle("Etang");
        p.initializeEtang();
        listParcelle.add(p);
    }
    public Plateau(Panda panda, Gardener gardener) {
        File fileP=null;
        File fileG=null;
        try{
            fileP =new File("src/img/pandaMin.png");
            fileG =new File("src/img/gardener2.png");
            imgP= ImageIO.read(fileP);
            imgG= ImageIO.read(fileG);

        }
        catch (Exception e){
            Log.logger.log(Level.SEVERE,"Image not found");

        }
        this.turnPlayed=1;
        listParcelle = new ArrayList<>();
        Parcelle p = new Parcelle("Etang");
        p.initializeEtang();
        listParcelle.add(p);
        this.panda=panda;
        this.gardener=gardener;
    }

    /**
     * @param x coordinate on axis X
     * @param y coordinate on axis Y
     * @param p object parcel
     *
     * add a parcel to game plateau
     */
   public void addParcelle( int x, int y, Parcelle p) {

        p.setXY(x, y);
       //String toPrint =  "/!\\ ACTION /!\\ POSE UNE PARCELLE EN  [" + p.getX() +","+ p.getY()+"]";
       //Log.logger.log(Level.INFO, toPrint);

        p.majParcelle(this); // irrigue les pts de la parcelle, fait les segements, irrigue parcelle si possible
        listParcelle.add(p);
        irriguerParcellesSiPossible();
        for (Parcelle prc : listParcelle){
            prc.majParcelle(this);
        }
    }

    /**
     * @return the list of  parcels on the board
     */
    public ArrayList<Parcelle> getListParcelle() {
        return listParcelle;
    }



    /**
     * @param x   coordinate
     * @param y   coordinate
     * @return true if the specified position has a parcel or not
     */
    public Boolean isEmpty(int x, int y) {
        for (Parcelle p : listParcelle) {
            if (p.getX() == x && p.getY() == y) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param x
     * @param y
     * @return the object parcelle through its coordinates
     */
    public Parcelle getParcelleByCoord(int x, int y) {
        for (Parcelle p : this.listParcelle) {
            if ((p.getX() == x) && (p.getY() == y)) {
                return p;
            }
        }
        return null;
    }

    /**
     * method that irrigate a parcel
     */
   public void irriguerParcellesSiPossible() {
        for (Parcelle p : this.listParcelle) {
            p.majParcelle(this);
        }
    }

    /**
     * print the list of parcels present on the board
     */
   public void printListParcelle() {
        String toPrint = "La Parcelle: ";
        for (Parcelle p : listParcelle) {
            toPrint += String.format("(posX: %d, posY: %d); ", p.getX(), p.getY());
        }
        //logger.log(Level.FINE,toPrint);
    }

    public Panda getPanda(){
       return this.panda;
    }

    public Gardener getGardener(){
        return  this.gardener;
    }

    public void setJoueurJouant(String str){
        joueurJouant=str;
    }

    public void setScoreJ1(int a){
        scoreJ1=a;
    }
    public void setScoreJ2(int b){
        scoreJ2=b;
    }

    public int getTurnPlayed() {
        return turnPlayed;
    }

    public void addOneTurnToCount() {
        this.turnPlayed+=1;
    }

    public void draw(Graphics g){
        List<Parcelle> copy = new ArrayList<Parcelle>(getListParcelle().size());
        for (Parcelle pcopy : getListParcelle()) copy.add(new Parcelle(pcopy));
        for(Parcelle p:copy){
            Polygon hex=new Polygon();
            Draw.createHexa(hex,p.getX(),p.getY());
            if(p.getColor().equals("Etang"))  g.setColor(Color.BLUE);
            else if(p.getColor().equals("Green"))g.setColor(Color.GREEN);
            else if(p.getColor().equals("Pink"))g.setColor(Color.PINK);
            else g.setColor(Color.yellow);
            g.fillPolygon(hex);
            drawIrrigation(hex,p,g);
            //g.setFont();
            g.drawString("(" + p.getX() +"," + p.getY() +") ",hex.xpoints[0] - 10,hex.ypoints[0] -34);
            g.setColor(Color.BLUE);
            if(p.isParcelleIrrigue()) g.drawString("I", hex.xpoints[0] -20 , hex.ypoints[0]- 20);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(p.getBamboo()),hex.xpoints[0] - 20,hex.ypoints[0] -50);
        }
        Point pt= Draw.retPoints(panda.getPosX(),panda.getPosY());
        g.drawImage(imgP,pt.x +10,pt.y +10,null);
        pt= Draw.retPoints(gardener.getPosX(),gardener.getPosY());
        g.drawImage(imgG,pt.x -20,pt.y +10,null);

        Font font = new Font("Courier", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString("Nombre de tours joués :"+turnPlayed,400,20);
        g.drawString("C'est à : "+joueurJouant+" de jouer ",380,80);
        g.setColor(Color.blue);
        g.drawString("Score de JOUEUR 1 : "+scoreJ1,50,80);
        g.setColor(Color.red);
        g.drawString("Score de JOUEUR 2 : "+scoreJ2,700,80);


    }



    private void drawIrrigation(Polygon hex, Parcelle p,Graphics g) {
        g.setColor(Color.BLUE);

        ((Graphics2D) g).setStroke(new BasicStroke(3));
        //g.setFont(new Font("TimesRoman", Font.PLAIN, 5));
        Parcelle copy = new Parcelle(p);
        for(Segement s: copy.getTabSegIrrigues())
        {
            int x= (s.getP1()+2)%6;
            int y=(s.getP2()+2)%6;
            g.drawLine(hex.xpoints[x],hex.ypoints[x],
                    hex.xpoints[y],hex.ypoints[y]);

        }
        g.setColor(Color.BLACK);
    }
}
