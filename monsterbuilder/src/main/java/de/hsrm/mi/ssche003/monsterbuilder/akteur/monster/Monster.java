package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster;

import java.util.HashSet;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.skill.Skill;
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
    private HashSet<Trait> alleTraits = new HashSet<>();

    @ManyToMany
    private HashSet<Skill> skills = new HashSet<>();
    
}
