package de.hsrm.mi.ssche003.monsterbuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AcceptAction;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.MonsterRepo;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterService.MonsterService;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterapi.MonsterRestApi;
import de.hsrm.mi.ssche003.monsterbuilder.model.MonsterDTO;

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
    @Autowired MonsterRestApi api;
    static final Logger logger = LoggerFactory.getLogger(MonsterRestApiTests.class);

    final String PATH = "/api/monster";

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
    }

    @BeforeEach
    public void leere() {
        repo.deleteAll();
    }

    @Test
    @DisplayName("hole alle Monster(2) aus DB per GET request an Monsterapi")
    public void holeAlleMonster() throws Exception {
        repo.save((Monster) new Monster().setName(name).setLebenspunkte(hp).setRuestungsklasse(ac).setGeschwindigkeit_ft(geschwindigkeit));
        repo.save((Monster) new Monster().setName("Hans Juergen").setLebenspunkte(hp).setRuestungsklasse(ac).setGeschwindigkeit_ft(geschwindigkeit));
        assertTrue(repo.count() == 2);
        mockMvc.perform( get(PATH + "/alle")
        .accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)));;
    }
    
    @Test
    @DisplayName("POST new Monster")
    public void post_neues_Monster() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(erstelleDTO());
 
        mockMvc.perform(post(PATH + "/neu").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(request)).andExpect(status().isOk());
        assertTrue(repo.count() == 1);
    }

    @Test
    @DisplayName("GET Monster mit id")
    public void get_Monster_mit_id() throws Exception{

        repo.save(erstelleKorrektesMonster());
        assertTrue(repo.count() == 1);
        long id = repo.findAll().get(0).getId();
        mockMvc.perform(get(PATH + "/hole/"+id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    @DisplayName("PUT bearbeite Monster")
    public void put_aendere_monster() throws Exception{

        MonsterDTO dto = erstelleDTO();
       
        Monster monster = repo.save(erstelleKorrektesMonster());
        assertTrue(repo.count() == 1);

        dto.setId(monster.getId()).setName("Version2");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(dto);
        
 
        String response = mockMvc.perform(put(PATH + "/bearbeite").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE).content(request)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertTrue(response.contains(dto.getName()));
    
    }   
    
    
    private MonsterDTO erstelleDTO() {
        return new MonsterDTO().setId(-1l).setAlignment(Alignment.CHAOTIC_EVIL).setGeschwindigkeit_ft(geschwindigkeit)
        .setLebenspunkte(hp).setName(name).setRuestungsklasse(ac);
    }

    private Monster erstelleKorrektesMonster() {
        return (Monster) new Monster().setName(name).setLebenspunkte(hp).setRuestungsklasse(ac).setGeschwindigkeit_ft(geschwindigkeit);
    }

}
