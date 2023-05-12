package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.ArrayList;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;


public class SimState {
    private ArrayList<Akteur> lebendig;
    private Set<Charakter> charaktere;
    private Set<Monster> monster;
    private int kampfrunden;
    //Battlemap usw.

    public SimState(){}

    public SimState initSimState(Set<Charakter> gruppe, Set<Monster> monster) {
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
      return !lebendig.stream().anyMatch(mon -> monster.stream().anyMatch(m -> m.getName().equals(mon.getName())));
    }

    public boolean istGruppeBesiegt() {
        return !lebendig.stream().anyMatch(chara -> charaktere.stream().anyMatch(m -> m.getName().equals(chara.getName())));
    }

    public ArrayList<Akteur> getLebende() {
        return this.lebendig;
    }

    public void initChange(ArrayList<Akteur> lebend) {
        this.lebendig = lebend;
    }

    public void erhoeheRunden() {
        this.kampfrunden++;
    }

    public Set<Charakter> getCharaktere() {
        return charaktere;
    }

    public Set<Monster> getMonster() {
        return monster;
    }

    public int getRunden() {
        return this.kampfrunden;
    }
}
