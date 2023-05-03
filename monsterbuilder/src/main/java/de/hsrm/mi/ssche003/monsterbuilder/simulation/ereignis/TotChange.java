package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;


import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public class TotChange implements StateChange{

    private Akteur akteur;

    public TotChange(Akteur a) {
        this.akteur = a;
    }

    @Override
    public void changeState(SimState state) {
        state.toeteAkteur(akteur);
    }
    
}
