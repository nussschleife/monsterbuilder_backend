package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber;

import java.util.HashSet;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import jakarta.persistence.ManyToMany;
import jakarta.validation.Valid;

public class Zauber {
    private byte level;
    private String name;
    private String beschreibung;
    private byte reichweite;
    
    @ManyToMany(mappedBy = "zauber")
    private HashSet<@Valid Akteur> akteure = new HashSet<>();
}
