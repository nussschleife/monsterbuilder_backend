package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster;

import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.elementvertraeglichkeit.Elementvertraeglichkeit;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.Trait;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.AggressiveAktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Monster extends Akteur{

    @ManyToMany
    @JoinTable(
        name = "monster_trait", 
        joinColumns = @JoinColumn(name = "monster_id"), 
        inverseJoinColumns = @JoinColumn(name = "trait_id"))

    private Set<Trait> alleTraits = new HashSet<>();    

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "monster")
    private Set<Elementvertraeglichkeit> elementvertraeglichkeiten = new HashSet<>(); 

    public Set<Trait> getAlleTraits() {
        return alleTraits;
    }

    public void setAlleTraits(Set<Trait> alleTraits) {
        this.alleTraits = alleTraits;
    }

    public void addTrait(Trait trait) {
        this.alleTraits.add(trait);
    }

    @Override
    public void bekommeSchaden(Schadensart art, int anzahl) {
        for (Elementvertraeglichkeit elementvertraeglichkeit : this.elementvertraeglichkeiten) {
            if(elementvertraeglichkeit.getSchadensart() == art) {
                anzahl = elementvertraeglichkeit.berechneSchadenNeu(anzahl);
                break;
            }
        }
        this.setLebenspunkte( this.getLebenspunkte() - anzahl);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
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

    @Override
    public Akteur angriffAusfuehren(AggressiveAktion angriff, Akteur gegner) {
        if(gegner instanceof Charakter) {
            super.angriffAusfuehren(angriff, gegner);
        }
        return gegner;
    }
    
}
