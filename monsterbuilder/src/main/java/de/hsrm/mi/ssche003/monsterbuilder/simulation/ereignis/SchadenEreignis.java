package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;

public class SchadenEreignis implements AkteurEreignis {
    public Akteur getroffener;
    public int schaden;
    Schadensart art;
    EreignisCode code = EreignisCode.SCHADEN;
    Logger logger = LoggerFactory.getLogger(SchadenEreignis.class);
    private boolean changeState = false;

    public SchadenEreignis(Akteur betroffener, int dmg) {
        this.getroffener = betroffener;
        this.schaden = dmg;
    }

    @Override
    public List<IEreignis> generiereFolEreignis(PyObject py) {
        changeState = ((PyInteger) py).asInt() != 0;
        return new ArrayList<IEreignis>(0);
    }

    @Override
    public String getFuncName() {
        return this.code.toString();
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
        if(changeState)
            return Optional.of(new TotChange(getroffener));
        return Optional.empty();
    }

    @Override
    public Akteur getAkteurVerhalten() {
       return getroffener;
    }



}
