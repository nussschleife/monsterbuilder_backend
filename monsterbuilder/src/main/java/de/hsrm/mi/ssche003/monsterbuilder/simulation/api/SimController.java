package de.hsrm.mi.ssche003.monsterbuilder.simulation.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimRequest;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.SimResponse;
import de.hsrm.mi.ssche003.monsterbuilder.simulation.simService.SimService;
import jakarta.annotation.PostConstruct;

@Controller
public class SimController {

    private static final Logger logger = LoggerFactory.getLogger(SimController.class);
    @Autowired SimService simService;
    @Autowired SimpMessagingTemplate template;
    ObjectMapper mapper;
    ObjectWriter writer;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        writer = mapper.writer().withDefaultPrettyPrinter();
    }
  

    @MessageMapping("/sim/neu/") 
    public void starteNeueSimulation(String msg, @Header("simpsessionid") String sessionID) {
        //todo prüfen dass monster valid ist
        //todo charaktere an level anpassen lassen
        SimRequest request;
        SimResponse response = new SimResponse();
        String antwortNachricht = "";
        try {
            request = mapper.readValue(msg, SimRequest.class);
            request.setUserName(sessionID);
            response.setSimID(simService.starteSimulation(request).getSimID());
            response.setSimName(request.getSimName());
            antwortNachricht = writer.writeValueAsString(response);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        template.convertAndSend("/queue/user/sim/neu/"+sessionID, antwortNachricht);
        logger.info("ende der api methode");
    }

}
