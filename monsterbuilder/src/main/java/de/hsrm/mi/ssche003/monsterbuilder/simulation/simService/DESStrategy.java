package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.ArrayList;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag.SimStrategy;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;

public class DESStrategy implements SimStrategy{

    @Override
    public ArrayList<DESimTask> createSimTasks(String simID, SimRequest request) {
        ArrayList<DESimTask> alleTasks = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            alleTasks.add(new DESimTask(simID, "testnachricht: "+String.valueOf(i)));
        }
        return alleTasks;
    }

}
