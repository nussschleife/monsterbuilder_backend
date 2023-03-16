package de.hsrm.mi.ssche003.monsterbuilder.trait;

import de.hsrm.mi.ssche003.monsterbuilder.Schadensart;
import jakarta.persistence.Entity;

@Entity
public class StatusTrait extends Trait{
    
    private Schadensart typ;
    private int wert; //negativer Wert ist resistenz, pos. schwäche
    private boolean istImmun = false;
}
