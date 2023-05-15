package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.List;
import java.util.Optional;

public interface Ereignis {
    public Boolean addToHead();
    public Optional<StateChange> getChange();
    public List<Ereignis> generiereFolgeEreignis();
    
}
