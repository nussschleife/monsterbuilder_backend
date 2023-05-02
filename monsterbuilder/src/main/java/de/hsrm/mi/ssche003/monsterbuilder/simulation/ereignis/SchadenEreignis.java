package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Optional;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

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
    public Optional<Ereignis[]> auslösen(SimState state, PythonInterpreter interpreter) {
        PyObject[] pys = new PyObject[2];
        pys[0] = new PyInteger(getroffener.getId().intValue());
        pys[1] = new PyInteger(schaden);
        if(((PyInteger) interpreter.get("schaden").__call__(pys)).asInt() != 0) 
            state.toeteAkteur(getroffener);
        return Optional.empty();
    }

    @Override
    public boolean addToHead() {
        return true;
    }
    
}
