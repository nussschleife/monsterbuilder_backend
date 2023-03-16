package de.hsrm.mi.ssche003.monsterbuilder.spielleiter;

import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Monster;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;

@Entity
public class Spielleiter {
    
    @OneToMany(mappedBy = "spielleiter", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
    private Set<@Valid Monster> alleMonster = new HashSet<>();

    @OneToMany(mappedBy = "spielleiter", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
    private Set<@Valid Gruppe> alleGruppen = new HashSet<>();

    private String passwort;
    
    private String name;
}
