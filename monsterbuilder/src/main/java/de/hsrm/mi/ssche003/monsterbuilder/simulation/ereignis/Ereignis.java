package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public interface Ereignis {
    Optional<Ereignis[]> auslösen(SimState state);

    boolean addToHead();
}
