package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;

public class InitiativeEreignis implements Ereignis{
    List<Akteur> reihenfolge;

    public InitiativeEreignis(List<Akteur> akteure) {
        reihenfolge = akteure;
    }
    
    @Override
    public Optional<Ereignis[]> auslösen() {
        reihenfolge.sort(Comparator.comparingInt(akteur -> akteur.wuerfleInitiative()));
        return Optional.empty(); //Rundenereignis??
    }
    
}
