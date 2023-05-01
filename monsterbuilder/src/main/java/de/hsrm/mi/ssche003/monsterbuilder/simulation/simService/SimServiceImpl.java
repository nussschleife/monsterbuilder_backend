package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter.Encounter;
import jakarta.annotation.PostConstruct;

@Service
public class SimServiceImpl implements SimService{

    @Autowired SimpMessagingTemplate template;
    final EncounterSimulationMaster MASTER = EncounterSimulationMaster.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(SimServiceImpl.class);
    private Map<String, String> simID_SessionID = new HashMap<String,String>();
    ObjectMapper mapper;
    ObjectWriter writer;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    @Override
    public void beendeSimulation(String simID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'beendeSimulation'");
    }

    @Override //TODO: SimException usw.
    public SimResponse starteSimulation(SimRequest request) {
        String simID = "";
        simID = MASTER.addAuftrag(request, new DESStrategy(), (SimResult res) -> sendeUpdate(res));
        simID_SessionID.put(simID, request.getUserName());
        return new SimResponse(simID);
    }

    @Override
    public Optional<Encounter> findeEncounterMitId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findeEncounterMitId'");
    }
    
    private void sendeUpdate(SimResult res) {
        logger.info(res.getMessage());
        String result = "json error";
        try {
            result = writer.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       // template.convertAndSend( "/queue/user/sim/update"+res.getUserSessionID(), result); 
    }
}
