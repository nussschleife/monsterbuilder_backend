package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore;

import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Positive;

@Entity
public class AbilityScore extends Regelelement{
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private AbilityScoreName scoreName;

    @Positive
    private int score;

    @ManyToMany(mappedBy = "abilityScores")
    private Set<Monster> monster = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<Monster> getMonster() {
        return monster;
    }

    public void setMonster(Set<Monster> akteur) {
        this.monster = akteur;
    }

    public void addMonster(Monster monster) {
        this.monster.add(monster);
    }


    
    //Zauber, Angriff usw.
}
 