package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public interface StateChange {

    public void changeState(SimState state);
    
}