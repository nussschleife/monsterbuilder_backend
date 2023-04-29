package de.hsrm.mi.ssche003.monsterbuilder.simulation;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.Ereignis;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis.InitiativeEreignis;

public class SimTask implements Callable<SimResult> {
    SimState state;
    int kampfrunden;
    ArrayList<Akteur> reihenfolge;
    ArrayList<Ereignis> ereignisse;
    Monster monster;
    Gruppe gruppe;

    public SimTask(Gruppe gruppe, Monster monster) {
        this.monster = monster;
        this.gruppe = gruppe;
        ereignisse = new ArrayList<Ereignis>();
        reihenfolge = new ArrayList<Akteur>();
        reihenfolge.add(monster);
        reihenfolge.addAll(gruppe.getAllCharaktere());
        state = new SimState(reihenfolge);
    }

    @Override
    public SimResult call() {
        while(!istEncounterVorbei()) {
            //ereignis auf index 1 abarbeiten
        }
        beendeEncounter();
        return new SimResult(kampfrunden);
    }

    private void initEncounter() { 
        //Startereignis erstellen
        Ereignis startereignis = new InitiativeEreignis(reihenfolge);
       //alle HP auffüllen

        //liste an ereignissen wird erstlelt -> oder wird SimTask mit Startereignis zum Ini würfeln initialisiert?
            //ereignis wäre eine Runde oder Aktion pro Spieler
        
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

    private boolean istMonsterBesiegt() {
        return monster.getLebenspunkte() <= 0;
    }

    private boolean istGruppeBesiegt() {
        return gruppe.getAllCharaktere().stream().allMatch(charakter -> {return charakter.getLebenspunkte() <= 0;});
    }

    private boolean istEncounterVorbei() {
        return keineErgeinisseÜbrig() || istGruppeBesiegt() || istMonsterBesiegt();
    }

}
