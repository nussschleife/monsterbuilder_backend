package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.python.core.PyBoolean;
import org.python.core.PyObject;


public class AusweichEreignis implements AkteurEreignis {
    String a;
    EreignisCode code = EreignisCode.AUSWEICHEN;
    int dmg;
    int schwierigkeit = 10;
    public boolean ausgewichen = false;

    public AusweichEreignis(String a, int schaden) {
        this.a =a ;
        this.dmg = schaden;
    }

    @Override
    public List<IEreignis> generiereFolEreignis() {
        //PyObject enthält Boolean: wurde ausweichen geschafft?
        if(!ausgewichen)
            return Arrays.asList(new SchadenEreignis(a, dmg));
        return new ArrayList<IEreignis>(0);
    }

    @Override
    public Boolean addToHead() {
        return true;
    }


    @Override
    public Optional<StateChange> getChange() {
        return Optional.empty();
    }

    public String getAkteurName() {
        return a;
    }
    
    @Override
    public EreignisCode getCode() {
        return this.code;
    }
}
