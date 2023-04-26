package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;

@Entity
public class Sprache extends Regelelement{ 
   
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private Long id;
    @Version
    private Long version;
    @Length(min = 0)
    private String sprache;

    @ManyToMany(mappedBy = "sprachen") @JsonIgnore
    private Set<Monster> alleMonster = new HashSet<>();
    
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
    public String getSprache() {
        return sprache;
    }
    public void setSprache(String sprache) {
        this.sprache = sprache;
    }
    public Set<Monster> getAlleMonster() {
        return alleMonster;
    }
    public void setAlleMonster(Set<Monster> alleMonster) {
        this.alleMonster = alleMonster;
    }
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((sprache == null) ? 0 : sprache.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sprache other = (Sprache) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        if (sprache == null) {
            if (other.sprache != null)
                return false;
        } else if (!sprache.equals(other.sprache))
            return false;
        return true;
    }

}
