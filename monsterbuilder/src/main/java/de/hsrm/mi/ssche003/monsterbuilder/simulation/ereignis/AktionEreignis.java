package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.python.core.PyObject;
import org.python.core.PyObjectDerived;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;

public class AktionEreignis implements AkteurEreignis {

    private Akteur akteur;
    EreignisCode code = EreignisCode.ANGREIFEN;
    public int schaden;
    public Akteur gegner;

    public AktionEreignis( Akteur akteur) {
        this.akteur = akteur;
    }

    public Boolean addToHead() {
        return false;
    }

    @Override
    public List<IEreignis> generiereFolEreignis(PyObject py) {
       List<IEreignis> ereignis =  new ArrayList<IEreignis>(1);
       PyObjectDerived derived = (PyObjectDerived) py;
       ereignis.add((SchadenEreignis)derived.__tojava__(SchadenEreignis.class));
       return ereignis;
    }

    @Override
    public String getFuncName() {
        return code.toString();
    }

    public Long getAkteurID() {
        return 1l;
    }

    @Override
    public Optional<StateChange> getChange() {
        return Optional.empty();
    }

    @Override
    public Akteur getAkteurVerhalten() {
        return akteur;
    }
    
}
