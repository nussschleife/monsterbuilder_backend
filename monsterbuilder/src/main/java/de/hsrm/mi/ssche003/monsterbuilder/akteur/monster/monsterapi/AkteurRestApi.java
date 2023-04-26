package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterapi;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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


    @PutMapping("/monster/bearbeite")
    public ResponseEntity<MonsterDTO> bearbeiteMonster(@Valid @RequestBody MonsterDTO monsterdto, BindingResult result) {
        if(!result.hasErrors()) {
            Monster monster = dtoZuMonster(monsterdto);
            monster = monsterService.editMonster(monster);
            //fehler!! unbearbeitetes dto wird zurüclkgegeben? sollte man nicht eher fielderrors geben
            return ResponseEntity.ok().body(monsterdto);
        }
        return ResponseEntity.badRequest().body(monsterdto);
    }

    @PostMapping("/monster/neu")
    public ResponseEntity<Monster> addMonster(@Valid @RequestBody MonsterDTO monsterdto, BindingResult result) {
        //TODO: auf bindingresult errors prüfen
        logger.info("NEUES MONSTER ERHALTEN: "+monsterdto);
        Monster monster = dtoZuMonster(monsterdto);
        monster = monsterService.editMonster(monster);
        // MonsterDTO dto = new MonsterDTO().setId(monster.getId()).setGeschwindigkeit_ft(monster.getGeschwindigkeit_ft())
        //setLebenspunkte(monster.getLebenspunkte()).setName(monster.getName()).setRuestungsklasse(monster.getRuestungsklasse());
        
        logger.info(String.valueOf(monsterService.findeAlleMonster().size()));
        return ResponseEntity.ok().body(monster);

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
