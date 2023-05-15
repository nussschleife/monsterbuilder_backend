package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.gruppe.Gruppe;

public class Level extends Number implements SimValue{
    int level; 
    Gruppe gruppe;

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

    public Gruppe getGruppe() {
        return gruppe;
    }    

    @Override
    public String toString() {
        return "Level: "+level;
    }
    
}
