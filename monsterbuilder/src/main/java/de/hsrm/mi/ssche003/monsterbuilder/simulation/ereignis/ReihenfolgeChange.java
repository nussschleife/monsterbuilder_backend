package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.ArrayList;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.service.SimState;

public class ReihenfolgeChange implements StateChange{


    private ArrayList<Akteur> sortiert;

    public ReihenfolgeChange(ArrayList<Akteur> sortiert) {
        this.sortiert = sortiert;
    }

    @Override
    public void changeState(SimState state) {
        state.aendereReihenfolge(sortiert);
    }
    
}
