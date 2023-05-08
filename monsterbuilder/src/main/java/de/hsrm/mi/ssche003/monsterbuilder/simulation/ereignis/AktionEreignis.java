package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AktionEreignis implements AkteurEreignis {

    private String akteur;
    EreignisCode code = EreignisCode.AKTION;
    public boolean toedlich;
    public String gegner = "";

    public AktionEreignis( String akteur) {
        this.akteur = akteur;
    }

    public Boolean addToHead() {
        return false;
    }

    @Override
    public List<IEreignis> generiereFolEreignis() {
        this.toedlich = false;
        this.gegner = "";
        return Arrays.asList(this);
    }


    @Override
    public Optional<StateChange> getChange() {
        Optional<StateChange> change = Optional.empty();
        if(toedlich && gegner != "") 
            change = Optional.of(new TotChange(gegner));
        
        return change;
    }

    @Override
    public String getAkteurName() {
        return akteur;
    }
    
    @Override
    public EreignisCode getCode() {
        return this.code;
    }
}
