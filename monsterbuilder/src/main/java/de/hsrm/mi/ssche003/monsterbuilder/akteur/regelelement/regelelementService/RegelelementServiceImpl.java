package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.regelelementService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Sprache;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.SpracheRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.RegelelementRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import jakarta.transaction.Transactional;

@Transactional @Service
public class RegelelementServiceImpl implements RegelelementService{

    @Autowired RegelelementRepository<AbilityScore> abilityScoreRepo; 
    @Autowired RegelelementRepository<Sprache> spracheRepo;
    @Autowired SpracheRepository spracheRepo_extended;
    static final Logger logger = org.slf4j.LoggerFactory.getLogger(RegelelementServiceImpl.class);

    @SuppressWarnings("unused")
    private <T extends Regelelement> RegelelementRepository<T> getRepository(T element) {

       if(element instanceof AbilityScore)
            return (RegelelementRepository<T>) abilityScoreRepo;
        if(element instanceof Sprache)
            return (RegelelementRepository<T>) spracheRepo;
        return null;
    }

    @Override
    public <T extends Regelelement> List<T> findeAlleVonTyp(T typ) {
        RegelelementRepository<T> repo = getRepository(typ);
        return repo == null ? new ArrayList<T>() : repo.findAll();
    }

    public List<String> findeSprachenNurNamen() {
        return spracheRepo_extended.findeAlleSprachenNamen();
    }
}
