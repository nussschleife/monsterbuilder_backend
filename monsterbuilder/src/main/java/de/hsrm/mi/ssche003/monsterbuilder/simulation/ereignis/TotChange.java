package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;


import de.hsrm.mi.ssche003.monsterbuilder.simulation.service.SimState;

public class TotChange implements StateChange{

    private String akteur;

    public TotChange(String a) {
        this.akteur = a;
    }

    @Override
    public void changeState(SimState state) {
        state.toeteAkteur(akteur);
    }
    
}
