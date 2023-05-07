package de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.encounter.Encounter;

public class Auftrag {
    SimStrategy strategy;
    ArrayList<SimTask> tasks;
    ArrayList<CompletableFuture<SimResult>> results = new ArrayList<>();
    String simID;

    public Auftrag(SimStrategy strategy, SimRequest request, String id) {
        this.strategy = strategy;
        this.tasks = strategy.createSimTasks(id, request);
        this.simID = id;
    } //TODO: Auftrag soll SimRequest ersetzen und nicht beinhalten
    
    public boolean istFertig() {
        return results.stream().allMatch((Future<SimResult> result) -> result.isDone()) || results.size() == 0;
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
