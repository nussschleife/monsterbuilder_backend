package de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;

public class Auftrag {
    ArrayList<SimTask> tasks;
    ArrayList<CompletableFuture<SimResult>> results = new ArrayList<>();
    String simID;

    public Auftrag(SimStrategy strategy, SimRequest request, String id) {
        this.tasks = strategy.createSimTasks(id, request);
        this.simID = id;
    }
    
    public boolean istFertig() {
        return results.stream().allMatch((Future<SimResult> result) -> result.isDone()) || results.size() == 0;
    }

    public ArrayList<SimTask> getTasks() {
        return this.tasks;
    }

    public String getSimID() {
        return this.simID;
    }

    public void addFuture(CompletableFuture<SimResult> result) {
        this.results.add(result);
    }

    public ArrayList<CompletableFuture<SimResult>> getResults(){
        return results;
    }

    
}
