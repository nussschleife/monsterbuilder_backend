package de.hsrm.mi.ssche003.monsterbuilder.simulation.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;

@RestController
@RequestMapping("/simulation")
public class SimRestApi {

    @PostMapping("/stop/{siMID}")
    public void beendeSimulation(@PathVariable String simID) {

    }

    @PostMapping("/neu/{request}")
    public ResponseEntity<SimResponse> starteNeueSimulation(@PathVariable SimRequest request) {
        return ResponseEntity.ok().body(new SimResponse("test"));
    }
    
}
