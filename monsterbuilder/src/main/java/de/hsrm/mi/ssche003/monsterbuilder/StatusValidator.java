package de.hsrm.mi.ssche003.monsterbuilder;

import java.util.ArrayList;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.skill.Schadenszauber;
import de.hsrm.mi.ssche003.monsterbuilder.trait.StatusTrait;

public class StatusValidator implements IValidator {

    @Override
    /***
     * Validiert ein Monster auf korrekte Zauber.
     * Sobald ein Zauber existiert, gegen dessen Schadenstyp das Monster durch ein Trait schwach ist, wird false zurückgegeben.
     */
    public boolean validiere(Monster monster) {
        ArrayList<Schadensart> schwächen = new ArrayList<>();
        monster.getAlleTraits().forEach(trait -> {
            if(trait instanceof StatusTrait) {
                StatusTrait st = (StatusTrait) trait;
                if(st.bestimmtSchwäche())
                    schwächen.add(st.getTyp());
            }
        });


        for(Zauber zauber : monster.getZauber()) {
            if(zauber instanceof Schadenszauber) {
                Schadenszauber sz = (Schadenszauber) zauber;
                if(schwächen.contains(sz.getTyp()))
                    return false;
            }
        }
      return true;
    }
    
}
