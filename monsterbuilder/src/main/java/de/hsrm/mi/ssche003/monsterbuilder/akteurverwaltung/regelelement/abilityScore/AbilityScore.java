package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.abilityScore;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.Regelelement;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Positive;

@Entity(name = "AbilityScore")
public class AbilityScore extends Regelelement{
   
    @Enumerated(EnumType.STRING)
    private AbilityScoreName scoreName;

    @Positive
    private int score;

    @ManyToMany(mappedBy = "abilityScores") @JsonIgnore
    private Set<Akteur> alleAkteure = new HashSet<>();

    public AbilityScoreName getScoreName() {
        return scoreName;
    }

    public void setScoreName(AbilityScoreName name) {
        this.scoreName = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

   

    @Override @JsonIgnore
    public AbilityScore getInstance() {
        return new AbilityScore();
    }

    @Override
    public AbilityScore übernehmeBasisWerteVon(Regelelement element) {
        if(element instanceof AbilityScore) {
            this.score = ((AbilityScore) element).getScore();
            this.setName(element.getName());
            this.setScoreName(((AbilityScore) element).getScoreName());
        }

        return this;
    }

    public Set<Akteur> getAlleAkteure() {
        return alleAkteure;
    }

    public void setAlleAkteure(Set<Akteur> alleAkteure) {
        this.alleAkteure = alleAkteure;
    }

    public void addAkteur(Akteur akteur) {
        this.alleAkteure.add(akteur);
    }
    
}
 