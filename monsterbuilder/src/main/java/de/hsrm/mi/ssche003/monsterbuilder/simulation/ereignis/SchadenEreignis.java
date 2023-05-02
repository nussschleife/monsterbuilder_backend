package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public class SchadenEreignis implements Ereignis {
    Akteur getroffener;
    int schaden;
    Schadensart art;

    public SchadenEreignis(Akteur betroffener, int dmg) {
        this.getroffener = betroffener;
        this.schaden = dmg;
    }

    @Override
    public Optional<Ereignis[]> auslösen(SimState state) {
        state.verwundeAkteur(getroffener, schaden);
        return Optional.empty();
    }

    @Override
    public boolean addToHead() {
        return true;
    }
    
}
