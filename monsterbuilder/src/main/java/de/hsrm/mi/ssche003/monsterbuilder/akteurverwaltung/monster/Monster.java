package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.AkteurAktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.elementvertraeglichkeit.Elementvertraeglichkeit;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.trait.Trait;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.savingThrow.SavingThrow;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.schaden.Wuerfel;
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "monster")
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
    public Akteur aktionAusfuehren(AkteurAktion aktion, Akteur gegner) {
        if( gegner instanceof Charakter) {
            Optional<AbilityScore> score = this.abilityScores.stream().filter(abilityScore -> abilityScore.getScoreName() == aktion.getAbilityScoreName()).findFirst();
            aktion.ausfuehren(gegner, score.isPresent() ? score.get().getScore() : 0);
        }
        
        return gegner;
    }

    public Set<Elementvertraeglichkeit> getElementvertraeglichkeiten() {
        return elementvertraeglichkeiten;
    }

    public void setElementvertraeglichkeiten(Set<Elementvertraeglichkeit> elementvertraeglichkeiten) {
        this.elementvertraeglichkeiten = elementvertraeglichkeiten;
    }


    @Override
    public boolean macheSavingThrow(SavingThrow save) {
        Optional<SavingThrow> savingThrow = this.savingThrows.stream().filter(s -> s.getTyp() == save.getTyp()).findFirst();
        int mod = savingThrow.isPresent() ? savingThrow.get().getSchwierigkeit() : 0;
        return Wuerfel.W20.wuerfle() + mod > save.getSchwierigkeit(); //Add save mod from akteur
    }

}
