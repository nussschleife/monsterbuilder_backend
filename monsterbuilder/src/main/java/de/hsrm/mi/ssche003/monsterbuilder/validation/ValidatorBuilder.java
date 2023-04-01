package de.hsrm.mi.ssche003.monsterbuilder.validation;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.elementverträglichkeit.Schwäche;

public class ValidatorBuilder {
    private List<Validation> validatoren = new ArrayList<>();

    public void zurücksetzen() {
        this.validatoren.clear();
    }

    public ValidatorBuilder plusSchwächeValidator(Schwäche schwäche) {
        validatoren.add(new WeaknessValidation(schwäche));
        return this;
    }

    public Validator baueValidator() {
        return new Validator(validatoren);
    }
}
