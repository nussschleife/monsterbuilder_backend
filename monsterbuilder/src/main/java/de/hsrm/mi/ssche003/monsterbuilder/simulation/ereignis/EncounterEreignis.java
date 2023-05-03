package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;


import java.util.List;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public interface EncounterEreignis extends IEreignis {

    List<IEreignis> auslösen(SimState state);
    
}
