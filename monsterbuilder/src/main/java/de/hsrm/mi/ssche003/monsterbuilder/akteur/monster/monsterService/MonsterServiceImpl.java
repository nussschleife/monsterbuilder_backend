package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterService;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.exception.MonsterServiceException;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.MonsterRepo;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.Trait;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.TraitRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.Angriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.regelelementService.RegelelementService;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

@Service
public class MonsterServiceImpl implements MonsterService{

    @Autowired MonsterRepo repo;
    @Autowired TraitRepository traitRepo;
    @Autowired RegelelementService regelService;

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
    public Monster editMonster(Monster monster) throws MonsterServiceException {
        Monster persistiertesMonster;
        persistiertesMonster = findeMonsterMitId(monster.getId());
        if(persistiertesMonster == null) {
            persistiertesMonster = new Monster();
        }
            //bidirektionale Verbindungen erstellen
            
            //Angriffe 
            for(Angriff angriff : monster.getAlleAngriffe()) {
                Optional<Angriff> optAngriff = regelService.findeElementMitId(angriff.getId(), angriff);
                if( optAngriff.isEmpty() )
                    throw new MonsterServiceException("Angriff wurde nicht gefunden.");
                optAngriff.get().addMonster(persistiertesMonster);
                persistiertesMonster.addAngriff(angriff);
            }
            //Traits
            for(Trait trait : monster.getAlleTraits()) {
                Optional<Trait> optTrait = traitRepo.findById(trait.getId());
                if( optTrait.isEmpty() )
                    throw new MonsterServiceException("Trait wurde nicht gefunden.");
                optTrait.get().addMonster(persistiertesMonster);
                persistiertesMonster.addTrait(trait);
            }
            //Sprachen

            for(Sprache sprache : monster.getSprachen()) {
                Optional<Sprache> optSprache = regelService.findeElementMitId(sprache.getId(), sprache);
                if( optSprache.isEmpty() )
                    throw new MonsterServiceException("Sprache wurde nicht gefunden.");
                optSprache.get().addMonster(persistiertesMonster);
                persistiertesMonster.addSprache(sprache);
            }
            //AbilityScores

            for(AbilityScore abilityScore : monster.getAbilityScores()) {
                Optional<AbilityScore> optAbilityScore = regelService.findeElementMitId(abilityScore.getId(), abilityScore);
                if( optAbilityScore.isEmpty() )
                    throw new MonsterServiceException("AbilityScore wurde nicht gefunden.");
                optAbilityScore.get().addMonster(persistiertesMonster);
                persistiertesMonster.addAbilityScore(abilityScore);
            }

            persistiertesMonster.setLebenspunkte(monster.getLebenspunkte());
            persistiertesMonster.setName(monster.getName());
            persistiertesMonster.setGeschwindigkeit_ft(monster.getGeschwindigkeit_ft());
            persistiertesMonster.setRuestungsklasse(monster.getRuestungsklasse());
            persistiertesMonster.setAbilityScores(monster.getAbilityScores());
            persistiertesMonster.setAlleTraits(monster.getAlleTraits());
            persistiertesMonster.setAlleAngriffe(monster.getAlleAngriffe());
        
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

    public List<Trait> findeAlleTraits() {
        return traitRepo.findAll();
    }

    public Optional<Trait> findeTraitMitNamen(String name) {
        return traitRepo.findByName(name);
    }
    
}
