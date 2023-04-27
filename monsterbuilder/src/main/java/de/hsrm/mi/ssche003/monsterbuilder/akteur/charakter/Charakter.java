package de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter;

import java.util.HashSet;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class Charakter extends Akteur{
/*
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private Long id;

    @Min(1) @Max(20)
    private byte level;

    @ManyToOne
    private Gruppe gruppe;

    @ManyToMany
    private HashSet<Skill> skills = new HashSet<>(); */

}
