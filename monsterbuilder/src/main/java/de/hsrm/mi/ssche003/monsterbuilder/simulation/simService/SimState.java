package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.LinkedList;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;


public class SimState {
    LinkedList<Akteur> lebendig;
    Gruppe gruppe;
    Set<Monster> monster;
   public int kampfrunden;
    //Battlemap usw.

    public SimState(Gruppe gruppe, Set<Monster> monster) {
        this.lebendig = new LinkedList<Akteur>();
        this.lebendig.addAll(monster);
        this.lebendig.addAll(gruppe.getAllCharaktere());
        this.gruppe = gruppe;
        this.monster = monster;
        kampfrunden = 0;
    }

    public void toeteAkteur(Akteur akteur) {
        this.lebendig.remove(akteur);
    }

    public boolean istMonsterBesiegt() {
        return monster.stream().allMatch(mon -> {return mon.getLebenspunkte() <= 0;});
    }

    public boolean istGruppeBesiegt() {
        return gruppe.getAllCharaktere().stream().allMatch(charakter -> {return charakter.getLebenspunkte() <= 0;});
    }

    public LinkedList<Akteur> getLebende() {
        return this.lebendig;
    }


    public void setLebende(LinkedList<Akteur> lebend) {
        this.lebendig = lebend;
    }

}
