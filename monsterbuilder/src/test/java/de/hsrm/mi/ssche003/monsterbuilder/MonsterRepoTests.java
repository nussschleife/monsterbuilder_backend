package de.hsrm.mi.ssche003.monsterbuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.MonsterRepo;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MonsterRepoTests {

    @Autowired MonsterRepo repo;

    String name = "Blizzard Arbor";
    int hp = 10;
    byte level = 4;
    byte falschesLevel = -4;
    int falsche_hp = -1;
    byte ac = 18;
    byte falsche_ac = -1;

    @BeforeAll
    public void basischeck() {
        assertThat(repo).isNotNull();
    }

    @BeforeEach
    public void clear() {
        repo.deleteAll();
    }

    @Test
    @DisplayName("Leeres Monster wird nicht gespeichert")
    public void persistiere_leeres_Monster() {
        Monster mon = new Monster(); 
        assertThrows(ConstraintViolationException.class, () -> {
            repo.save(mon);
        });
    }
    
}
