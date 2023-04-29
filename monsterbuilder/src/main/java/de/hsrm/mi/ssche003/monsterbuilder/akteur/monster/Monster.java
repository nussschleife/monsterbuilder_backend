package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster;

import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.Trait;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

@Entity
public class Monster extends Akteur{

    //TODO: Regex oder custom validator
    private String bildpfad;

    @ManyToMany
    @JoinTable(
        name = "monster_trait", 
        joinColumns = @JoinColumn(name = "monster_id"), 
        inverseJoinColumns = @JoinColumn(name = "trait_id"))
    private Set<Trait> alleTraits = new HashSet<>();    

    /*@ManyToOne
    private Spielleiter spielleiter;

    @ManyToMany
    private HashSet<Skill> skills = new HashSet<>();*/

    /*@Transient
    private ArrayList<Schadensart> weaknesses = new ArrayList<>(); */


    public String getBildpfad() {
        return bildpfad;
    }

   /* public Spielleiter getSpielleiter() {
        return spielleiter;
    }*/

    public Set<Trait> getAlleTraits() {
        return alleTraits;
    }
    /*
    public HashSet<Skill> getSkills() {
        return skills;
    }*/


    public void setAlleTraits(Set<Trait> alleTraits) {
        this.alleTraits = alleTraits;
    }

    public void addTrait(Trait trait) {
        this.alleTraits.add(trait);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((bildpfad == null) ? 0 : bildpfad.hashCode());
        result = prime * result + ((alleTraits == null) ? 0 : alleTraits.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Monster other = (Monster) obj;
        if (bildpfad == null) {
            if (other.bildpfad != null)
                return false;
        } else if (!bildpfad.equals(other.bildpfad))
            return false;
        if (alleTraits == null) {
            if (other.alleTraits != null)
                return false;
        } else if (!alleTraits.equals(other.alleTraits))
            return false;
        return true;
    }

    @Override 
    public void setLevel(int level) {
        this.level = level;
    }
    
}
