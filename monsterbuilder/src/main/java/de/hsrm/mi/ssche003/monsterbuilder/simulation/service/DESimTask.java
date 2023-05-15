package de.hsrm.mi.ssche003.monsterbuilder.simulation.service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue.SimValue;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter.Encounter;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.service.ereignis.AkteurEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.service.ereignis.EncounterEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.service.ereignis.IEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.service.ereignis.InitiativeEreignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.service.ereignis.StateChange;

public class DESimTask implements SimTask {
    private SimState state;
    private Deque<IEreignis> ereignisse;
    private String simID;
    private Logger logger = LoggerFactory.getLogger(DESimTask.class);
    private SimValue value;
    private PythonInterpreter interpreter;
    private int anzahlDurchläufe;
    private final String PFAD = "src/main/resources/skripts/";
    private boolean istFehlerAufgetreten;
    private HashMap<Alignment, String> alignment_Pfad = new HashMap<>(Map.of(Alignment.CHAOTIC_EVIL, PFAD +"Chaotic_Evil_Verhalten.py"));
    private final String MAINSKRIPT = PFAD + "HauptSkript.py";

    public DESimTask(Gruppe gruppe, Monster monster, String id, SimValue value, int anzahlDurchläufe, String customSkriptName) {
        this.simID = id;
        this.value = value;
        this.interpreter = new PythonInterpreter();
        this.ereignisse = new ArrayDeque<IEreignis>();
        this.anzahlDurchläufe = anzahlDurchläufe;
        this.state = new SimState().initSimState(gruppe.getAllCharaktere(), monster);
        this.alignment_Pfad.put(Alignment.CUSTOM, customSkriptName);
    }

    public void initTask() {
        boolean istFehlerAufgetreten = false;
        InitiativeEreignis ini = new InitiativeEreignis(state.getMonster(), state.getCharaktere());
        this.ereignisse.add(ini); //Startereignis

        //initialisiere Python Umgebung
        interpreter.set("state", this.state);
        interpreter.set("aktuellesEreignis", ini);
        interpreter.set("encounter", new Encounter());
        interpreter.execfile(MAINSKRIPT); 

        //Setze Monster
        interpreter.set("toInit", state.getMonster());
        interpreter.get("initialisiere").__call__();
        

        //Setze  Charakter
        for(Charakter chara : state.getCharaktere()) {
            interpreter.set("toInit", chara);
            interpreter.get("initialisiere").__call__();
        }
    }

    private String getSkriptPath(Alignment alignment) {
        if(!alignment_Pfad.containsKey(alignment)) 
            return alignment_Pfad.get(Alignment.CHAOTIC_EVIL);
        return alignment_Pfad.get(alignment);
    }

    @Override
    public SimResult call() {
        logger.info("task called");
        for(int i = 0; i < anzahlDurchläufe; i++) {
            resetEncounter();
            initTask();

            while(!istEncounterVorbei()) {
                IEreignis aktuell = ereignisse.pop();
                List<IEreignis> folgeEreignisse  = null;
                if(aktuell instanceof AkteurEreignis) {
                    if(state.getLebende().stream().anyMatch((lebendiger) -> lebendiger.getName() == ((AkteurEreignis)aktuell).getAkteurName())) {
                        bearbeiteAkteurEreignis((AkteurEreignis)aktuell); 
                        verändereState(aktuell.getChange());
                        folgeEreignisse =  ((AkteurEreignis)aktuell).generiereFolgeEreignis();
                    }
                } else { 
                    verändereState(aktuell.getChange());
                    folgeEreignisse = ((EncounterEreignis)aktuell).generiereFolgeEreignis();
                }
                addEreignisseZuWarteschlange(folgeEreignisse);
            }
        }
        return beendeEncounter();
    }

    private void resetEncounter() {
        interpreter.cleanup();
        state.initSimState(state.getCharaktere(), state.getMonster());
    }

    private void verändereState(Optional<StateChange> change) {
        if(change.isPresent())
            change.get().changeState(state);
    }

    private void addEreignisseZuWarteschlange(List<IEreignis> folge) {
        if(folge != null)  {
            for(IEreignis ereignis : folge) {
                if(ereignis.addToHead())
                    ereignisse.addFirst(ereignis);
                else
                    ereignisse.add(ereignis);
            }
        }
    }

    private void bearbeiteAkteurEreignis(AkteurEreignis aktuell) {
        
        Akteur mon = state.getLebende().stream().filter((akteur) -> akteur.getName().equals(aktuell.getAkteurName())).findFirst().get();
            interpreter.set("aktuellesEreignis", aktuell);
            interpreter.set("state", state);
            interpreter.set("akteur", mon);
            try{
                interpreter.execfile(getSkriptPath(mon.getAlignment())); 
            } catch(PyException py) {
                logger.error(py.toString());
                istFehlerAufgetreten = true;
            }
            
    }

    private SimResult beendeEncounter() {
        interpreter.close();
        SimResult result = new SimResult(state.getRunden(), value, simID);
        result.setNachricht(!istFehlerAufgetreten ? ("Gewinner: " + (state.istGruppeBesiegt() ? "Monster " : "Gruppe ")) : "Fehler beim Ausführen des Skripts.");
        return result;
    }

    private boolean keineErgeinisseÜbrig() {
        return ereignisse.isEmpty();
    }

    private boolean istEncounterVorbei() {
        return istFehlerAufgetreten || ( state.getRunden() >= 1 && ( keineErgeinisseÜbrig() || state.istGruppeBesiegt() || state.istMonsterBesiegt()));
    }

    @Override
    public String getSimID() {
        return this.simID;
    }

}