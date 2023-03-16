package de.hsrm.mi.ssche003.monsterbuilder.skill;

import de.hsrm.mi.ssche003.monsterbuilder.Schaden;
import de.hsrm.mi.ssche003.monsterbuilder.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.Zauber;

public class Schadenszauber extends Zauber{
    Schadensart typ;
    Schaden schaden;
    
    public Schadensart getTyp() {
        return typ;
    }
    public void setTyp(Schadensart typ) {
        this.typ = typ;
    }
    public Schaden getSchaden() {
        return schaden;
    }
    public void setSchaden(Schaden schaden) {
        this.schaden = schaden;
    }
    
    
}
