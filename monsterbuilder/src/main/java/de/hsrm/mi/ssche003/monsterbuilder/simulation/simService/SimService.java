package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter.Encounter;

public interface SimService {
    public void beendeSimulation(String simID);
    public SimResponse starteSimulation(SimRequest request);
    public Optional<Encounter> findeEncounterMitId(Long id);
}
