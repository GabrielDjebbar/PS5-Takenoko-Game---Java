package takenoko.graphics;

import java.awt.*;

public class Draw {
    private static final int CENTERX =475;
    private static final int CENTERY = 275;
    private static final int XSTEP=  38 ; //38; // 34
    private static final int YSTEP = 64; //60

    /**
     * @param p hexagon to draw
     * @param x x cartesian coordinate to place hexagon
     * @param y y x cartesian coordinate to place hexagon
     */
    public static void createHexa(Polygon p, int x , int y){
        for(int i =0; i< 6;i++){
            p.addPoint((int)(CENTERX + (x*XSTEP) + 40* Math.sin(i * 2 * Math.PI / 6)),
                    (int) (CENTERY + (-y*YSTEP) +  40* Math.cos(i * 2 * Math.PI / 6)));
        }
    }

    /**
     * @param x x cartesian coordinate
     * @param y x cartesian coordinate
     * @return return coordinates to draw on the graphics part of the fame
     */
    public static Point retPoints(int x, int y){
        return new Point(CENTERX + (x*XSTEP), CENTERY + (-y*YSTEP));
    }
    }
