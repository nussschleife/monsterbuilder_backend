package de.hsrm.mi.ssche003.monsterbuilder.simulation.api;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimService;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;

@RestController
@RequestMapping("/api/simulation")
public class SimRestApi {

    @Value("${custom.skript.path}")
    private String skriptPath;

    @Autowired
    SimService simService;
    Logger logger = LoggerFactory.getLogger(SimRestApi.class);

    @PostMapping("/stop/{siMID}")
    public void beendeSimulation(@PathVariable String simID) {

    }

    @PostMapping("/sim/neu/")
    public SimResponse starteNeueSimulation(@RequestBody SimRequest request,
            @RequestHeader("sessionid") String sessionID) {
        SimResponse response = new SimResponse();
        request.setUserName(sessionID);
        response.setSimID(simService.starteSimulation(request).getSimID());
        response.setSimName(request.getSimName());

        return response;
    }

    @GetMapping("/skript/template")
    public ResponseEntity<Resource> get_holeTemplate() {
        logger.info("hole template");
        Resource file = new ClassPathResource("/skripts/VerhaltenTemplate.py");
        return ResponseEntity.ok().body(file);
    }

    @PostMapping("/skript/template/custom")
    public ResponseEntity<Void> post_speichereTemplate(@RequestBody MultipartFile file) {
        logger.info("neues template erhalten");
        String home = System.getProperty("user.dir"); // TODO: aendern pls
        File skript = new File(home + '/' + skriptPath, file.getOriginalFilename());
        logger.info("PATH: " + skript.getPath());

        try {
            file.transferTo(skript);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

}
