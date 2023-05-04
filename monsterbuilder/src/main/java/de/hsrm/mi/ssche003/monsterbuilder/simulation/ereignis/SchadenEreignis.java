package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;

public class SchadenEreignis implements AkteurEreignis {
    public String getroffener;
    public int schaden;
    Schadensart art;
    EreignisCode code = EreignisCode.SCHADEN;
    Logger logger = LoggerFactory.getLogger(SchadenEreignis.class);
    private boolean changeState = false; //vom skript setzen lassen

    public SchadenEreignis(String betroffener, int dmg) {
        this.getroffener = betroffener;
        this.schaden = dmg;
    }

    @Override
    public List<IEreignis> generiereFolEreignis() { 
        return new ArrayList<IEreignis>(0);
    }


    @Override
    public Boolean addToHead() {
        return true;
    }


    @Override
    public Optional<StateChange> getChange() {
        if(this.changeState)
            return Optional.of(new TotChange(getroffener));
        return Optional.empty();
    }

    @Override
    public String getAkteurName() {
       return getroffener;
    }

    @Override
    public EreignisCode getCode() {
        return this.code;
    }

    public void setChange(boolean value) {
        this.changeState = value;
    }

}
