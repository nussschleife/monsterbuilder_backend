package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.trait;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import jakarta.persistence.Entity;

public class StatusTrait extends Trait{
    
    private Schadensart typ;
    private int wert; //negativer Wert ist resistenz, pos. schwäche, 0 immunität
    private boolean istImmun = false;

    public boolean bestimmtImmunität() {
        return istImmun;
    }

    public boolean bestimmtResistenz() {
        return wert < 0 && !istImmun;
    }

    public boolean bestimmtSchwäche(){
        return wert > 0 && !istImmun;
    }

    public Schadensart getTyp() {
        return typ;
    }

    public int getWert() {
        return wert;
    }

    
}
