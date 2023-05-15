package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.charakterService;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.gruppe.Gruppe;

public interface CharakterService { 
    Charakter findeCharakterMitId (Long id);
    Charakter bearbeiteCharakter(Charakter charakter);
    Gruppe findeGruppeMitId(Long id);
    Gruppe bearbeiteGruppe(Gruppe gruppe);
    Charakter holeStandardCharakter(int level, String klasse);
}
