package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.ArrayList;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.SimValue;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag.SimStrategy;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;

public class DESStrategy implements SimStrategy{

    @Override
    public ArrayList<DESimTask> createSimTasks(String simID, SimRequest request) {
        ArrayList<DESimTask> alleTasks = new ArrayList<>();
        for(SimValue value : request.getValues()) {
            alleTasks.add(new DESimTask(request.getGruppe(), request.getMonster(), simID, value));
        }
        return alleTasks;
    }

}
