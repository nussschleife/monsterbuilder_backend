package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.regelelementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.RegelelementRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.WaffenAngriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.effekt.Condition;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.effekt.ConditionRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

@Transactional @Service
public class RegelelementServiceImpl implements RegelelementService{

    @Autowired RegelelementRepository<AbilityScore> abilityScoreRepo; 
    @Autowired RegelelementRepository<Sprache> spracheRepo;
    @Autowired RegelelementRepository<Schadensart> schadensartRepo;
    @Autowired RegelelementRepository<WaffenAngriff> angriffRepo;
    @Autowired RegelelementRepository<Condition> conditionRepository;

    static final Logger logger = org.slf4j.LoggerFactory.getLogger(RegelelementServiceImpl.class);

    private <T extends Regelelement> RegelelementRepository<T> getRepository(T element) {

       if(element instanceof AbilityScore)
            return (RegelelementRepository<T>) abilityScoreRepo;
        if(element instanceof Sprache)
            return (RegelelementRepository<T>) spracheRepo;
        if(element instanceof Schadensart)
            return (RegelelementRepository<T>) schadensartRepo;
        if(element instanceof WaffenAngriff)
            return (RegelelementRepository<T>) angriffRepo;
        if(element instanceof Condition)
            return (RegelelementRepository<T>) conditionRepository;
        return null;
    }

    @Override
    public <T extends Regelelement> List<T> findeAlleVonTyp(T typ) {
        RegelelementRepository<T> repo = getRepository(typ);
        return repo == null ? new ArrayList<T>() : repo.findAll();
    }

    @Override
    public <T extends Regelelement> List<String> findeAlleNamenVonElement(T element) {
        RegelelementRepository<T> repo = getRepository(element);
        return repo == null ? new ArrayList<String>() : repo.findeAlleNamen();
    }

    @Override
    public <T extends Regelelement> Optional<T> findeElementMitNamen(String name, T element) {
        RegelelementRepository<T> repo = getRepository(element);
        return repo == null ? null : repo.findByName(name);
    }

    @Override @Transactional
    public <T extends Regelelement> T bearbeiteElement(T element) { 
        RegelelementRepository<T> repo = getRepository(element);
        Optional<T> persistiertOpt = findeElementMitId(element);
        T persistiert = persistiertOpt.isEmpty() ? (T)element.getInstance() : persistiertOpt.get();
        persistiert.übernehmeBasisWerteVon(element);      
        persistiert.setName(element.getName());  
        try {
           return repo.save(persistiert);
        } catch (OptimisticLockException ole) {
            logger.error("ELEMENT MIT NAMEN: "+element.getName()+ "KONNTE NICHT GESPEICHERT WERDEN");
        }
        return null; //ODER THROW REGELELEMENTSERVICEEX
    }

    @Override
    public <T extends Regelelement> Optional<T> findeElementMitId(T element) {
        RegelelementRepository<T> repo = getRepository(element);
        return repo == null || element.getId() == null ? Optional.empty() : repo.findById(element.getId());
    }

}
