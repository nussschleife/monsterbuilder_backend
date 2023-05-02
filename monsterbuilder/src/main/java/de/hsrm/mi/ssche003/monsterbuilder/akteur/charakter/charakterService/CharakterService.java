package de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.charakterService;

import org.springframework.stereotype.Service;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;

public interface CharakterService { 
    Charakter findeCharakterMitId (Long id);
    Charakter bearbeiteCharakter(Charakter charakter);
    Gruppe findeGruppeMitId(Long id);
    Gruppe bearbeiteGruppe(Gruppe gruppe);
   

}
