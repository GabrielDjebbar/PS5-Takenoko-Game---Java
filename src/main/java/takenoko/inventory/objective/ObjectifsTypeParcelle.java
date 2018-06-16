package takenoko.inventory.objective;

import takenoko.inventory.objective.Objectif;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ADD JAVADOC OF THE OBJECTIF HERE !!!!!
 */
public class ObjectifsTypeParcelle extends Objectif {
    private static final Logger logger= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private  String geometry;
    private int coordinatesObj[][][];
    private int xbis; // le bis c'est juste que j'avais peur d'un conflit de variable avec l'autre x !
    private int ybis; // xbis et ybis correspondrons à la parcelle considérée..




    /**
     * objet objectiftypeparcelle hérité de l'objet objectif
     * au quel on ajoute les paramètres suivants
     * @param geom string, "Straight","Triangle","Curve","Diamond"
     */
    public ObjectifsTypeParcelle(String geom, String color){
        super("Parcelle", color);
        this.geometry=geom;
        String toPrint="Creation ObjectifTypeParcelle : " +
                "La géométrie de l'objectif est "+this.geometry+" color:"+this.color;
        logger.log(Level.FINE,toPrint);
        this.setCoordinatesAndPoints();

        //printCoord();
    }




    /**
     * setter des coordoonés et de points en fonction
     * de la géométrie de chaque objectif
     * Les 3 cas correspondent a chanque fois au deplacement du point de reference de l'objectif .
     * on peut le regarder avec une reference a gauche , droite ou millieu.
     * Ces cas aident a réduire la complexité de l'analyse de parcelle pour le bot2.
     */
    private void setCoordinatesAndPoints(){
        if (this.geometry.equals("Straight")){

            //3 cas ou l'objectif est diagonal (haut droite-bas gauche)

            int[] a1={xbis,ybis};
            int[] b1={xbis+1,ybis+1};
            int[] c1={xbis+2,ybis+2};
            int[][] orientation1 = new int[][] {a1,b1,c1};

            int[] a2={xbis,ybis};
            int[] b2={xbis+1,ybis+1};
            int[] c2={xbis-1,ybis-1};
            int[][] orientation2 = new int[][] {a2,b2,c2};

            int[] a3={xbis,ybis};
            int[] b3={xbis-1,ybis-1};
            int[] c3={xbis-2,ybis-2};
            int[][] orientation3 = new int[][] {a3,b3,c3};



            int[] a4={xbis,ybis};
            int[] b4={xbis-1,ybis+1};
            int[] c4={xbis-2,ybis+2};
            int[][] orientation4 = new int[][] {a4,b4,c4};

            int[] a5={xbis,ybis};
            int[] b5={xbis-1,ybis+1};
            int[] c5={xbis+1,ybis-1};
            int[][] orientation5 = new int[][] {a5,b5,c5};

            int[] a6={xbis,ybis};
            int[] b6={xbis+1,ybis-1};
            int[] c6={xbis+2,ybis-2};
            int[][] orientation6 = new int[][] {a6,b6,c6};

            // 3 cas ou l'objectif est horizontal

            int[] a7={xbis,ybis};
            int[] b7={xbis+2,ybis};
            int[] c7={xbis-2,ybis};
            int[][] orientation7 = new int[][] {a7,b7,c7};

            int[] a8={xbis,ybis};
            int[] b8={xbis+2,ybis};
            int[] c8={xbis+4,ybis};
            int[][] orientation8 = new int[][] {a8,b8,c8};

            int[] a9={xbis,ybis};
            int[] b9={xbis-2,ybis};
            int[] c9={xbis-4,ybis};
            int[][] orientation9 = new int[][] {a9,b9,c9};

            this.coordinatesObj = new int[][][] {orientation1,orientation2,orientation3,orientation4,orientation5,orientation6,orientation7,orientation8,orientation9};
            this.nbDePoints = 4;

        }

        if (this.geometry.equals("Triangle")){
            int[] a={xbis,ybis};
            int[] b={xbis+1,ybis+1};
            int[] c={xbis+2,ybis};
            int[][] orientation1 = new int[][] {a,b,c};

            int[] a2={xbis,ybis};
            int[] b2={xbis+2,ybis};
            int[] c2={xbis+1,ybis-1};
            int[][] orientation2 = new int[][] {a2,b2,c2};

            int[] a3={xbis,ybis};
            int[] b3={xbis+1,ybis-1};
            int[] c3={xbis-1,ybis-1};
            int[][] orientation3 = new int[][] {a3,b3,c3};

            int[] a4={xbis,ybis};
            int[] b4={xbis-1,ybis-1};
            int[] c4={xbis-2,ybis};
            int[][] orientation4 = new int[][] {a4,b4,c4};

            int[] a5={xbis,ybis};
            int[] b5={xbis-2,ybis};
            int[] c5={xbis-1,ybis+1};
            int[][] orientation5 = new int[][] {a5,b5,c5};

            int[] a6={xbis,ybis};
            int[] b6={xbis-1,ybis+1};
            int[] c6={xbis+1,ybis+1};
            int[][] orientation6 = new int[][] {a6,b6,c6};

            this.coordinatesObj = new int[][][] {orientation1,orientation2,orientation3,orientation4,orientation5,orientation6};
            this.nbDePoints = 3;

        }
        if (this.geometry.equals("Curve")){
            int[] a={xbis,ybis}; // vers le haut ,Repere toujours au centre
            int[] b={xbis-1,ybis+1};
            int[] c={xbis,ybis+2};
            int[][] orientation1 = new int[][] {a,b,c};

            int[] a2={xbis,ybis};
            int[] b2={xbis+1,ybis+1};
            int[] c2={xbis,ybis+2};
            int[][] orientation2 = new int[][] {a2,b2,c2};

            int[] a3={xbis,ybis};
            int[] b3={xbis+1,ybis+1};
            int[] c3={xbis+1,ybis-1};

            int[][] orientation3 = new int[][] {a3,b3,c3};

            int[] a4={xbis,ybis}; // vers le haut droite
            int[] b4={xbis+1,ybis+1};
            int[] c4={xbis+3,ybis+1};
            int[][] orientation4 = new int[][] {a4,b4,c4};

            int[] a5={xbis,ybis};
            int[] b5={xbis+2,ybis};
            int[] c5={xbis+3,ybis+1};
            int[][] orientation5 = new int[][] {a5,b5,c5};

            int[] a6={xbis,ybis};
            int[] b6={xbis+2,ybis};
            int[] c6={xbis-1,ybis-1};
            int[][] orientation6 = new int[][] {a6,b6,c6};

            int[] a7={xbis,ybis};// vers le bas droite
            int[] b7={xbis+3,ybis-1};
            int[] c7={xbis+1,ybis-1};
            int[][] orientation7 = new int[][] {a7,b7,c7};

            int[] a8={xbis,ybis};
            int[] b8={xbis+3,ybis-1};
            int[] c8={xbis+2,ybis};
            int[][] orientation8 = new int[][] {a8,b8,c8};

            int[] a9={xbis,ybis};
            int[] b9={xbis-2,ybis};
            int[] c9={xbis+1,ybis-1};
            int[][] orientation9 = new int[][] {a9,b9,c9};

            int[] a10={xbis,ybis};//vers le bas
            int[] b10={xbis,ybis-2};
            int[] c10={xbis-1,ybis-1};
            int[][] orientation10 = new int[][] {a10,b10,c10};

            int[] a11={xbis,ybis};
            int[] b11={xbis,ybis-2};
            int[] c11={xbis+1,ybis-1};
            int[][] orientation11 = new int[][] {a11,b11,c11};

            int[] a12={xbis,ybis};
            int[] b12={xbis-1,ybis-1};
            int[] c12={xbis-1,ybis+1};
            int[][] orientation12 = new int[][] {a12,b12,c12};

            int[] a13={xbis,ybis};//vers le bas gauche
            int[] b13={xbis-3,ybis-1};
            int[] c13={xbis-2,ybis};
            int[][] orientation13 = new int[][] {a13,b13,c13};

            int[] a14={xbis,ybis};
            int[] b14={xbis-3,ybis-1};
            int[] c14={xbis-1,ybis-1};
            int[][] orientation14 = new int[][] {a14,b14,c14};

            int[] a15={xbis,ybis};
            int[] b15={xbis+1,ybis+1};
            int[] c15={xbis-2,ybis};
            int[][] orientation15 = new int[][] {a15,b15,c15};

            int[] a16={xbis,ybis};//vers le haut gauche
            int[] b16={xbis-3,ybis+1};
            int[] c16={xbis-1,ybis+1};
            int[][] orientation16 = new int[][] {a16,b16,c16};

            int[] a17={xbis,ybis};
            int[] b17={xbis-3,ybis+1};
            int[] c17={xbis-2,ybis};
            int[][] orientation17 = new int[][] {a17,b17,c17};

            int[] a18={xbis,ybis};
            int[] b18={xbis-2,ybis};
            int[] c18={xbis+1,ybis-1};
            int[][] orientation18 = new int[][] {a18,b18,c18};


            this.coordinatesObj = new int[][][] {orientation1,orientation2,orientation3,orientation4,orientation5,orientation6
                    ,orientation7,orientation8,orientation9,orientation10,orientation11,orientation12
                    ,orientation13,orientation14,orientation15,orientation16,orientation17,orientation18};
            this.nbDePoints = 2;

        }
        if (this.geometry.equals("Diamond")){
            int[] a1={xbis,ybis};//4 cas ou l'objectif est diagonal (haut droite-bas gauche)
            int[] b1={xbis+2,ybis};
            int[] c1={xbis+1,ybis-1};
            int[] d1={xbis-1,ybis-1};
            int[][] orientation1 = new int[][] {a1,b1,c1};

            int[] a2={xbis,ybis};
            int[] b2={xbis-2,ybis};
            int[] c2={xbis-3,ybis-1};
            int[] d2={xbis-1,ybis-1};
            int[][] orientation2 = new int[][] {a2,b2,c2,d2};

            int[] a3={xbis,ybis};
            int[] b3={xbis-2,ybis};
            int[] c3={xbis-1,ybis+1};
            int[] d3={xbis+1,ybis+1};
            int[][] orientation3 = new int[][] {a3,b3,c3,d3};

            int[] a4={xbis,ybis};
            int[] b4={xbis+1,ybis+1};
            int[] c4={xbis+2,ybis};
            int[] d4={xbis+3,ybis+1};
            int[][] orientation4 = new int[][] {a4,b4,c4,d4};



            int[] a5={xbis,ybis};//4 cas ou l'objectif est diagonal (haut gauche-bas droite)
            int[] b5={xbis+2,ybis};
            int[] c5={xbis+3,ybis-1};
            int[] d5={xbis+1,ybis-1};
            int[][] orientation5 = new int[][] {a5,b5,c5,d5};

            int[] a6={xbis,ybis};
            int[] b6={xbis-2,ybis};
            int[] c6={xbis-1,ybis-1};
            int[] d6={xbis-3,ybis-1};
            int[][] orientation6 = new int[][] {a6,b6,c6,d6};

            int[] a7={xbis,ybis};
            int[] b7={xbis-3,ybis+1};
            int[] c7={xbis-1,ybis+1};
            int[] d7={xbis-2,ybis};
            int[][] orientation7 = new int[][] {a7,b7,c7,d7};

            int[] a8={xbis,ybis};
            int[] b8={xbis-1,ybis+1};
            int[] c8={xbis+1,ybis+1};
            int[] d8={xbis+2,ybis};
            int[][] orientation8 = new int[][] {a8,b8,c8,d8};



            int[] a9={xbis,ybis}; // 4 cas ou l'objectif est horizontal
            int[] b9={xbis-1,ybis-1};
            int[] c9={xbis+1,ybis-1};
            int[] d9={xbis,ybis-2};
            int[][] orientation9 = new int[][] {a9,b9,c9,d9};

            int[] a10={xbis,ybis};
            int[] b10={xbis+1,ybis+1};
            int[] c10={xbis+1,ybis-1};
            int[] d10={xbis+2,ybis};
            int[][] orientation10 = new int[][] {a10,b10,c10,d10};

            int[] a11={xbis,ybis};
            int[] b11={xbis-1,ybis+1};
            int[] c11={xbis+1,ybis+1};
            int[] d11={xbis,ybis+2};
            int[][] orientation11 = new int[][] {a11,b11,c11,d11};

            int[] a12={xbis,ybis};
            int[] b12={xbis-1,ybis-1};
            int[] c12={xbis-1,ybis+1};
            int[] d12={xbis-2,ybis};
            int[][] orientation12 = new int[][] {a12,b12,c12,d12};


            this.nbDePoints = 5;

            this.coordinatesObj = new int[][][] {orientation1,orientation2,orientation3,orientation4
            ,orientation5,orientation6,orientation7,orientation8
            ,orientation9,orientation10,orientation11,orientation12};
        }
    }

    private void printCoord(){
        for(int i = 0; i<this.coordinatesObj.length; i++){
            System.out.println(coordinatesObj[i][0]);
            System.out.println(coordinatesObj[i][1]);
        }
    }

    public int[][][] getCoordinatesObj(){
        return this.coordinatesObj;
    }



    public String getGeometry(){return geometry;}
}


