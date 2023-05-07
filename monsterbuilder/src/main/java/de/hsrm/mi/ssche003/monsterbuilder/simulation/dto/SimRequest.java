package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.SimValue;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;

public class SimRequest {
    Set<Monster> monster = new HashSet<>();
    Gruppe gruppe;
    ArrayList<SimValue> values = new ArrayList<>();
    String userName;
    String simName;
    int durchlaeufe; //in frontend zufuegen

    
    public void setUserName(String name) {
        this.userName = name;
    }
    @JsonIgnore
    public String getUserName() {
        return userName;
    }

    public Set<Monster> getMonster() {
        return monster;
    }

    public void setMonster(Set<Monster> monster) {
        this.monster = monster;
    }

    public Gruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
    }

    public ArrayList<SimValue> getValues() {
        return values;
    }

    public void setValues(ArrayList<SimValue> values) {
        this.values = values;
    }
    public String getSimName() {
        return simName;
    }
    public void setSimName(String simName) {
        this.simName = simName;
    }
    public int getDurchlaeufe() {
        return durchlaeufe;
    }
    public void setDurchlaeufe(int durchlaeufe) {
        this.durchlaeufe = durchlaeufe;
    }

}
