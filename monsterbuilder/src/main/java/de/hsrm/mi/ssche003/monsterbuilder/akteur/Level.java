package de.hsrm.mi.ssche003.monsterbuilder.akteur;

public class Level extends Number implements SimValue{
    int level; 

    public Level(int value) {
        this.level = value;
    }

    @Override
    public int intValue() {
       return level;
    }

    @Override
    public long longValue() {
       return Long.valueOf(level);
    }

    @Override
    public float floatValue() {
        return Float.valueOf(level);
    }

    @Override
    public double doubleValue() {
        return Double.valueOf(level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString(){
        return String.valueOf(level);
    }    
}
