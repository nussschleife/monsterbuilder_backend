package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.SimTask;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag.Auftrag;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.auftrag.SimStrategy;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResult;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.exception.EncounterSimulationException;

public class EncounterSimulationMaster {
    private static EncounterSimulationMaster MASTER;

    private List<Auftrag> auftraege = new ArrayList<>();
    private ThreadPoolExecutor executor;
    private static final Logger logger = LoggerFactory.getLogger(EncounterSimulationMaster.class);

    private EncounterSimulationMaster() {
        int kerne = Runtime.getRuntime().availableProcessors();
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(kerne);
      
    }

    public synchronized String addAuftrag(SimRequest neuerAuftrag, SimStrategy strategy, Consumer<SimResult> sendeErgebnis){
        Auftrag auftrag = (new Auftrag(strategy, neuerAuftrag, generiereSimID()));  
        this.auftraege.add(auftrag);
        for(SimTask task : auftrag.getTasks()) {
            CompletableFuture<SimResult> result = CompletableFuture.supplyAsync(() -> {
                try { return task.call(); }
                catch (Exception ex) { 
                    ex.printStackTrace();
                    throw new EncounterSimulationException(ex.getMessage()); 
                } 
            }, executor);
            result.thenAccept(t -> sendeErgebnis.accept(t));
            auftrag.addFuture(result);
        }
     
        return auftrag.getSimID();
    }

    public static EncounterSimulationMaster getInstance() {
        if(MASTER == null)
            return new EncounterSimulationMaster();
        return MASTER;
    }

    public synchronized void stoppeAuftragMitId(String simID) {
        Stream<Auftrag> zuLöschenStream = this.auftraege.stream().filter((Auftrag a) -> {return a.getSimID() == simID;});
        Optional<Auftrag> zulöschenOpt = zuLöschenStream.findFirst(); 
        if(zulöschenOpt.isPresent()) {
            Auftrag zuLöschen = zulöschenOpt.get();
            auftraege.remove(zuLöschen);
           // zuLöschen.getTasks().forEach((ComletableFuture<SimResult> t) -> t.cancel());

        }
    }

    private String generiereSimID() {
       return UUID.randomUUID().toString();
    }

}
