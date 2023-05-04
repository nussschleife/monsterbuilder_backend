package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.List;

import org.python.core.PyObject;


public interface AkteurEreignis extends IEreignis{
    
    public List<IEreignis> generiereFolEreignis();
    public String getAkteurName();
    public EreignisCode getCode();
    
}
