package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.SimValue;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.AkteurEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.EncounterEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.IEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.InitiativeEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.SchadenEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.StateChange;

public class DESimTask implements SimTask {
    SimState state;
    Deque<IEreignis> ereignisse;
    String simID;
    String message;
    Logger logger = LoggerFactory.getLogger(DESimTask.class);
    SimValue value;
    PythonInterpreter interpreter;
    final String PFAD = "src/main/resources/skripts/";
    final Map<Alignment, String> Alignment_Pfad = Map.of(Alignment.CHAOTIC_EVIL, PFAD +"Chaotic_Evil_Verhalten.py", Alignment.NEUTRAL, PFAD + "Neutral_Verhalten.py");

    public DESimTask(Gruppe gruppe, Set<Monster> monster, String id, SimValue value) {

        this.simID = id;
        this.value = value;
        this.interpreter = new PythonInterpreter();
        this.ereignisse = new ArrayDeque<IEreignis>();
        this.state = new SimState().initSimState(gruppe.getAllCharaktere(), monster);
    }

    public synchronized void initTask() {

        this.ereignisse.add(new InitiativeEreignis()); //Startereignis
      
        interpreter.execfile(Alignment_Pfad.get(Alignment.CHAOTIC_EVIL)); //aendern in Haupt-skript
        interpreter.set("javaMonster", state.monster);
        interpreter.set("javaCharaktere", state.charaktere);
        interpreter.set("state", this.state);
        interpreter.get("initialisiere").__call__(); //vom Haupt-skript
        logger.info(state.lebendig.toString());
            
    }

    @Override
    public SimResult call() {
        logger.info("task called");
        initTask();
    
        while(!istEncounterVorbei()) {
            IEreignis aktuell = ereignisse.pop();

            if(aktuell instanceof AkteurEreignis) {
                if(state.getLebende().contains(((AkteurEreignis) aktuell).getAkteurName())) {
                    Optional<List<IEreignis>> folgeEreignisse = bearbeiteAkteurEreignis((AkteurEreignis)aktuell);
                    if(folgeEreignisse.isPresent() && folgeEreignisse.get().size() > 0) 
                        folgeEreignisse.get().forEach((e) -> addEreignisZuWarteschlange(e));
                }
                
            } else {
                ereignisse.addAll(((EncounterEreignis)aktuell).auslösen(state));
            }
            verändereState(aktuell.getChange());
        }
        
        return beendeEncounter();
    }

    private void verändereState(Optional<StateChange> change) {
        if(change.isPresent())
            change.get().changeState(state);
    }

    private void addEreignisZuWarteschlange(IEreignis ereignis) {
        if(ereignis.addToHead())
            ereignisse.addFirst(ereignis);
        else
            ereignisse.add(ereignis);
    }

    private Optional<List<IEreignis>> bearbeiteAkteurEreignis(AkteurEreignis aktuell) {
        if(aktuell instanceof SchadenEreignis)
            logger.info("davor: "+ interpreter.get("log").__call__().asString());
        /*interpreter.execfile(Alignment_Pfad.get(aktuell.getAkteurVerhalten().getAlignment()));*/
            interpreter.set("aktuellesEreignis", aktuell); //das nicht -> im Methodenaufruf params übergben -> wie einheitlich? State rein?
            interpreter.set("state", state);
            interpreter.set("akteur", aktuell.getAkteurName());
            interpreter.get("handleEreignis").__call__();
          
        if(aktuell instanceof SchadenEreignis)
            logger.info("danach: "+ interpreter.get("log").__call__().asString());
  return Optional.of(aktuell.generiereFolEreignis());

    }

    private SimResult beendeEncounter() {
        logger.info(state.toString());
        interpreter.close();
        return new SimResult(state.kampfrunden, value, simID);
    }

    private boolean keineErgeinisseÜbrig() {
        return ereignisse.isEmpty();
    }

    private boolean istEncounterVorbei() {
        return keineErgeinisseÜbrig() || state.istGruppeBesiegt() || state.istMonsterBesiegt();
    }

    @Override
    public String getSimID() {
        return this.simID;
    }

}
