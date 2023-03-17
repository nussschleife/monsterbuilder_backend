package de.hsrm.mi.ssche003.monsterbuilder.nutzer;

import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;

@Entity
public class Spielleiter {
    
    @OneToMany(mappedBy = "spielleiter", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
    private Set<de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster> alleMonster = new HashSet<>();

    @OneToMany(mappedBy = "spielleiter", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
    private Set<de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe> alleGruppen = new HashSet<>();

    private String passwort;
    
    private String name;
}
