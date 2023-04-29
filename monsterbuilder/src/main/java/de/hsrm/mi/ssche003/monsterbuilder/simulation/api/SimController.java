package de.hsrm.mi.ssche003.monsterbuilder.simulation.api;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SimController {

    private static final Logger logger = LoggerFactory.getLogger(SimController.class);
    @Autowired private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/topic/sim")
    @SendTo("/topic/sim/fromserver")
    public SimResponse simResponse(SimResponse message){ //TODO: in JSON String umwandeln?
        logger.info("sende simResponse gehörend zu sim mit id: "+message.getSimID());
        return message;
    }

    /*@MessageMapping("/sim") 
    public void sendSpecific(
    @Payload Message msg, 
    Principal user, 
    @Header("simpSessionId") String sessionId) throws Exception { 
        OutputMessage out = new OutputMessage(
        msg.getFrom(), 
        msg.getText(),
        new SimpleDateFormat("HH:mm").format(new Date())); 
        simpMessagingTemplate.convertAndSendToUser(
        msg.getTo(), "/user/topic", out); 
    }*/
}
