package de.hsrm.mi.ssche003.monsterbuilder.akteur;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.RegelelementRepository;

public interface SpracheRepository extends RegelelementRepository<Sprache>{
    @Query("SELECT DISTINCT s.sprache FROM Sprache s")
    List<String> findeAlleSprachenNamen();
}
