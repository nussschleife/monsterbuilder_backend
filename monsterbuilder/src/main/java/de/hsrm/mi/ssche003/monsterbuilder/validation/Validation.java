package de.hsrm.mi.ssche003.monsterbuilder.validation;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;

public interface Validation<T extends Regelelement> {
   boolean validiere(T element);
}
