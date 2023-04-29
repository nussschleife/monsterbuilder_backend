package de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag;

import java.util.ArrayList;
import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter.Encounter;

public class Auftrag {
    SimStrategy strategy;
    ArrayList<SimTask> tasks;
    String simID;

    public Auftrag(SimStrategy strategy, SimRequest request, String id) {
        this.strategy = strategy;
        this.tasks = strategy.createSimTasks(request);
        this.simID = id;
    }
    
    public boolean istFertig() {
        return tasks.stream().allMatch((SimTask task) -> {return task.istFertig();});
    }

    public ArrayList<SimTask> getTasks() {
        return this.tasks;
    }

    public Optional<Encounter> erstelleEncounter() {
        if(istFertig()) {
            return Optional.of(new Encounter());
        }
        return Optional.empty();
    }
}
