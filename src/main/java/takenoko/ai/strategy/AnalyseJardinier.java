package takenoko.ai.strategy;

import takenoko.inventory.objective.Objectif;
import takenoko.inventory.objective.ObjectifsTypeJardinier;

import java.awt.*;
import java.util.ArrayList;

public class AnalyseJardinier{
    public ArrayList<Point> directionJardinier =new ArrayList<>();
    public ArrayList<Integer> nombrePassage =new ArrayList<>();
    public ArrayList<Point> ParcellesAIrriguer =new ArrayList<>();
    public ArrayList<Point> parcellesAPoser =new ArrayList<>();
    public ObjectifsTypeJardinier objectif;
    AnalyseJardinier(ObjectifsTypeJardinier objectif){
        this.objectif=objectif;
    }

}
