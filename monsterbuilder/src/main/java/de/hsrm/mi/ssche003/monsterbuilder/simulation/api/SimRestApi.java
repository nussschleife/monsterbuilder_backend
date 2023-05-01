package de.hsrm.mi.ssche003.monsterbuilder.simulation.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimService;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;

@RestController
@RequestMapping("/api/simulation")
public class SimRestApi {

    @Autowired SimService simService;
    @Autowired SimpMessagingTemplate template;

    @PostMapping("/stop/{siMID}")
    public void beendeSimulation(@PathVariable String simID) {

    }

    @PostMapping("/sim/neu/{sessionID}")
    public ResponseEntity<SimResponse> starteNeueSimulation(@RequestBody SimRequest request, @PathVariable String sessionID) {
        String response = "";
        request.setUserName(sessionID);
        response = simService.starteSimulation(request).getSimID();
        template.convertAndSend("/queue/user/sim/neu/"+sessionID, response);
        //logger.info("ende der api methode");
        
        return ResponseEntity.ok().body(new SimResponse("test"));
    }
    
}
