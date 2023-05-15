package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.api;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.charakterService.CharakterService;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.dto.AkteurInitResponse;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.dto.MonsterDTO;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.exception.MonsterServiceException;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.monsterService.MonsterService;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.trait.Trait;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.regelelementService.RegelelementService;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.sprache.Sprache;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/akteur")
public class AkteurRestApi {

    @Autowired MonsterService monsterService;
    @Autowired RegelelementService regelelementService;
    @Autowired CharakterService charakterService;
    static final Logger logger = org.slf4j.LoggerFactory.getLogger(AkteurRestApi.class);


    @GetMapping("/monster/alle")
    public List<Monster> holeAlleMonster() {
        return monsterService.findeAlleMonster();
    }

    @GetMapping("/monster/hole/{id}")
    public Monster holeMonster(@PathVariable Long id) {
        Monster monster = null;
        try{
             monster = monsterService.findeMonsterMitId(id); 
        } catch(MonsterServiceException mse) {}
        return monster;
    }

    @DeleteMapping("/monster/{id}")
    public boolean deleteLöscheMonster(@PathVariable Long id) {
        return false;
    }

    @PostMapping("/monster") @Transactional
    public ResponseEntity<Monster> addMonster(@Valid @RequestBody Monster monster, BindingResult result) {
        if(!result.hasErrors()) {
           
            logger.info("NEUES MONSTER ERHALTEN: ");
            Set<Sprache> sprachen = new HashSet<>();
            Sprache elementSprache = new Sprache();

            monster.setSprachen(sprachen);

            try{
                monster = monsterService.editMonster(monster);
            } catch(MonsterServiceException mse) {
                logger.error("errorrr");
                return ResponseEntity.badRequest().body(monster);
            }
           
            logger.info(String.valueOf(monsterService.findeAlleMonster().size()));
            return ResponseEntity.ok().body(monster);
        } return ResponseEntity.badRequest().body(monster);

    }

    @GetMapping("/init")
    public AkteurInitResponse getInitialValues() {
        logger.info("INIT AKTEUR");
        AkteurInitResponse init = new AkteurInitResponse();
        init.setAlleTraits(monsterService.findeAlleTraits().toArray(new Trait[0]));
        return init;
    }

    @GetMapping("/gruppe/{id}")
    public Gruppe getGruppeMitId(@PathVariable Long id) {
        return charakterService.findeGruppeMitId(id);
    }

    @GetMapping("/charakter/{id}")
    public Charakter get_holeCharakterMitId(@PathVariable Long id) {
        return charakterService.findeCharakterMitId(id);
    }

    @PostMapping("/gruppe/bearbeiten")
    public ResponseEntity<Gruppe> post_bearbeiteGruppe(@RequestBody Gruppe bearbeiteteGruppe) {
        Gruppe gespeichert = charakterService.bearbeiteGruppe(bearbeiteteGruppe);
        return ResponseEntity.ok().body(gespeichert);
    }

    @PostMapping("/charakter/bearbeiten")
    public ResponseEntity<Charakter> post_bearbeiteCharakter(@RequestBody Charakter charakter) {
        Charakter gespeichert = charakterService.bearbeiteCharakter(charakter);
        return ResponseEntity.ok().body(gespeichert);
    }

    @GetMapping("/charakter/standard/{level}/{klasse}") @Transactional
    public ResponseEntity<Charakter> get_holeStandardCharakter(@PathVariable int level, @PathVariable String klasse) {
        return ResponseEntity.ok().body(charakterService.holeStandardCharakter(level, klasse)); 
    }

}
