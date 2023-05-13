package de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.charakterService;

import java.util.Optional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.CharakterRepository;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.GruppeRepository;
import jakarta.transaction.Transactional;

@Service
public class CharakterServiceImpl implements CharakterService{
//TODO: custom exception
    @Autowired CharakterRepository charaRepo;
    @Autowired GruppeRepository gruppeRepo;

    private static final Logger logger = LoggerFactory.getLogger(CharakterServiceImpl.class);

    @Override @Transactional
    public Charakter bearbeiteCharakter(Charakter charakter) { //TODO: das ganze hier macht hibernate doch von allein?
        Charakter persistierterCharakter = findeCharakterMitId(charakter.getId());
        persistierterCharakter.setAlignment(charakter.getAlignment());
        persistierterCharakter.setName(charakter.getName());
        persistierterCharakter.setGeschwindigkeit_ft(charakter.getGeschwindigkeit_ft());
        persistierterCharakter.setLebenspunkte(charakter.getLebenspunkte());
        persistierterCharakter.setRuestungsklasse(charakter.getRuestungsklasse());
        persistierterCharakter.setGruppe(charakter.getGruppe());
        return charaRepo.save(persistierterCharakter);

    }

    @Override
    public Gruppe findeGruppeMitId(Long id) {
        if(id != null) {
            Optional<Gruppe> gruppeOpt = gruppeRepo.findById(id);
            if(gruppeOpt.isPresent())
                return gruppeOpt.get();
        }
        return gruppeRepo.save(new Gruppe());
    }

    @Override
    public Charakter findeCharakterMitId(Long id) {
        if(id != null) {
            Optional<Charakter> charaOpt = charaRepo.findById(id);
            if(charaOpt.isPresent())
                return charaOpt.get();
        }
        return charaRepo.save(new Charakter());
    }

    @Override @Transactional
    public Gruppe bearbeiteGruppe(Gruppe gruppe) {
        Gruppe persistierteGruppe = findeGruppeMitId(gruppe.getId());
        persistierteGruppe.setAlleCharaktere(gruppe.getAlleCharaktere());
        persistierteGruppe.getAllCharaktere().forEach(c -> {  
            c.setGruppe(persistierteGruppe);  
            c = bearbeiteCharakter(c);
        });
        return gruppeRepo.save(persistierteGruppe);
    }

    @Override @Transactional
    public Charakter holeStandardCharakter(int level, String klasse) {
        //TODO: custom exception, naming convention
        String name = "StandardCharakterLevel"+level;
        Optional<Charakter> charakter = charaRepo.findByName(name);
        return initChara(charakter.get());
    }

    @Transactional
    public Charakter initChara(Charakter chara){
       
            if(!Hibernate.isInitialized(chara.getAbilityScores())){
                Hibernate.initialize(chara.getAbilityScores());
            }
            if(!Hibernate.isInitialized(chara.getAlleAngriffe())){
                Hibernate.initialize(chara.getAlleAngriffe());
            }
            if(!Hibernate.isInitialized(chara.getAlleZauber())){
                Hibernate.initialize(chara.getAlleZauber());
            }
            if(!Hibernate.isInitialized(chara.getSprachen())){
                Hibernate.initialize(chara.getSprachen());
            }
            if(!Hibernate.isInitialized(chara.getSavingThrows())){
                Hibernate.initialize(chara.getSavingThrows());
            }
        return chara;
    }

}
