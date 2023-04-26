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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.dto.InitResponse;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.dto.MonsterDTO;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.exception.MonsterServiceException;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterService.MonsterService;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.Trait;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.regelelementService.RegelelementService;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/akteur")
public class AkteurRestApi {

    @Autowired MonsterService monsterService;
    @Autowired RegelelementService regelelementService;
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


    @PutMapping("/monster/bearbeite")
    public ResponseEntity<MonsterDTO> bearbeiteMonster(@Valid @RequestBody MonsterDTO monsterdto, BindingResult result) {
        if(!result.hasErrors()) {
            Monster monster = dtoZuMonster(monsterdto);
            monster = monsterService.editMonster(monster);
            //fehler!! unbearbeitetes dto wird zurüclkgegeben? sollte man nicht eher fielderrors geben
            //TODO: abilityScores und Traits ändern
            return ResponseEntity.ok().body(monsterdto);
        }
        return ResponseEntity.badRequest().body(monsterdto);
    }

    @PostMapping("/monster/neu")
    public ResponseEntity<Monster> addMonster(@Valid @RequestBody MonsterDTO monsterdto, BindingResult result) {
        Monster monster = dtoZuMonster(monsterdto);
        if(!result.hasErrors()) {
            logger.info("NEUES MONSTER ERHALTEN: "+monsterdto);
            Set<Sprache> sprachen = new HashSet<>();
            Sprache elementSprache = new Sprache();

            //Neue Sprachen einspeichern und existierende aus repo holen
            for(String sprache : monsterdto.getSprachen()) {
                Optional<Sprache> optional = regelelementService.findeElementMitNamen(sprache, elementSprache);
                Sprache hinzufügen = optional.isEmpty() ? new Sprache() : optional.get();
                hinzufügen.setName(sprache);
                sprachen.add(hinzufügen);
            }

            //neue Traits speichern und existierende aus repo holen
            Set<Trait> traits = new HashSet<>();
            for(Trait trait : monsterdto.getTraits()) {
                Optional<Trait> optional = monsterService.findeTraitMitNamen(trait.getName());
                Trait hinzufügen = optional.isEmpty() ? new Trait() : optional.get();
                hinzufügen.setName(trait.getName());
                traits.add(hinzufügen);
            }

            //neue AbilityScores und existierende aus repo holen
            Set<AbilityScore> scores = new HashSet<>();
            AbilityScore elementScore = new AbilityScore();
            for(AbilityScore as : monsterdto.getAbilityScores()) {
                Optional<AbilityScore> optional = regelelementService.findeElementMitId(as.getId(), elementScore);
                AbilityScore hinzufügen = optional.isEmpty() ? new AbilityScore() : optional.get();
                hinzufügen.setName(as.getName());
                hinzufügen.setScore(as.getScore());
                scores.add(hinzufügen);
            }

            monster.setSprachen(sprachen);
            monster.setAlleTraits(traits);
            monster.setAbilityScores(scores);
            //TODO: wann wird monster bei sprachen, traits, ability scores gesetzt???

            monster = monsterService.editMonster(monster);
            // Sprachen String erstmal aufdröseln und sprachen suchen und zuweisen, selbes für traits und ability Scores.
            
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
        return init;
    }

    private Monster dtoZuMonster(MonsterDTO monsterdto) {
        Monster monster = monsterService.findeMonsterMitId(monsterdto.getId());
        monster = monster == null ? new Monster() : monster;
        monster.setLebenspunkte(monsterdto.getLebenspunkte());
        monster.setName(monsterdto.getName());
        monster.setGeschwindigkeit_ft(monsterdto.getGeschwindigkeit_ft());
        monster.setRuestungsklasse(monsterdto.getRuestungsklasse());
        monster.setAbilityScore(Set.of(monsterdto.getAbilityScores()));
        
        return monster;
    }
    
}
