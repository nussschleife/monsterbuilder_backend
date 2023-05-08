package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;

public class InitiativeEreignis implements EncounterEreignis {
    
    ArrayList<Akteur> alleTeilnehmer;
    public InitiativeEreignis(Set<Monster> monster, Set<Charakter> charaktere) {
        alleTeilnehmer = new ArrayList<Akteur>(monster);
        alleTeilnehmer.addAll(charaktere);
    }
    
    @Override
    public List<IEreignis> auslösen() {
        alleTeilnehmer.sort(Comparator.comparingInt((akteur) -> akteur.wuerfleInitiative()));
        ArrayList<IEreignis> folEreignisse = new ArrayList<>(0);
        folEreignisse.add(new NeueRundeEreignis());
        for( Akteur akteur : alleTeilnehmer) {
            folEreignisse.add( new AktionEreignis(akteur.getName()));
        }
        return folEreignisse;
    }

    public Boolean addToHead() {
       return false;
    }

    @Override
    public Optional<StateChange> getChange() {
        return Optional.of(new ReihenfolgeChange(alleTeilnehmer));
    }

}
