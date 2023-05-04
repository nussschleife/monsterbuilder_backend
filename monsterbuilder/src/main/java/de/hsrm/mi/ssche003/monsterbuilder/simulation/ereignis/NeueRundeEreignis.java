package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public class NeueRundeEreignis implements EncounterEreignis{

    @Override
    public List<IEreignis> auslösen(SimState state) {
        state.kampfrunden++;
        List<IEreignis> folEreignisse = Arrays.asList(new NeueRundeEreignis());
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
