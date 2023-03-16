package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import java.util.HashSet;

import de.hsrm.mi.ssche003.monsterbuilder.spielleiter.Spielleiter;
import de.hsrm.mi.ssche003.monsterbuilder.trait.Trait;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;

@Entity
public class Monster extends Akteur{

    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Alignment alignment;
    //TODO: Regex
    private String bildpfad;

    @ManyToOne
    private Spielleiter spielleiter;

    @ManyToMany
    private HashSet<de.hsrm.mi.ssche003.monsterbuilder.trait.Trait> alleTraits = new HashSet<>();
    
}
