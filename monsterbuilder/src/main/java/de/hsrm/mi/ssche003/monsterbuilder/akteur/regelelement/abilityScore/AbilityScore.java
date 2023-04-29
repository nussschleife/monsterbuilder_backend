package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Positive;

@Entity(name = "AbilityScore")
public class AbilityScore extends Regelelement{
   
    @Enumerated(EnumType.STRING)
    private AbilityScoreName scoreName;

    @Positive
    private int score;

    @ManyToMany(mappedBy = "abilityScores") @JsonIgnore
    private Set<Monster> monster = new HashSet<>();

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

    @JsonIgnore
    public Set<Monster> getMonster() {
        return monster;
    }

    public void setMonster(Set<Monster> akteur) {
        this.monster = akteur;
    }

    public void addMonster(Monster monster) {
        this.monster.add(monster);
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
    
    //Zauber, Angriff usw.
}
 