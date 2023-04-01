package de.hsrm.mi.ssche003.monsterbuilder.validation;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.elementverträglichkeit.Schwäche;

public class WeaknessValidation implements Validation<Schwäche> {

    private Schwäche schwäche;

    WeaknessValidation(Schwäche schwäche) {
        this.schwäche = schwäche;
    }

    @Override
    public boolean validiere(Schwäche element) {
      return element == this.schwäche;
    }

    
}
