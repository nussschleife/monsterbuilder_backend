package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public class InitiativeEreignis implements Ereignis{

    public InitiativeEreignis() {
    }
    
    @Override
    public Optional<Ereignis[]> auslösen(SimState state) {
        Akteur[] akteureSortiert = (state.getLebende().stream().sorted(Comparator.comparingInt(akteur -> akteur.wuerfleInitiative())).toArray(Akteur[] :: new));
        LinkedList<Akteur> reihenfolge = new LinkedList<Akteur>(Arrays.asList(akteureSortiert));
        state.setLebende(reihenfolge);
        Ereignis[] folEreignisse ={new NeueRundeEreignis()};
        return Optional.of(folEreignisse); 
    }

    @Override
    public boolean addToHead() {
       return true;
    }
    
}
