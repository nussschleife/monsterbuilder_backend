package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.SimValue;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.Ereignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.InitiativeEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Level;

public class DESimTask implements SimTask {
    SimState state;
    Deque<Ereignis> ereignisse;
    String simID;
    String message;
    Logger logger = LoggerFactory.getLogger(DESimTask.class);
    SimValue value;
    PythonInterpreter interpreter;

    public DESimTask(Gruppe gruppe, Set<Monster> monster, String id, SimValue value) {
        this.simID = id;
        ereignisse = new ArrayDeque<Ereignis>();
        state = new SimState(gruppe, monster);
        ereignisse.add(new InitiativeEreignis());
        this.value = value;
    }

    @Override
    public SimResult call() { //TODO: python interpreter
        logger.info("task called");
        while(!istEncounterVorbei()) {
            Optional<Ereignis[]> folgeEreignisse = ereignisse.pop().auslösen(state);
            if(folgeEreignisse.isPresent()) {
                for(Ereignis e : folgeEreignisse.get()) {
                    if(e.addToHead())
                        ereignisse.addFirst(e);
                    else
                        ereignisse.add(e);
                }
            }
        }
        
        beendeEncounter();
        message = "runden: "+ state.kampfrunden + "value: "+String.valueOf(((Level) value).intValue());
        return new SimResult(simID, message);
    }

    private void beendeEncounter() {

    }

    private void encounter() {

        //arbeite alle ereignisse ab
        //Aktionereignis ruft skript auf, führt aktion aus und gibt sowas wie saving throw und angriff als ereignis zurück, kommt in die Liste und  wird auch ausgeführt
    }

    private boolean keineErgeinisseÜbrig() {
        return ereignisse.isEmpty();
    }

    private boolean istEncounterVorbei() { //TODO: letzte bedingung raus?
        return keineErgeinisseÜbrig() || state.istGruppeBesiegt() || state.istMonsterBesiegt();
    }

    @Override
    public String getSimID() {
        return this.simID;
    }

}
