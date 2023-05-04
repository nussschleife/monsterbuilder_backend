package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.List;

public interface AkteurEreignis extends IEreignis{
    
    public List<IEreignis> generiereFolEreignis();
    public String getAkteurName();
    public EreignisCode getCode();
    
}
