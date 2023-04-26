package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.regelelementService;

import java.util.List;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;

public interface RegelelementService {
     public <T extends Regelelement> List<T> findeAlleVonTyp(T typ);

     public List<String> findeSprachenNurNamen();
}
