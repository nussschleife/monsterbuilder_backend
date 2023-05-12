package de.hsrm.mi.ssche003.monsterbuilder.akteur.simValue;

import java.util.List;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.effekt.Condition;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Effektzauber;

public class ConditionValue implements SimValue{

    private Effektzauber zauber;
    private Condition condition;

    public Effektzauber getZauber() {
        return zauber;
    }
    public void setZauber(Effektzauber zauber) {
        this.zauber = zauber;
    }
    public Condition getConditions() {
        return condition;
    }
    public void setConditions(Condition conditions) {
        this.condition = conditions;
    }
    
}
