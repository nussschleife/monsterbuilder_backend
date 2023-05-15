package de.hsrm.mi.ssche003.monsterbuilder.simulation.service.ereignis;

import java.util.List;
import java.util.Optional;

public interface IEreignis {
    public Boolean addToHead();
    public Optional<StateChange> getChange();
    public List<IEreignis> generiereFolgeEreignis();
    
}
