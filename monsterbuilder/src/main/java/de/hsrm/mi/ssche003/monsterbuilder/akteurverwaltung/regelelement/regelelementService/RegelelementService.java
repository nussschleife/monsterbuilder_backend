package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.regelelementService;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.Regelelement;

public interface RegelelementService {
     public <T extends Regelelement> List<T> findeAlleVonTyp(T typ);

     public <T extends Regelelement> List<String> findeAlleNamenVonElement(T element);

     public<T extends Regelelement> Optional<T> findeElementMitNamen(String name, T element);

     public<T extends Regelelement> Optional<T> findeElementMitId(T element);

     public<T extends Regelelement> T bearbeiteElement(T element);

}