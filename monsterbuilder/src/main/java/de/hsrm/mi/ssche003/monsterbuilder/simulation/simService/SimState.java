package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.ArrayList;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;


public class SimState {
    ArrayList<String> lebendig;
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
        for( Monster mon : monster){
            this.lebendig.add(mon.getName());
        } 
        for( Charakter chara : charaktere){
            this.lebendig.add(chara.getName());
        } 
        return this;
    }

    public void toeteAkteur(String akteur) {
        lebendig.remove(akteur);
    }

    public boolean istMonsterBesiegt() {
      return !lebendig.stream().anyMatch(mon -> monster.stream().anyMatch(m -> m.getName().equals(mon)));
    }

    public boolean istGruppeBesiegt() {
        return !lebendig.stream().anyMatch(chara -> charaktere.stream().anyMatch(m -> m.getName().equals(chara)));
    }

    public ArrayList<String> getLebende() {
        return this.lebendig;
    }


    public void setLebende(ArrayList<String> lebend) {
        this.lebendig = lebend;
    }

    public Set<Charakter> getCharaktere() {
        return charaktere;
    }

    public Set<Monster> getMonster() {
        return monster;
    }

    

}
