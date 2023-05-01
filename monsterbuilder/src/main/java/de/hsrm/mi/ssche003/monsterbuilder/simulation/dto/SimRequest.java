package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto;
import java.util.ArrayList;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.SimValue;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;

public class SimRequest {
    Monster monster;
    Gruppe gruppe;
    ArrayList<SimValue> values;
    String userName;

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return userName;
    }
}
