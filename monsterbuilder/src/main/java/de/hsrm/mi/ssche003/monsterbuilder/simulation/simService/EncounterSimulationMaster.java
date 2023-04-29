package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag.Auftrag;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag.SimStrategy;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;

public class EncounterSimulationMaster {
    private static EncounterSimulationMaster MASTER;

    private Queue<Auftrag> auftragSchlange = new LinkedList<>();
    private int kerne;
    private ThreadPoolExecutor executor;
    //private Queue<Auftrag || SimResult> fertigeTasks = new LinkedList<>();

    private EncounterSimulationMaster() {
        kerne = Runtime.getRuntime().availableProcessors()-1;
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(kerne);
    }

    public <T extends SimStrategy> String addAuftrag(SimRequest neuerAuftrag, T strategy) {
        //erstelle SimTasks anhand einer Strategy und füge sie Hinzu
        //TODO: task Direkt in threadpool ?? 
        Auftrag auftrag = (new Auftrag(strategy, neuerAuftrag, generiereSimID()));
        this.auftragSchlange.add(auftrag);
        for(SimTask task : auftrag.getTasks()) {
            Future<SimResult> result = executor.submit(task);
            sendeSimulationsdurchlauf(result);
        }
        return "SIMID";
    }

    public static EncounterSimulationMaster getInstance() {
        if(MASTER == null)
            return new EncounterSimulationMaster();
        return MASTER;
    }

    private void sendeSimulationsdurchlauf(Future<SimResult> result) { 
            
            //TODO: Sende result ans Frontend
            //füge result zu ergebnissen hinzu
            //checke ob der Auftrag beendet ist usw.
    }

    public void stoppeAuftragMitId(String simID) {

    }

    private String generiereSimID() {
       return UUID.randomUUID().toString();
    }
}
