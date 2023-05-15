package de.hsrm.mi.ssche003.monsterbuilder.simulation.service;

import java.util.ArrayList;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;


public class SimState {
    private ArrayList<Akteur> lebendig;
    private Set<Charakter> charaktere;
    private Monster monster;
    private int kampfrunden;

    public SimState(){}

    public SimState initSimState(Set<Charakter> gruppe, Monster monster) {
        this.lebendig = new ArrayList<>();
        this.charaktere = gruppe;
        this.monster = monster;
        kampfrunden = 0;
        return this;
    }

    public void toeteAkteur(String akteur) {
        lebendig.removeIf((lebender) -> lebender.getName().equals(akteur));
    }

    public boolean istMonsterBesiegt() {
      return !lebendig.stream().anyMatch(mon -> mon.getName().equals(monster.getName()));
    }

    public boolean istGruppeBesiegt() {
        return !lebendig.stream().anyMatch(chara -> charaktere.stream().anyMatch(m -> m.getName().equals(chara.getName())));
    }

    public ArrayList<Akteur> getLebende() {
        return this.lebendig;
    }

    public void aendereReihenfolge(ArrayList<Akteur> lebend) {
        this.lebendig = lebend;
    }

    public void erhoeheRunden() {
        this.kampfrunden++;
    }

    public Set<Charakter> getCharaktere() {
        return charaktere;
    }

    public Monster getMonster() {
        return monster;
    }

    public int getRunden() {
        return this.kampfrunden;
    }
}
