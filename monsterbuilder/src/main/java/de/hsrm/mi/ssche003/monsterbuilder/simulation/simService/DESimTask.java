package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.python.util.PythonInterpreter;
import org.python.core.PyBoolean;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyObjectDerived;
import org.python.core.PySet;
import org.python.core.PySingleton;
import org.python.core.PyString;
import org.python.core.PyObject.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.SimValue;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.AkteurEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.AktionEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.EncounterEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.IEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.InitiativeEreignis;
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

        //TODO: WIR ERSTELLEN LEEERE MONSTER MIT VERHALTEN IN JYTHON; HOLEN SIE IN JAVA; BENUTZEN METHODEN MIT GETATTR
        //TODO: Konstruktor möglichst klein machen, damit erstellung in parallelem Thread gemacht wird und den server beim antworten nicht aufhält
        this.simID = id;
        this.value = value;
        this.interpreter = new PythonInterpreter();
        this.ereignisse = new ArrayDeque<IEreignis>();
        this.state = new SimState().initSimState(gruppe.getAllCharaktere(), monster);
    }

    public synchronized void initTask() {

        
        this.ereignisse.add(new InitiativeEreignis()); //Startereignis
        for(Alignment alignment : Alignment.values()) {
            //alle charaktere und monster mit diesem alignment
            List<Monster> monsterMitAlignment = state.monster.stream().filter((a) -> a.getAlignment() == alignment).toList();
            List<Charakter> charaktereMitAlignment = state.charaktere.stream().filter((a) -> a.getAlignment() == alignment).toList();
            if(monsterMitAlignment.size() > 0 || charaktereMitAlignment.size() > 0) {
                 //alignments durchgehen und verhalten initialisieren, Akteure dann in liste
                interpreter.execfile(Alignment_Pfad.get(alignment)); 
                interpreter.set("javaMonster", monsterMitAlignment);
                interpreter.set("javaCharaktere", charaktereMitAlignment);
                interpreter.get("initialisiere").__call__();
                state.getLebende().addAll((List<Monster>)interpreter.get("alleMonster"));
                state.getLebende().addAll((List<Charakter>)interpreter.get("alleCharaktere"));

            }
        }
    }

    @Override
    public SimResult call() {
        logger.info("task called");
        initTask();
    
        while(!istEncounterVorbei()) {
            IEreignis aktuell = ereignisse.pop();

            if(aktuell instanceof AkteurEreignis) {
                Optional<List<IEreignis>> folgeEreignisse = bearbeiteAkteurEreignis((AkteurEreignis)aktuell);
                if(folgeEreignisse.isPresent() && folgeEreignisse.get().size() > 0) 
                    folgeEreignisse.get().forEach((e) -> addEreignisZuWarteschlange(e));
                
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
        
        interpreter.execfile(Alignment_Pfad.get(aktuell.getAkteurVerhalten().getAlignment()));/*
        PyObject annahme = interpreter.get("wirdEreignisAngenommen");

        if(((PyBoolean) annahme.__call__(new PyInteger(aktuell.getAkteurID().intValue()))).getBooleanValue()) {
            interpreter.set("aktuellesEreignis", aktuell);
            //ereignis kennt die passende Methode im Skript
                PyObject value =  interpreter.get(aktuell.getFuncName()).__call__();
                 return Optional.of(aktuell.generiereFolEreignis(value));

            }*/
            interpreter.set("aktuellesEreignis", aktuell); //das nicht -> im Methodenaufruf params übergben -> wie einheitlich? State rein?
            interpreter.set("state", state);
            //ereignis kennt die passende Methode im Skript
            interpreter.set("akteur", aktuell.getAkteurVerhalten());
            PyObject value =  interpreter.get(aktuell.getFuncName()).__call__();
            return Optional.of(aktuell.generiereFolEreignis(value));

    }

    private SimResult beendeEncounter() {
        logger.info(state.toString());
        interpreter.close();
        return new SimResult(state.kampfrunden, value, simID);
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
