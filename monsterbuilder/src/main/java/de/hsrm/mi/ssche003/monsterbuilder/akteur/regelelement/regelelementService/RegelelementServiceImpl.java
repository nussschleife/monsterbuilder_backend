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
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

@Transactional @Service
public class RegelelementServiceImpl implements RegelelementService{

    @Autowired RegelelementRepository<AbilityScore> abilityScoreRepo; 
    @Autowired RegelelementRepository<Sprache> spracheRepo;
    @Autowired RegelelementRepository<Schadensart> schadensartRepo;
    static final Logger logger = org.slf4j.LoggerFactory.getLogger(RegelelementServiceImpl.class);

    @SuppressWarnings("unused")
    private <T extends Regelelement> RegelelementRepository<T> getRepository(T element) {

       if(element instanceof AbilityScore)
            return (RegelelementRepository<T>) abilityScoreRepo;
        if(element instanceof Sprache)
            return (RegelelementRepository<T>) spracheRepo;
        if(element instanceof Schadensart)
        return (RegelelementRepository<T>) schadensartRepo;
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

    @Override
    public <T extends Regelelement> T bearbeiteElement(T element) {
        RegelelementRepository<T> repo = getRepository(element);
        try {
           return repo.save(element);
        } catch (OptimisticLockException ole) {
            logger.error("ELEMENT MIT NAMEN: "+element.getName()+ "KONNTE NICHT GESPEICHERT WERDEN");
        }
        return null;
    }

    @Override
    public <T extends Regelelement> Optional<T> findeElementMitId(Long id, T element) {
        RegelelementRepository<T> repo = getRepository(element);
        return repo == null ? null : repo.findById(id);
    }

}
