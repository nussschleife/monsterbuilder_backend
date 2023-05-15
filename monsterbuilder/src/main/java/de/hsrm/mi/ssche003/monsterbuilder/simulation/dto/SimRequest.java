package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue.SimValue;

public class SimRequest {
    Monster monster;
    Gruppe gruppe;
    ArrayList<SimValue> values = new ArrayList<>();
    String userName;
    String simName;
    int durchlaeufe; //in frontend zufuegen
    String customSkriptName;

    
    public void setUserName(String name) {
        this.userName = name;
    }
    @JsonIgnore
    public String getUserName() {
        return userName;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
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
    public String getCustomSkriptName() {
        return customSkriptName;
    }
    public void setCustomSkriptName(String customSkriptName) {
        this.customSkriptName = customSkriptName;
    }

}
