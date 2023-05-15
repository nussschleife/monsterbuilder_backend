package de.hsrm.mi.ssche003.monsterbuilder.simulation.service;

import java.util.ArrayList;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag.SimStrategy;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue.SimValue;

public class DESStrategy implements SimStrategy{

    @Override
    public ArrayList<DESimTask> createSimTasks(String simID, SimRequest request) {
        ArrayList<DESimTask> alleTasks = new ArrayList<>();
        for(SimValue value : request.getValues()) {
            for( int i = 0; i < request.getDurchlaeufe(); i++) {
                alleTasks.add(new DESimTask(request.getGruppe(), request.getMonster(), simID, value,1, request.getCustomSkriptName())); 
            }
            
        }
        return alleTasks;
    }

}
