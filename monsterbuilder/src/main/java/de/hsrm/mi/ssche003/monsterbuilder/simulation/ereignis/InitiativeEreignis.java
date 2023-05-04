package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public class InitiativeEreignis implements EncounterEreignis {
    
    @Override
    public List<IEreignis> auslösen(SimState state) {
        String[] akteureSortiert = (state.getLebende().stream().sorted(Comparator.comparingInt((t) -> ((int)Math.random()*20)+1)).toArray(String[] :: new));
        ArrayList<String> reihenfolge = new ArrayList<String>(Arrays.asList(akteureSortiert));
        state.setLebende(reihenfolge);
        ArrayList<IEreignis> folEreignisse = new ArrayList<>(0);
        folEreignisse.add(new NeueRundeEreignis());
        for( String akteur : state.getLebende()) {
            folEreignisse.add( new AktionEreignis(akteur));
        }
        return folEreignisse;
    }

    public Boolean addToHead() {
       return false;
    }

    @Override
    public Optional<StateChange> getChange() {
        return Optional.empty();
    }

}
