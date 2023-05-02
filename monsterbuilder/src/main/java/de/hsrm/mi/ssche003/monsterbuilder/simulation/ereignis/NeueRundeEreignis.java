package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimState;

public class NeueRundeEreignis implements Ereignis{

    @Override
    public Optional<Ereignis[]> auslösen(SimState state) {
        state.kampfrunden++;
        Ereignis[] folEreignisse = new Ereignis[state.getLebende().size()+1];
        for( int i = 0; i < folEreignisse.length; i++) {
            folEreignisse[i] = new AktionEreignis(false, state.getLebende().iterator().next().getId());
        }
        folEreignisse[folEreignisse.length-1] = new NeueRundeEreignis();
        return Optional.of(folEreignisse); 
    }

    @Override
    public boolean addToHead() {
        return false;
    }
    
}
