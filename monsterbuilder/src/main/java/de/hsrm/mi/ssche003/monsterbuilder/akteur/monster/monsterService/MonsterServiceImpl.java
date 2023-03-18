package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.MonsterRepo;
import de.hsrm.mi.ssche003.monsterbuilder.exception.MonsterServiceException;
import de.hsrm.mi.ssche003.monsterbuilder.model.MonsterDTO;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

@Service
public class MonsterServiceImpl implements MonsterService{

    @Autowired MonsterRepo repo;
    static final Logger logger = org.slf4j.LoggerFactory.getLogger(MonsterServiceImpl.class);

    @Override
    @Transactional
    public Monster findeMonsterMitId(Long id){

        if(id != null) {
            Optional<Monster> monster = repo.findById(id);
                return monster.isPresent() ?  monster.get() : null;
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteMonsterMitId(Long id) {
        Monster monster = findeMonsterMitId(id);
        repo.delete(monster);
    }

    @Override
    @Transactional
    public Monster editMonster(MonsterDTO monster) throws MonsterServiceException {
        Monster persistiertesMonster;
        persistiertesMonster = findeMonsterMitId(monster.getId());
        if(persistiertesMonster == null)
            persistiertesMonster = new Monster();

        persistiertesMonster.setLebenspunkte(monster.getLebenspunkte());
        persistiertesMonster.setName(monster.getName());
        persistiertesMonster.setGeschwindigkeit_ft(monster.getGeschwindigkeit_ft());
        persistiertesMonster.setRuestungsklasse(monster.getRuestungsklasse());
        
        try {
            persistiertesMonster = repo.save(persistiertesMonster);
        } catch(OptimisticLockException ole) {
            throw new MonsterServiceException("Monster konnte nicht aktualisiert werden.");
        }
        
        return persistiertesMonster;
    }

    @Override
    public List<Monster> findeAlleMonster() {
        return repo.findAll();
    }
    
}
