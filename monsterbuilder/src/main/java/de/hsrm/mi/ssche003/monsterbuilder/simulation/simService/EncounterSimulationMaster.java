package de.hsrm.mi.ssche003.monsterbuilder.simulation.simService;

import java.util.LinkedList;
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

public class EncounterSimulationMaster {
    private static EncounterSimulationMaster MASTER;

    private Queue<Auftrag> auftragSchlange = new LinkedList<>();
    private int kerne;
    private ThreadPoolExecutor executor;
    private static final Logger logger = LoggerFactory.getLogger(EncounterSimulationMaster.class);

    private EncounterSimulationMaster() {
        kerne = Runtime.getRuntime().availableProcessors();
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(kerne > 1 ? kerne : 2);
      
    }

    public <T extends SimStrategy> String addAuftrag(SimRequest neuerAuftrag, T strategy, Consumer<SimResult> sendeErgebnis){
        Auftrag auftrag = (new Auftrag(strategy, neuerAuftrag, generiereSimID()));  
        this.auftragSchlange.add(auftrag);
        for(SimTask task : auftrag.getTasks()) {
            CompletableFuture<SimResult> result = CompletableFuture.supplyAsync(() -> {
                try { return task.call(); }
                catch (Exception ex) { 
                    ex.printStackTrace();
                    throw new CompletionException(ex); 
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

    public void stoppeAuftragMitId(String simID) {
        Stream<Auftrag> zuLöschenStream = this.auftragSchlange.stream().filter((Auftrag a) -> {return a.getSimID() == simID;});
        Optional<Auftrag> zulöschenOpt = zuLöschenStream.findFirst(); 
        if(zulöschenOpt.isPresent()) {
            Auftrag zuLöschen = zulöschenOpt.get();
            auftragSchlange.remove(zuLöschen);
           // zuLöschen.getTasks().forEach((ComletableFuture<SimResult> t) -> t.cancel());

        }
    }

    private String generiereSimID() {
       return UUID.randomUUID().toString();
    }

}
