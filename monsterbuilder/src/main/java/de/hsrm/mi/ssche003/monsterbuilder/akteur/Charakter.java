package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Charakter extends Akteur{

    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private Long id;

    @Min(1) @Max(20)
    private byte level;

    @ManyToOne
    private Gruppe gruppe;
}
