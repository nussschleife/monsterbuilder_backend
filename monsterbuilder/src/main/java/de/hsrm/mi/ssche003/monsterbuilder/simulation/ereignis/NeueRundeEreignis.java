package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.python.core.PyObject;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public class NeueRundeEreignis implements EncounterEreignis{

    @Override
    public List<IEreignis> auslösen(SimState state) {
        state.kampfrunden++;
        int size = state.getLebende().size();
        List<IEreignis> folEreignisse = new ArrayList<>();
        for( Akteur akteur : state.getLebende()) {
            folEreignisse.add( new AktionEreignis(akteur));
        }
        folEreignisse.add(size-1, new NeueRundeEreignis());
        return folEreignisse; 
    }

    @Override
    public Boolean addToHead() {
        return false;
    }

    @Override
    public Optional<StateChange> getChange() {
        return Optional.empty();
    }

}
