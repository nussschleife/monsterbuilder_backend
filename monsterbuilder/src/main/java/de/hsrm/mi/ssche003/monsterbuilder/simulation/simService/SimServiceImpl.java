package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter.Encounter;

@Service
public class SimServiceImpl implements SimService{

    @Override
    public void beendeSimulation(String simID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'beendeSimulation'");
    }

    @Override
    public SimResponse starteSimulation(SimRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'starteSimulation'");
    }

    @Override
    public Optional<Encounter> findeEncounterMitId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findeEncounterMitId'");
    }
    
}
