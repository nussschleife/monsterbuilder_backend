package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.ArrayList;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;


public class SimState {
    ArrayList<Akteur> lebendig;
    Set<Charakter> charaktere;
    Set<Monster> monster;
   public int kampfrunden;
    //Battlemap usw.

    public SimState(){}

    public SimState initSimState(Set<Charakter> gruppe, Set<Monster> monster) {
        this.lebendig = new ArrayList<>();
        this.charaktere = gruppe;
        this.monster = monster;
        kampfrunden = 0;
        return this;
    }

    public void toeteAkteur(Akteur akteur) {
            lebendig.remove(akteur);
    }

    public boolean istMonsterBesiegt() {
      return !lebendig.stream().anyMatch(mon -> monster.stream().anyMatch(m -> mon.getId().equals(m.getId())));
    }

    public boolean istGruppeBesiegt() {
        return !lebendig.stream().anyMatch(chara -> charaktere.stream().anyMatch(m -> chara.getId().equals(m.getId())));
    }

    public ArrayList<Akteur> getLebende() {
        return this.lebendig;
    }


    public void setLebende(ArrayList<Akteur> lebend) {
        this.lebendig = lebend;
    }

}
