package de.hsrm.mi.ssche003.monsterbuilder.akteur.api;

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
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.charakterService.CharakterService;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.dto.InitResponse;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.dto.MonsterDTO;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.dto.ValidResponse;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.exception.MonsterServiceException;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterService.MonsterService;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.Trait;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.regelelementService.RegelelementService;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;
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
        //TODO: Monster löschen. Zuerst abilities usw.?
        return false;
    }

    @PostMapping("/monster") @Transactional
    public ResponseEntity<Monster> addMonster(@Valid @RequestBody MonsterDTO monsterdto, BindingResult result) {
        Monster monster = dtoZuMonster(monsterdto);
        if(!result.hasErrors()) {
           
            logger.info("NEUES MONSTER ERHALTEN: ");
            Set<Sprache> sprachen = new HashSet<>();
            Sprache elementSprache = new Sprache();

            //Neue Sprachen einspeichern
            for(String sprache : monsterdto.getSprachen()) {
                Optional<Sprache> optional = regelelementService.findeElementMitNamen(sprache, elementSprache);
                Sprache hinzufügen = optional.isEmpty() ? new Sprache() : optional.get();
                hinzufügen.setName(sprache);
                hinzufügen = regelelementService.bearbeiteElement(hinzufügen);
                sprachen.add(hinzufügen);
            }

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

    @GetMapping("/monster/validiere")
    public ResponseEntity<ValidResponse> validateMonster() {
        return ResponseEntity.ok().body(new ValidResponse());
    }

    @GetMapping("/init")
    public InitResponse getInitialValues() {
        logger.info("INIT");
        InitResponse init = new InitResponse();
        init.setSprachen(regelelementService.findeAlleNamenVonElement(new Sprache()).toArray(new String[0]));
        init.setAlleTraits(monsterService.findeAlleTraits().toArray(new Trait[0]));
        init.setSchadensarten(regelelementService.findeAlleNamenVonElement(new Schadensart()).toArray(new String[0]));
        return init;
    }

    @GetMapping("/gruppe/{id}")
    public Gruppe getGruppeMitId(@PathVariable Long id) {
        return charakterService.findeGruppeMitId(id);
    }

    @GetMapping("/charakter/{id}")
    public Charakter getCharakterMitId(@PathVariable Long id) {
        return charakterService.findeCharakterMitId(id);
    }

    @PostMapping("/gruppe/bearbeiten")
    public ResponseEntity<Gruppe> bearbeiteGruppe(@RequestBody Gruppe bearbeiteteGruppe) {
        //TODO: Validierung
        Gruppe gespeichert = charakterService.bearbeiteGruppe(bearbeiteteGruppe);
        return ResponseEntity.ok().body(gespeichert);
    }

    @PostMapping("/charakter/bearbeiten")
    public ResponseEntity<Charakter> bearbeiteCharakter(@RequestBody Charakter charakter) {
        //TODO: Validierung
        Charakter gespeichert = charakterService.bearbeiteCharakter(charakter);
        return ResponseEntity.ok().body(gespeichert);
    }

    private Monster dtoZuMonster(MonsterDTO monsterdto) {
        Monster monster = new Monster();
        monster.setLebenspunkte(monsterdto.getLebenspunkte());
        monster.setName(monsterdto.getName());
        monster.setGeschwindigkeit_ft(monsterdto.getGeschwindigkeit_ft());
        monster.setRuestungsklasse(monsterdto.getRuestungsklasse());
        monster.setAbilityScore(Set.of(monsterdto.getAbilityScores()));
        
        return monster;
    }
    
}
