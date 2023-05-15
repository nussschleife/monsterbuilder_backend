package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.abilityScore.AbilityScoreName;

public interface AkteurAktion{
    
    public Akteur ausfuehren(Akteur gegner, int modifikator);

    public AbilityScoreName getAbilityScoreName();
}
