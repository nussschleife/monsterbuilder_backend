package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.simValue.SimValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;

@Entity
public class Trait implements SimValue {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version @JsonIgnore
    private Long version;

    @Length(min = 0)
    private String name;
    
    private String beschreibung = "default";
    
    @ManyToMany(mappedBy = "alleTraits", fetch = FetchType.EAGER) @JsonIgnore //TODO: nicht eager fetchen müssen, lieber vor verwendung checken ob es geladen ist?
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @JsonIgnore
    public Set<Monster> getAlleMonster() {
        return alleMonster;
    }

    public void setAlleMonster(Set<Monster> alleMonster) {
        this.alleMonster = alleMonster;
    }

    public void addMonster(Monster monster) {
        this.alleMonster.add(monster);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((beschreibung == null) ? 0 : beschreibung.hashCode());
        result = prime * result + ((alleMonster == null) ? 0 : alleMonster.hashCode());
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
        Trait other = (Trait) obj;
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (beschreibung == null) {
            if (other.beschreibung != null)
                return false;
        } else if (!beschreibung.equals(other.beschreibung))
            return false;
        if (alleMonster == null) {
            if (other.alleMonster != null)
                return false;
        } else if (!alleMonster.equals(other.alleMonster))
            return false;
        return true;
    }

    //TODO: elementverträglichkeit, ability usw.

    
}
