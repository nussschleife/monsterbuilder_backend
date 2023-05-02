package de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe;

import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.nutzer.Spielleiter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.Valid;

@Entity
public class Gruppe {

    @Id @GeneratedValue
    private Long id;

    @Version
    private Long version;

    private String name;

    @OneToMany(mappedBy = "gruppe")
    private Set<Charakter> alleCharaktere = new HashSet<>();

    public Set<Charakter> getAllCharaktere() {
        return this.alleCharaktere;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Charakter> getAlleCharaktere() {
        return alleCharaktere;
    }

    public void setAlleCharaktere(Set<Charakter> alleCharaktere) {
        this.alleCharaktere = alleCharaktere;
    }

    
}
