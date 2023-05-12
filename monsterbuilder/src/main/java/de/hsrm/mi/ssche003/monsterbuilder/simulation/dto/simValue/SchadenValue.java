package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue;

import java.util.List;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.AggressiveAktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;

public class SchadenValue {
    AggressiveAktion aktion;
    Schadensart schadensart;

    @Override
    public String toString() {
        return "Schadensart: "+schadensart.getName();
    }
}