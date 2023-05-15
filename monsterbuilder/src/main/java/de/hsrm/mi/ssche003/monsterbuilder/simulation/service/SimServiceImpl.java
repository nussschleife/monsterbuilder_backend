package de.hsrm.mi.ssche003.monsterbuilder.simulation.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.trait.TraitRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.abilityScore.AbilityScoreRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.angriff.AngriffRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.regelelementService.RegelelementService;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.sprache.SpracheRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.zauber.Effektzauber;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.zauber.ZauberRepository;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue.Level;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class SimServiceImpl implements SimService{

    @Autowired SimpMessagingTemplate template;
    @Autowired EncounterSimulationMaster MASTER;
    private static final Logger logger = LoggerFactory.getLogger(SimServiceImpl.class);
    private Map<String, String> simID_SessionID = new HashMap<String,String>();
    ObjectMapper mapper;
    ObjectWriter writer;

    @Autowired TraitRepository traitRepo;
    @Autowired SpracheRepository sprachRepo;
    @Autowired RegelelementService regelService;
    @Autowired AbilityScoreRepository abilityScoreRepo;
    @Autowired AngriffRepository angriffRepo;
    @Autowired ZauberRepository zauberRepo;

    int hp = 10;
    byte level = 4;
    byte ac = 12;
    byte geschwindigkeit = 30;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        writer = mapper.writer().withDefaultPrettyPrinter();
    }

    @Override
    public void beendeSimulation(String simID) {
       MASTER.beendeAuftrag(simID);
    }

    @Override @Transactional
    public SimResponse starteSimulation(SimRequest request) {
        request = erstelleRequestZumTestBisFrontendGeht(request);
        String simID = MASTER.addAuftrag(request, new DESStrategy(), (SimResult res) -> sendeUpdate(res));
        simID_SessionID.put(simID, request.getUserName());
        return new SimResponse(simID);
    }
    
    private void sendeUpdate(SimResult res) {
        logger.info("sende simUpdate: "+res.getNachricht());
        String result = "json error";
        
        try {
            result = writer.writeValueAsString(res);
        } catch (JsonProcessingException e) {
           logger.error(e.getMessage());
           throw new SimServiceException();
        }
        template.convertAndSend("/queue/user/sim/update/"+simID_SessionID.get(res.getSimID()), result); 
        logger.info(simID_SessionID.get(res.getSimID()));
    }

    @Transactional
    private SimRequest erstelleRequestZumTestBisFrontendGeht(SimRequest fromFrontend) {
        for(int i = 1; i < 5; i++) {
            fromFrontend.getValues().add(new Level(i));
        }
        return fromFrontend;

    }

}
