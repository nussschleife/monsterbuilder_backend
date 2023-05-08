package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScoreName;

public interface AggressiveAktion{
    
    public Akteur ausfuehren(Akteur gegner, int modifikator);

    public AbilityScoreName getAbilityScoreName();
}
