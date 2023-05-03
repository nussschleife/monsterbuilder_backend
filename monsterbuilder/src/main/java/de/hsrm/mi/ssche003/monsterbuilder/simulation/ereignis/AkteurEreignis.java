package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

import java.util.List;

import org.python.core.PyObject;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;

public interface AkteurEreignis extends IEreignis{
    
    public List<IEreignis> generiereFolEreignis(PyObject py);
    public String getFuncName();
    public Long getAkteurID();
    public Akteur getAkteurVerhalten();
    
}
