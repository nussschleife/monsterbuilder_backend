package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScoreName;

public interface AkteurAktion{
    
    public Akteur ausfuehren(Akteur gegner, int modifikator);

    public AbilityScoreName getAbilityScoreName();
}
