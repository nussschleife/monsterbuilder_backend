package de.hsrm.mi.ssche003.monsterbuilder.simulation.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SimConfig implements WebSocketMessageBrokerConfigurer{

   @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/queue/");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/sim").setAllowedOrigins("*");
        registry.addEndpoint("/sim/neu").setAllowedOrigins("*");
    }
}
