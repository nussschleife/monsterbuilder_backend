package de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag;

import java.util.ArrayList;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;

public interface SimStrategy {
    public ArrayList<SimTask> createSimTasks(SimRequest request);
}
