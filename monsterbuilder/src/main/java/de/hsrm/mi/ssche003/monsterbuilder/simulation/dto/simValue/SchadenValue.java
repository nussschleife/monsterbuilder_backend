package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue;

import java.util.List;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.AkteurAktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;

public class SchadenValue {
    AkteurAktion aktion;
    Schadensart schadensart;

    @Override
    public String toString() {
        return "Schadensart: "+schadensart.getName();
    }
}
