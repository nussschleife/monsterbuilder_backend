package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

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

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Level;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.TraitRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScoreRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.AngriffRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.regelelementService.RegelelementService;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.SpracheRepository;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter.Encounter;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class SimServiceImpl implements SimService{

    @Autowired SimpMessagingTemplate template;
    final EncounterSimulationMaster MASTER = EncounterSimulationMaster.getInstance();
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

    int hp = 10;
    byte level = 4;
    byte ac = 18;
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
        logger.info("sende simUpdate: "+res.getMessage());
        String result = "json error";
        
        try {
            result = writer.writeValueAsString(res);
        } catch (JsonProcessingException e) {
           //TODO: SimServiceException
            e.printStackTrace();
        }
        template.convertAndSend("/queue/user/sim/update/"+simID_SessionID.get(res.getSimID()), result); 
    }

    @Transactional
    private SimRequest erstelleRequestZumTestBisFrontendGeht(SimRequest fromFrontend) {
        fromFrontend.getMonster().add(erstelleKorrektesMonster("bob"));
        Gruppe radio = new Gruppe();
        radio.setName("radio");
        radio.getAllCharaktere().add(erstelleKorrektenCharakter("brutus"));
        radio.getAllCharaktere().add(erstelleKorrektenCharakter("rasmodeus"));
        fromFrontend.setGruppe(radio);
        for(int i = 1; i < 6; i++) {
            fromFrontend.getValues().add(new Level(i));
        }
        
        return fromFrontend;

    }

    @Transactional
    private Monster erstelleKorrektesMonster(String name) {
        Monster monster =  (Monster) new Monster().setName(name).setLebenspunkte(hp).setRuestungsklasse(ac).setGeschwindigkeit_ft(geschwindigkeit);
     //   monster.setAbilityScores(Set.of(abilityScoreRepo.findAll().get(0)));
        monster.setAlleAngriffe(Set.of(angriffRepo.findAll().get(0)));
        return monster;
    }

    @Transactional
    private Charakter erstelleKorrektenCharakter(String name) {
        Charakter charakter =  (Charakter) new Charakter().setName(name).setLebenspunkte(hp).setRuestungsklasse(ac).setGeschwindigkeit_ft(geschwindigkeit);
      //  charakter.setAbilityScores(Set.of(abilityScoreRepo.findAll().get(0)));
        charakter.setAlleAngriffe(Set.of(angriffRepo.findAll().get(0)));
        return charakter;
    }
}
