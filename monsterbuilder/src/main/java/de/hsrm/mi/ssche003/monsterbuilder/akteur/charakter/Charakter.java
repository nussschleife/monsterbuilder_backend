package de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.AkteurAktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.WaffenAngriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow.SavingThrow;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Wuerfel;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Zauber;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity 
public class Charakter extends Akteur{

    public Charakter() {
        super.abilityScores = abilityScores;
        super.alleAngriffe = alleAngriffe;
        super.alleZauber = alleZauber;
        super.setSprachen(sprachen);
        super.conditions = conditions;
        super.savingThrows = savingThrows;
    }

    @ManyToOne @JsonIgnore
    private Gruppe gruppe;

    public Gruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
    }

    @ManyToMany( cascade = CascadeType.MERGE)
    @JoinTable(
        name = "charakter_sprache",
        joinColumns = @JoinColumn(name = "charakter_id"), 
        inverseJoinColumns = @JoinColumn(name = "sprache_id"))
    private Set<Sprache> sprachen = new HashSet<>();
    
    @ManyToMany( cascade = CascadeType.MERGE)
    @JoinTable(
        name = "charakter_zauber", 
        joinColumns = @JoinColumn(name = "charakter_id"), 
        inverseJoinColumns = @JoinColumn(name = "zauber_id"))
    protected Set<Zauber> alleZauber = new HashSet<>();

    @ManyToMany( cascade = CascadeType.MERGE)
    @JoinTable(
        name = "charakter_angriff", 
        joinColumns = @JoinColumn(name = "charakter_id"), 
        inverseJoinColumns = @JoinColumn(name = "angriff_id"))
    protected Set<WaffenAngriff> alleAngriffe = new HashSet<>();
    
    @ManyToMany( cascade = CascadeType.MERGE) 
    @JoinTable(
        name = "charakter_score", 
        joinColumns = @JoinColumn(name = "charakter_id"), 
        inverseJoinColumns = @JoinColumn(name = "score_id"))
    protected Set<AbilityScore> abilityScores = new HashSet<>(); 

    @ManyToMany( cascade = CascadeType.MERGE)
    @JoinTable(
        name = "charakter_save", 
        joinColumns = @JoinColumn(name = "charakter_id"), 
        inverseJoinColumns = @JoinColumn(name = "save_id"))
    protected Set<SavingThrow> savingThrows = new HashSet<>(); 

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((gruppe == null) ? 0 : gruppe.hashCode());
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
        Charakter other = (Charakter) obj;
        if (gruppe == null) {
            if (other.gruppe != null)
                return false;
        } else if (!gruppe.equals(other.gruppe))
            return false;
        return true;
    }

    @Override
    public Akteur aktionAusfuehren(AkteurAktion aktion, Akteur gegner) {
        if(gegner instanceof Monster) {
            Optional<AbilityScore> score = this.abilityScores.stream().filter(abilityScore -> abilityScore.getScoreName() == aktion.getAbilityScoreName()).findFirst();
            aktion.ausfuehren(gegner, score.isPresent() ? score.get().getScore() : 0);
        }
        return gegner;
    }

    @Override
    public Set<Sprache> getSprachen() {
        return sprachen;
    }

    @Override
    public void setSprachen(Set<Sprache> sprachen) {
        this.sprachen = sprachen;
    }

    @Override
    public Set<Zauber> getAlleZauber() {
        return alleZauber;
    }

    @Override
    public void setAlleZauber(Set<Zauber> alleZauber) {
        this.alleZauber = alleZauber;
    }

    @Override
    public Set<WaffenAngriff> getAlleAngriffe() {
        return alleAngriffe;
    }

    @Override
    public void setAlleAngriffe(Set<WaffenAngriff> alleAngriffe) {
        this.alleAngriffe = alleAngriffe;
    }

    @Override
    public Set<AbilityScore> getAbilityScores() {
        return abilityScores;
    }

    @Override
    public void setAbilityScores(Set<AbilityScore> abilityScores) {
        this.abilityScores = abilityScores;
    }

    @Override
    public Set<SavingThrow> getSavingThrows() {
        return savingThrows;
    }

    @Override
    public void setSavingThrows(Set<SavingThrow> savingThrows) {
        this.savingThrows = savingThrows;
    }

    @Override @JsonIgnore
    public List<AkteurAktion> getAlleAktionen() {
         List<AkteurAktion> alleAktionen = new ArrayList<>();
         for(AkteurAktion aktion : this.alleAngriffe) {
             alleAktionen.add(aktion);
         }
 
         for(AkteurAktion aktion : this.alleZauber) {
             alleAktionen.add(aktion);
         }
 
         return alleAktionen;
     }
}
