package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterapi;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterService.MonsterService;
import de.hsrm.mi.ssche003.monsterbuilder.exception.MonsterServiceException;
import de.hsrm.mi.ssche003.monsterbuilder.model.MonsterDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/monster")
public class MonsterRestApi {

    @Autowired MonsterService monsterService;

    @GetMapping("/alle")
    public List<Monster> holeAlleMonster() {
        return monsterService.findeAlleMonster();
    }

    @GetMapping("/hole/{id}")
    public Monster holeMonster(@PathVariable Long id) {
        try{
            Monster monster = monsterService.findeMonsterMitId(id); 
            return monster;
        } catch(MonsterServiceException mse) {}
        return null;
    }


    @PutMapping("/bearbeite/")
    public ResponseEntity<MonsterDTO> bearbeiteMonster(@Valid @RequestBody MonsterDTO monsterdto, BindingResult result) {
        if(!result.hasErrors()) {
            Monster monster = monsterService.editMonster(monsterdto);
            return ResponseEntity.ok().body(monsterdto);
        }
        return ResponseEntity.badRequest().body(monsterdto);
    }

    @PostMapping("/neu")
    public ResponseEntity<MonsterDTO> addMonster(@Valid @RequestBody MonsterDTO monsterdto, BindingResult result) {
        Monster monster = monsterService.editMonster(monsterdto);
        MonsterDTO dto = new MonsterDTO();
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/validiere")
    public ResponseEntity<ValidResponse> validateMonster() {
        return ResponseEntity.ok().body(new ValidResponse());
    }
    
}
