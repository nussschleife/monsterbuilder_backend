package de.hsrm.mi.ssche003.monsterbuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.MonsterRepo;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterService.MonsterService;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = MonsterbuilderApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MonsterRestApiTests {

    @Autowired MockMvc mockMvc;
    @Autowired MonsterRepo repo;
    @Autowired MonsterService service;

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
    public void leereUndFuelleMonsterDB() {
        repo.deleteAll();
        repo.save((Monster) new Monster().setName(name).setLebenspunkte(hp).setRuestungsklasse(ac).setGeschwindigkeit_ft(geschwindigkeit));
        repo.save((Monster) new Monster().setName("Hans Juergen").setLebenspunkte(hp).setRuestungsklasse(ac).setGeschwindigkeit_ft(geschwindigkeit));
    }

    @Test
    @DisplayName("hole alle Monster(2) aus DB per GET request an Monsterapi")
    public void holeAlleMonster() throws Exception {
       mockMvc.perform( get(PATH + "/alle")
        .accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)));;
    }
    
    //TODO: alle service methoden testen
}
