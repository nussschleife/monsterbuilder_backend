package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.python.core.PyBoolean;
import org.python.core.PyObject;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;

public class AusweichEreignis implements AkteurEreignis {
    Akteur a;
    EreignisCode code = EreignisCode.AUSWEICHEN;
    int dmg;
    int schwierigkeit = 10;

    public AusweichEreignis(Akteur a, int schaden) {
        this.a =a ;
        this.dmg = schaden;
    }

    @Override
    public List<IEreignis> generiereFolEreignis(PyObject py) {
        //PyObject enthält Boolean: wurde ausweichen geschafft?
        if(((PyBoolean) py).getValue() == 1) //falls ja dann schaden halbieren
            this.dmg /= 2;
        
        List<IEreignis> ereignisse = new ArrayList<IEreignis>(1);
        ereignisse.add(new SchadenEreignis(this.a, this.dmg));
        return ereignisse;
    }

    @Override
    public String getFuncName() {
        return code.toString();
    }

    @Override
    public Boolean addToHead() {
        return true;
    }

    @Override
    public Long getAkteurID() {
        return 1l;
    }

    @Override
    public Optional<StateChange> getChange() {
        return Optional.empty();
    }

    public Akteur getAkteurVerhalten() {
        return a;
    }
    
}
