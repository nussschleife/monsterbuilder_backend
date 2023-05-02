package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Optional;

import org.python.util.PythonInterpreter;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public class AusweichEreignis implements Ereignis{
    Akteur a;

    public AusweichEreignis(Akteur a) {
        this.a =a ;
    }

    @Override
    public Optional<Ereignis[]> auslösen(SimState state, PythonInterpreter interpreter) {
        //wenn ausweichen nicht geschafft wird dann Schadenereignis?
        Ereignis[] e = {new SchadenEreignis(a, (int) (Math.random()*5)+1)};
        return Optional.of(e);
    }

    @Override
    public boolean addToHead() {
        return true;
    }
    
}
