package de.hsrm.mi.ssche003.monsterbuilder.simulation.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimService;

@Controller
public class SimController {

    private static final Logger logger = LoggerFactory.getLogger(SimController.class);
    @Autowired SimService simService;

    //TODO: bratchat dding angucken
    @MessageMapping("/sim/neu") 
    @SendTo("/") //???
    public String starteNeueSimulation( SimRequest request) {
    //TODO: Simrequest als string annehmen
        return simService.starteSimulation(request).getSimID();
    }
}
