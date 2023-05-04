package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AktionEreignis implements AkteurEreignis {

    private String akteur;
    EreignisCode code = EreignisCode.ANGREIFEN;
    public int schaden;
    public String gegner = "";

    public AktionEreignis( String akteur) {
        this.akteur = akteur;
    }

    public Boolean addToHead() {
        return false;
    }

    @Override
    public List<IEreignis> generiereFolEreignis() {
        if(schaden > 0 && gegner != "")
            return Arrays.asList(new SchadenEreignis(gegner, schaden), new AktionEreignis(akteur));
       return Arrays.asList(new AktionEreignis(akteur));
    }


    @Override
    public Optional<StateChange> getChange() {
        return Optional.empty();
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
