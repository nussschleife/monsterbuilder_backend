package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import java.util.Set;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.exception.MonsterServiceException;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.MonsterRepo;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.Trait;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.TraitRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.WaffenAngriff;
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
            Set<WaffenAngriff> angriffe = new HashSet<>();
            for(WaffenAngriff angriff : monster.getAlleAngriffe()) {
                WaffenAngriff optAngriff = regelService.bearbeiteElement(angriff);
                optAngriff.addMonster(persistiertesMonster);
                angriffe.add(optAngriff);
            }
            //Traits
            Set<Trait> traits = new HashSet<>();
            for(Trait trait : monster.getAlleTraits()) {
                Optional<Trait> optTrait = traitRepo.findById(trait.getId());
                if( optTrait.isEmpty() )
                    throw new MonsterServiceException("Trait wurde nicht gefunden.");
                optTrait.get().addMonster(persistiertesMonster);
                traits.add(optTrait.get());
            }
            //Sprachen
            Set<Sprache> sprachen = new HashSet<>();
            for(Sprache sprache : monster.getSprachen()) {
                Sprache optSprache = regelService.bearbeiteElement(sprache);
                optSprache.addAkteur(persistiertesMonster);
                sprachen.add(optSprache);
            }
            //AbilityScores
            Set<AbilityScore> scores = new HashSet<>();

            for(AbilityScore abilityScore : monster.getAbilityScores()) {
                AbilityScore optAbilityScore = regelService.bearbeiteElement(abilityScore);
                optAbilityScore.addAkteur(persistiertesMonster);
                scores.add(optAbilityScore);
            }

            persistiertesMonster.setLebenspunkte(monster.getLebenspunkte());
            persistiertesMonster.setName(monster.getName());
            persistiertesMonster.setGeschwindigkeit_ft(monster.getGeschwindigkeit_ft());
            persistiertesMonster.setRuestungsklasse(monster.getRuestungsklasse());
            persistiertesMonster.setAbilityScores(scores);
            persistiertesMonster.setAlleTraits(traits);
            persistiertesMonster.setAlleAngriffe(angriffe);
            persistiertesMonster.setSprachen(sprachen);
            persistiertesMonster.setAlignment(monster.getAlignment());
            persistiertesMonster.setLevel(monster.getLevel());
        
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
