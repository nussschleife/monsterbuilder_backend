package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;

public interface AggressiveAktion{
    
    public Akteur ausfuehren(Akteur gegner, int modifikator);
}
