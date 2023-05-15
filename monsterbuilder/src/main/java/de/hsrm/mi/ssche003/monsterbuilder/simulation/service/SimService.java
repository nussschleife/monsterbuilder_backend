package de.hsrm.mi.ssche003.monsterbuilder.simulation.service;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;

public interface SimService {
    public void beendeSimulation(String simID);
    public SimResponse starteSimulation(SimRequest request);
}
