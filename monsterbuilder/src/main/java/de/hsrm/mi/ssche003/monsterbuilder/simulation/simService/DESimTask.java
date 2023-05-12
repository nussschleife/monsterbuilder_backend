package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.python.core.PyBoolean;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue.SimValue;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.AkteurEreignis;
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
    int anzahlDurchläufe;
    final String PFAD = "src/main/resources/skripts/";
    final Map<Alignment, String> alignment_Pfad = Map.of(Alignment.CHAOTIC_EVIL, PFAD +"Chaotic_Evil_Verhalten.py", Alignment.NEUTRAL, PFAD + "Neutral_Verhalten.py");
    final String MAINSKRIPT = PFAD + "HauptSkript.py";

    public DESimTask(Gruppe gruppe, Set<Monster> monster, String id, SimValue value, int anzahlDurchläufe) {
        this.simID = id;
        this.value = value;
        this.interpreter = new PythonInterpreter();
        this.ereignisse = new ArrayDeque<IEreignis>();
        this.anzahlDurchläufe = anzahlDurchläufe;
        this.state = new SimState().initSimState(gruppe.getAllCharaktere(), monster);
    }

    public void initTask() {
        InitiativeEreignis ini = new InitiativeEreignis(state.getMonster(), state.getCharaktere());
        this.ereignisse.add(ini); //Startereignis
        //TODO: erst checken ob custom template ini methode hat und Python errors fangen, default template verwenden
        //initialisiere Python Umgebung
        interpreter.set("state", this.state);
        interpreter.set("aktuellesEreignis", ini);
        interpreter.execfile(MAINSKRIPT); 

        //Setze korrektes behavior-Objekt fuer jedes Monster
        for(Monster monster : state.getMonster()) {
            interpreter.set("toInit", monster);
            interpreter.execfile(alignment_Pfad.get(Alignment.CHAOTIC_EVIL));
        }

        //Setze korrektes behavior-Objekt fuer jeden Charakter
        for(Charakter chara : state.getCharaktere()) {
            interpreter.set("toInit", chara);
            interpreter.execfile(alignment_Pfad.get(Alignment.CHAOTIC_EVIL));
        }
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
                        folgeEreignisse =  ((AkteurEreignis)aktuell).generiereFolEreignis();
                    }
                } else { 
                    verändereState(aktuell.getChange());
                    folgeEreignisse = ((EncounterEreignis)aktuell).auslösen();
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
        /*wenn alle Mosnter initialisiert sind braucht man nichtmal ein haupt-skript dass alle hält, weil sie in der python-umgebung existieren.
         * auf map kann theoretisch so zugegriffen werden
        */
            interpreter.set("aktuellesEreignis", aktuell);
            interpreter.set("state", state);
            interpreter.execfile(MAINSKRIPT); 
           // logger.info((antwort).asString());
    }

    private SimResult beendeEncounter() {
        interpreter.close();
        return new SimResult(state.getRunden(), value, simID);
    }

    private boolean keineErgeinisseÜbrig() {
        return ereignisse.isEmpty();
    }

    private boolean istEncounterVorbei() {
        return state.getRunden() >= 1 && ( keineErgeinisseÜbrig() || state.istGruppeBesiegt() || state.istMonsterBesiegt());
    }

    @Override
    public String getSimID() {
        return this.simID;
    }

}


/** ZIEL FUER CALLLL: -> State wird nur ueber statechange veraendert. Generierefolgeereignis ist teil von IEreignis
 * while(!istEncounterVorbei()) {
        IEreignis aktuell = ereignisse.pop();
        EreignisResult result = null;
        if(aktuell instanceof AkteurEreignis) {
            if(state.getLebende().contains(((AkteurEreignis) aktuell).getAkteurName())) {
              result = bearbeiteAkteurEreignis((AkteurEreignis)aktuell);
            }
        } else {
            aktuell.ausfuehren();
        }
        List<IEreignis> folgeEreignisse = aktuell.generiereFolgeEreignis(result);
        folgeEreignisse.forEach((e) -> addEreignisZuWarteschlange(e));
        veraendereState(aktuell.getChange());
    }
 */