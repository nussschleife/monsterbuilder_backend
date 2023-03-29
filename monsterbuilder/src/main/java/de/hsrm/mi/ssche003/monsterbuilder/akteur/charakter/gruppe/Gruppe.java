package de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe;

import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.nutzer.Spielleiter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.Valid;

public class Gruppe {

    @Id @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @OneToMany(mappedBy = "gruppe")
    private Set<de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter> alleCharaktere = new HashSet<>();

    @ManyToOne
    private @Valid Spielleiter spielleiter;
    
}
