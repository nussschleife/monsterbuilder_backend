package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.service.SimState;

public class RundenChange implements StateChange{

    @Override
    public void changeState(SimState state) {
        state.erhoeheRunden();
    }
    
}
