package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter.Encounter;

@Service
public class SimServiceImpl implements SimService{

    @Autowired SimpMessagingTemplate template;
    final EncounterSimulationMaster MASTER = EncounterSimulationMaster.getInstance();

    @Override
    public void beendeSimulation(String simID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'beendeSimulation'");
    }

    @Override
    public SimResponse starteSimulation(SimRequest request) {
        String simID = MASTER.addAuftrag(request, null, (SimResult res) -> sendeUpdate(res));
        return new SimResponse(simID);
    }

    @Override
    public Optional<Encounter> findeEncounterMitId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findeEncounterMitId'");
    }
    
    private void sendeUpdate(SimResult res) {
        template.convertAndSendToUser(res.getUserSessionID(), "/user/queue", res); 
    }
}
