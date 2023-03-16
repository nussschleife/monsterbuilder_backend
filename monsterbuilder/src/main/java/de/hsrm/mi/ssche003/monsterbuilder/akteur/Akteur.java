package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@MappedSuperclass	
public class Akteur {
    @Id @GeneratedValue @JsonIgnore
    private Long id;
    @Version
    private Long version;
    @NotNull @NotEmpty
    private String name;
    @PositiveOrZero
    private byte lebenspunkte;
    @Positive
    private byte ruestungsklasse;
    @PositiveOrZero //TODO: modulo
    private byte geschwindigkeit_ft; //Geschwindigkeit in 5-Fuß Abstufungen
}
