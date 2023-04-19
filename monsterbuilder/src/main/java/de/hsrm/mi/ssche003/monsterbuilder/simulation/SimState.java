package de.hsrm.mi.ssche003.monsterbuilder.simulation;

import java.util.ArrayList;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;


public class SimState {
    ArrayList<Akteur> lebendig;
    //Battlemap usw.

    public SimState(ArrayList<Akteur> alleTeilnehmer) {
        this.lebendig = alleTeilnehmer;
    }

    public Akteur verwundeAkteur(Akteur akteur, int schaden) {
        akteur.setLebenspunkte(akteur.getLebenspunkte() - schaden);
        if(akteur.getLebenspunkte() <= 0)
            lebendig.remove(akteur);
        return akteur;
    }

}
