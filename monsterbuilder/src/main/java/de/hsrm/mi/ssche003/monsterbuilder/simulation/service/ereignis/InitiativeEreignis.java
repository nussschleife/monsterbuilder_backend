package de.hsrm.mi.ssche003.monsterbuilder.simulation.service.ereignis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.Monster;

public class InitiativeEreignis implements EncounterEreignis {
    
    ArrayList<Akteur> alleTeilnehmer;
    public InitiativeEreignis(Monster monster, Set<Charakter> charaktere) {
        alleTeilnehmer = new ArrayList<Akteur>(charaktere);
        alleTeilnehmer.add(monster);
    }
    
    @Override
    public List<IEreignis> generiereFolgeEreignis() {
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