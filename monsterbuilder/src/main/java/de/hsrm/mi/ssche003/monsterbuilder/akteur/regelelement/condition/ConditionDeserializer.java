package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.condition;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ConditionDeserializer extends StdDeserializer<Condition> {

    public ConditionDeserializer() { 
        this(null); 
    } 

    public ConditionDeserializer(Class<?> vc) { 
        super(vc); 
    }

    @Override
    public Condition deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException, JsonProcessingException {
        Condition condition;
        JsonNode node = jp.getCodec().readTree(jp);
        Long id = (node.get("id")).asLong();
        String name = node.get("name").asText();
        int dauer = node.get("dauer").asInt();
        if(name == "PRONE") {
            condition = new Prone();
        } else if (name == "PETRIFIED") {
            condition = new Petrified();
        } else {
            condition = new Enfeebled();
        }
        condition.setId(id);
        condition.setDauer(dauer);
        condition.setName(name);
        return condition;
    }
}
