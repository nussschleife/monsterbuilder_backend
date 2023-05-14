package de.hsrm.mi.ssche003.monsterbuilder.simulation.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.TraitRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.RegelelementRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScoreRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.AngriffRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.WaffenAngriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.regelelementService.RegelelementService;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.SpracheRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.ZauberRepository;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue.Level;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter.Encounter;
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

    //wieder löschen wenn es aus Frontend kommt
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'beendeSimulation'");
    }

    @Override @Transactional//TODO: SimException usw.
    public SimResponse starteSimulation(SimRequest request) {
        request = erstelleRequestZumTestBisFrontendGeht(request);
        String simID = MASTER.addAuftrag(request, new DESStrategy(), (SimResult res) -> sendeUpdate(res));
        simID_SessionID.put(simID, request.getUserName());
        return new SimResponse(simID);
    }

    @Override
    public Optional<Encounter> findeEncounterMitId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findeEncounterMitId'");
    }
    
    private void sendeUpdate(SimResult res) {
        logger.info("sende simUpdate: "+res.getNachricht());
        String result = "json error";
        
        try {
            result = writer.writeValueAsString(res);
        } catch (JsonProcessingException e) {
           //TODO: SimServiceException
            e.printStackTrace();
        }
        template.convertAndSend("/queue/user/sim/update/"+simID_SessionID.get(res.getSimID()), result); 
        logger.info(simID_SessionID.get(res.getSimID()));
    }

    @Transactional
    private SimRequest erstelleRequestZumTestBisFrontendGeht(SimRequest fromFrontend) {
       // fromFrontend.getGruppe().getAllCharaktere().forEach(c -> c=erstelleKorrektenCharakter(c));
        fromFrontend.getMonster().forEach(m -> m = erstelleKorrektesMonster(m));
        for(int i = 1; i < 5; i++) {
            fromFrontend.getValues().add(new Level(i));
        }
        return fromFrontend;

    }

    @Transactional
    private Monster erstelleKorrektesMonster(Monster monster) {
     //   monster.setId(generateIDBisFrontendGeht());
        monster.setAlleZauber(Set.of(zauberRepo.findFirstEffektzauber().get()));
        return monster;
    }

    @Transactional
    private Charakter erstelleKorrektenCharakter(Charakter fromfrontend) {
        fromfrontend.setName(fromfrontend.getName()).setLebenspunkte(hp).setRuestungsklasse(ac).setGeschwindigkeit_ft(geschwindigkeit);
        fromfrontend.setAlleAngriffe(Set.of(angriffRepo.findFirstByOrderByIdAsc().get())); 
        fromfrontend.setId(generateIDBisFrontendGeht());
        return fromfrontend;
    }

    public Long generateIDBisFrontendGeht() {
        long leftLimit = 1L;
        long rightLimit = 420L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
