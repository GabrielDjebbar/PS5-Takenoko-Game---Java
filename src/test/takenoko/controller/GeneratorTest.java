package takenoko.controller;

import org.junit.Test;
import takenoko.inventory.board.Parcelle;
import takenoko.inventory.objective.ObjectifsTypePanda;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GeneratorTest {

    @Test
    public void getObjectifTypePanda() throws Exception {
        Generator g = new Generator();
        ArrayList<ObjectifsTypePanda> stockObjPanda = new ArrayList<>();
        int nbGreenObj = 0;
        int nbPinkObj = 0;
        int nbYellowObj = 0;
        int nbMulticolorObj = 0;
        int nbObj = 0;

        for (int i = 0 ; i<15;i++){
            ObjectifsTypePanda obj = g.getObjectifTypePanda();
            stockObjPanda.add(obj);
        }

        for(ObjectifsTypePanda obj : stockObjPanda){
            if(obj.getColor().equals("Green")){
                nbGreenObj++;
            }
            if(obj.getColor().equals("Pink")){
                nbPinkObj++;
            }
            if(obj.getColor().equals("Yellow")){
                nbYellowObj++;
            }
            if(obj.getColor().equals("multicolor")){
                nbMulticolorObj++;
            }
            nbObj++;
        }

        boolean test = ((nbGreenObj == 5) && (nbPinkObj == 3) && (nbYellowObj == 4) && (nbMulticolorObj == 3));
        assertTrue(test);
    }

    /**
     * Un test qui vérifie qu'on a le bon nombre de parcelles initialisés
     * de part leur nombre total, les nombres par chaque couleurs, et pour
     * chaque aménagement
     * On crée une liste des nombres attendus pour chaque élément,
     * ensuite on vide le deck de parcelle, et on incrémente les valeurs
     * pour chaque parcelle (par sa couleurs, et son aménagemnet)
     * ensuite on compare les deux listes expectedList et actualList
     */
    @Test
    public void checkGeneratorParcelle() throws Exception {
        Generator g = new Generator();
        boolean expected = true;

        // vérifer si on a le bon nombre de parcelles dans le generator

        int expectedParcelleStackSize = 27;
        int actualParcelleStackSize = g.nbParcellesDispo();

        if(expectedParcelleStackSize != actualParcelleStackSize){
            expected = false;
        }


        // vérifier qu'on a le bon nombre de parcelles pour chaque aménagement / couleur.

        // l'ordre de la listeeeeeee
        // green---
        // 0 noAm, 1 bassinAm, 2 noPandaAm, 3 doubleBamboAm
        // pink---
        // 4 noAm, 5 bassinAm, 6 noPandaAm, 7 doubleBamboAm
        // yellow---
        // 8 noAm, 9 bassinAm, 10 noPandaAm, 11 doubleBamboAm

        ArrayList<Integer> expectedList = new ArrayList<Integer>(Arrays.asList(6,2,2,1,4,1,1,1,6,1,1,1));
        ArrayList<Integer> actualList = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0));




        for(int i = 0; i<27; i++){
            Parcelle p = g.getRandomParcelle();
            int value;
            if(p.getColor().equals("Green")){
                switch(p.getAmenagement().getType()){
                    case "bassin" : {
                        value = actualList.get(1);
                        actualList.set(1,++value);
                        break;
                    }
                    case "nopanda" : {
                        value = actualList.get(2);
                        actualList.set(2,++value);
                        break;
                    }
                    case "doublebambo" : {
                        value = actualList.get(3);
                        actualList.set(3,++value);
                        break;
                    }
                    default:{
                        value = actualList.get(0);
                        actualList.set(0,++value);
                        break;
                    }
                }
            }

            if(p.getColor().equals("Pink")){
                switch(p.getAmenagement().getType()){
                    case "bassin" : {
                        value = actualList.get(5);
                        actualList.set(5,++value);
                        break;
                    }
                    case "nopanda" : {
                        value = actualList.get(6);
                        actualList.set(6,++value);
                        break;
                    }
                    case "doublebambo" : {
                        value = actualList.get(7);
                        actualList.set(7,++value);
                        break;
                    }
                    default:{
                        value = actualList.get(4);
                        actualList.set(4,++value);
                        break;
                    }
                }
            }

            if(p.getColor().equals("Yellow")){
                switch(p.getAmenagement().getType()){
                    case "bassin" : {
                        value = actualList.get(9);
                        actualList.set(9,++value);
                        break;
                    }
                    case "nopanda" : {
                        value = actualList.get(10);
                        actualList.set(10,++value);
                        break;
                    }
                    case "doublebambo" : {
                        value = actualList.get(11);
                        actualList.set(11,++value);
                        break;
                    }
                    default:{
                        value = actualList.get(8);
                        actualList.set(8,++value);
                        break;
                    }
                }
            }


        }


        for(int k = 0; k<12;k++){
            int act = actualList.get(k);
            int exp = expectedList.get(k);
            if(act != exp){
                expected = false;
            }
        }


        assertTrue(expected);
    }
}