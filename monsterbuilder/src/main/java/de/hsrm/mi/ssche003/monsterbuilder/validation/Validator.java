package de.hsrm.mi.ssche003.monsterbuilder.validation;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;

public class Validator {
    private List<Validation> validatoren = new ArrayList<>();

    public Validator(List<Validation> validatoren) {
        this.validatoren = validatoren;
    }

    public boolean validiere(Regelelement element) { //TODO: problem wenn man zB spell validieren will aber ein validator nimmt nur skills
        return validatoren.iterator().next().validiere(element);
    }
}
