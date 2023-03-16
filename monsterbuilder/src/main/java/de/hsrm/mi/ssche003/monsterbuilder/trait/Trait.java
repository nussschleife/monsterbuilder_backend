package de.hsrm.mi.ssche003.monsterbuilder.trait;

import java.util.HashSet;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.Valid;

@MappedSuperclass
public class Trait {
    
    @Id @GeneratedValue
    private Long id;

    @Version
    private Long version;
    private String name;
    private String beschreibung;
    
    @ManyToMany(mappedBy = "alleTraits")
    private HashSet<de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster> alleMonster;
}
