package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Optional;

public interface IEreignis {
    public Boolean addToHead();
    Optional<StateChange> getChange();
    
}
