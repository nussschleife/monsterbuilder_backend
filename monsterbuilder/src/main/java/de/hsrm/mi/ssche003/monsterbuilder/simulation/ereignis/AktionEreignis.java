package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Optional;

public class AktionEreignis implements Ereignis{

    @Override
    public Optional<Ereignis[]> auslösen() {
        //per skript aktion herausfinden
        // akteur.aktion.auslösen()?
        //wenn es zB. saving throw erfordert, neues ereignis erstellen und dann als nächstes ereignis den angriff
        //Ereignisse sollen eigentlich unabhängig sein. Der Angriff ist aber abhängig vom save -> ein ereignis? Also Effektzauber ereignis?
        return Optional.empty();
    }
    
}
