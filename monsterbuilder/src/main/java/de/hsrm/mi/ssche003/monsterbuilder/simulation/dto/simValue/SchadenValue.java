package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.AkteurAktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.schaden.Schadensart;

public class SchadenValue {
    AkteurAktion aktion;
    Schadensart schadensart;

    @Override
    public String toString() {
        return "Schadensart: "+schadensart.getName();
    }
}
