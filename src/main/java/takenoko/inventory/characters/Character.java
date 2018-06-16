package takenoko.inventory.characters;



public class Character {
    private int posX;
    private int posY;


    public Character(){
        this.posX=0;
        this.posY=0;
    }



    /**
     * a method that modify the position of the given character
     * @param x the coordinate X of the given character
     * @param y the coordinate Y of the given character
     */
    public void setXY(int x, int y){
        this.posX = x;
        this.posY = y;
    }


    /**
     * @return the x coordinate of the character
     */
    public int getPosX(){
        return posX;
    }

    /**
     * @return the y coordinate of the character
     */
    public int getPosY(){
        return posY;
    }
}
