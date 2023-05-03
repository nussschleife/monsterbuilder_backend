package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.python.core.PyObject;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public class InitiativeEreignis implements EncounterEreignis {
    
    @Override
    public List<IEreignis> auslösen(SimState state) {
        Akteur[] akteureSortiert = (state.getLebende().stream().sorted(Comparator.comparingInt((t) -> ((int)Math.random()*20)+1)).toArray(Akteur[] :: new));
        ArrayList<Akteur> reihenfolge = new ArrayList<Akteur>(Arrays.asList(akteureSortiert));
        state.setLebende(reihenfolge);
        ArrayList<IEreignis> folEreignisse = new ArrayList<>(1);
        folEreignisse.add(new NeueRundeEreignis());
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
