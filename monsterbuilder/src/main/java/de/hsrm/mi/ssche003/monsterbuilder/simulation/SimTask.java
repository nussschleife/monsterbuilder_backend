package de.hsrm.mi.ssche003.monsterbuilder.simulation;

import java.util.concurrent.Callable;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;

public interface SimTask extends Callable<SimResult>{
    
    public String getSimID();
}
