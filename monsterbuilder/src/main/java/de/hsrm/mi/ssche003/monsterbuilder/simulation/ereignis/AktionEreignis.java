package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public class AktionEreignis implements Ereignis{

    private boolean nachAusweichen;
    private Long akteurID;

    public AktionEreignis(boolean nachAusweichen, Long akteurID) {
        this.nachAusweichen = nachAusweichen;
        this.akteurID = akteurID;
    }

    @Override
    public Optional<Ereignis[]> auslösen(SimState state) {

        //per skript aktion herausfinden
        // Wo wird der schaden gemacht? Schadenereignis?
        //wenn es zB. saving throw erfordert, neues ereignis erstellen und dann als nächstes ereignis den angriff
        Akteur randomOpfer = (Akteur) state.getLebende().toArray()[(int)(Math.random()*3)];
        //neues Aktionsereignis für die runde danach 
        Ereignis[] ereignis = {new AktionEreignis(false, akteurID), new SchadenEreignis(randomOpfer, (int)(Math.random()*7)+1)};
        return Optional.of(ereignis);
    }

    @Override
    public boolean addToHead() {
        return nachAusweichen;
    }
    
}
