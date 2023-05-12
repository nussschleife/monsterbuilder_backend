package de.hsrm.mi.ssche003.monsterbuilder.simulation.dto.simValue;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @Type(value = Level.class, name = "Level"),
    @Type(value = ConditionValue.class, name = "Condition"),
    @Type(value = SchadenValue.class, name = "Schadensart") })
public interface SimValue {

    @Override
    public String toString();
    
}
