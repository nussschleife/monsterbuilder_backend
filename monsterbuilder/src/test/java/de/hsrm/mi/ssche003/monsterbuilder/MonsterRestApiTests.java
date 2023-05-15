package de.hsrm.mi.ssche003.monsterbuilder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.api.AkteurRestApi;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.dto.MonsterDTO;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.MonsterRepo;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.monsterService.MonsterService;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.trait.Trait;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.trait.TraitRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.abilityScore.AbilityScoreName;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.abilityScore.AbilityScoreRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.angriff.Angriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.angriff.AngriffRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.regelelementService.RegelelementService;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.sprache.Sprache;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.sprache.SpracheRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import jakarta.transaction.Transactional;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = MonsterbuilderApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableWebMvc
public class MonsterRestApiTests {

    @Autowired MockMvc mockMvc;
    @Autowired MonsterRepo repo;
    @Autowired MonsterService service;
    @Autowired AkteurRestApi api;
    @Autowired TraitRepository traitRepo;
    @Autowired SpracheRepository sprachRepo;
    @Autowired RegelelementService regelService;
    @Autowired AbilityScoreRepository abilityScoreRepo;
    @Autowired AngriffRepository angriffRepo;

    static final Logger logger = LoggerFactory.getLogger(MonsterRestApiTests.class);

    final String PATH = "/api/akteur/monster";

    String name = "Blizzard Arbor";
    int hp = 10;
    byte level = 4;
    byte falschesLevel = -4;
    int falsche_hp = -1;
    byte ac = 18;
    byte falsche_ac = -1;
    byte geschwindigkeit = 30;
    byte falscheGeschwindigeit = -1;
    

    @BeforeAll
    public void pruefeBeans() {
        assertNotNull(repo);
        assertNotNull(service);
        assertNotNull(mockMvc);
        assertNotNull(api);
        assertNotNull(angriffRepo);
        assertNotNull(traitRepo);
        assertNotNull(sprachRepo);
        assertNotNull(abilityScoreRepo);
        assertNotNull(regelService);

        //pruefe dass repos gefüllt sind
        assertTrue(angriffRepo.count() > 0);
        assertTrue(abilityScoreRepo.count() > 0);
        assertTrue(traitRepo.count() > 0);
        assertTrue(sprachRepo.count() > 0);
    }

    @BeforeEach
    public void leere() {
        repo.deleteAll();
    }

    @Test @Transactional
    @DisplayName("hole alle Monster(2) aus DB per GET request an Monsterapi")
    public void holeAlleMonster() throws Exception {
        repo.save(erstelleKorrektesMonster("hand juergen"));
        repo.save(erstelleKorrektesMonster("paula"));
        assertTrue(repo.count() == 2);
        mockMvc.perform( get(PATH + "/alle")
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());//andExpect(jsonPath("$", hasSize(6)));;
    }
    
    @Test @Transactional
    @DisplayName("POST new Monster")
    public void post_neues_Monster() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(erstelleDTO());
 
        mockMvc.perform(post(PATH).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(request)).andExpect(status().isOk());
        assertTrue(repo.count() == 1);
    }

    @Test @Transactional
    @DisplayName("GET Monster mit id")
    public void get_Monster_mit_id() throws Exception{

        repo.save(erstelleKorrektesMonster("bernd"));
        repo.save(erstelleKorrektesMonster("olaf"));
        assertTrue(repo.count() == 2);
        long id = repo.findAll().get(0).getId();
        mockMvc.perform(get(PATH + "/hole/"+id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test @Transactional
    @DisplayName("PUT bearbeite Monster")
    public void put_aendere_monster() throws Exception{

        MonsterDTO dto = erstelleDTO();
        AbilityScore neu = new AbilityScore();

        neu.setScore(15);
        neu.setScoreName(AbilityScoreName.CHARISMA);
        neu.setName(neu.getScoreName().toString());
        neu.setId(-1l);

        Monster monster = repo.save(erstelleKorrektesMonster("heinz"));
        assertTrue(repo.count() == 1);

        dto.setId(monster.getId()).setName("Version2").getAbilityScores()[0] = neu;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(dto);
        
 
        String response = mockMvc.perform(post(PATH).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE).content(request)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(dto.getName()));
        Monster responseMonster = mapper.readValue(response, Monster.class);
        //ist false obwohl die werte gleich sind??
        assertTrue(responseMonster.getAbilityScores().stream().anyMatch((AbilityScore score) -> {return score.getScore() == neu.getScore() && score.getName() == neu.getName();}));    
    }   
    
    @Transactional
    private MonsterDTO erstelleDTO() {
        MonsterDTO dto = new MonsterDTO().setId(-1l).setAlignment(Alignment.CHAOTIC_EVIL).setGeschwindigkeit_ft(geschwindigkeit)
        .setLebenspunkte(hp).setName(name).setRuestungsklasse(ac);

        //hole Elemente aus Repos
        Trait trait = traitRepo.findAll().get(0);
        dto.setTraits(new Trait[]{trait});
        dto.setAbilityScores(new AbilityScore[]{abilityScoreRepo.findAll().get(0)});
        dto.setAngriffe(new Angriff[]{angriffRepo.findAll().get(0)});
        dto.setSprachen(new String[]{sprachRepo.findAll().get(0).getName()});

        return dto;
    }

    @Transactional
    private Monster erstelleKorrektesMonster(String name) {
        Monster monster =  (Monster) new Monster().setName(name).setLebenspunkte(hp).setRuestungsklasse(ac).setGeschwindigkeit_ft(geschwindigkeit);
        monster.setAlleTraits(Set.of(traitRepo.findAll().get(0)));
        monster.setAbilityScores(Set.of(abilityScoreRepo.findAll().get(0)));
        monster.setAlleAngriffe(Set.of(angriffRepo.findAll().get(0)));
        monster.setSprachen(Set.of(sprachRepo.findAll().get(0)));
        return monster;
    }

}