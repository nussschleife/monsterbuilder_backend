package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(EncounterSimulationMaster.class);

    private EncounterSimulationMaster() {
        kerne = Runtime.getRuntime().availableProcessors()-1;
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(kerne);
    }

    public <T extends SimStrategy> String addAuftrag(SimRequest neuerAuftrag, T strategy, Consumer<SimResult> sendeErgebnis) {
        //erstelle SimTasks anhand einer Strategy und füge sie Hinzu
        //TODO: task Direkt in threadpool ?? 
        Auftrag auftrag = (new Auftrag(strategy, neuerAuftrag, generiereSimID()));
        this.auftragSchlange.add(auftrag);

        for(SimTask task : auftrag.getTasks()) {
            Future<SimResult> result = executor.submit(task);
            try {
                sendeErgebnis.accept(result.get());
            } catch (InterruptedException e) {
                logger.error("Interrupted während auftrag mit id: "+auftrag.getSimID());
                e.printStackTrace();
            } catch (ExecutionException e) {
                logger.error("executionexception auftrag: "+auftrag.getSimID());
                e.printStackTrace();
            }
        }
        return "SIMID";
    }

    public static EncounterSimulationMaster getInstance() {
        if(MASTER == null)
            return new EncounterSimulationMaster();
        return MASTER;
    }

    public void stoppeAuftragMitId(String simID) {

    }

    private String generiereSimID() {
       return UUID.randomUUID().toString();
    }
}
