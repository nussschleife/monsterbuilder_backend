package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster;

import java.util.ArrayList;
import java.util.HashSet;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.nutzer.Spielleiter;
import de.hsrm.mi.ssche003.monsterbuilder.validation.IValidator;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.skill.Schadenszauber;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.skill.Skill;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.trait.StatusTrait;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.trait.Trait;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Monster extends Akteur{

    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Alignment alignment;
    //TODO: Regex oder custom validator
    private String bildpfad;

    @Min(-1) @Max(24) 
    private byte level;

    /*@ManyToOne
    private Spielleiter spielleiter;

    @ManyToMany
    private HashSet<Trait> alleTraits = new HashSet<>();

    @ManyToMany
    private HashSet<Skill> skills = new HashSet<>();*/

    @Transient
    private ArrayList<Schadensart> weaknesses = new ArrayList<>(); 
    
    public boolean validiere(IValidator validator) {
        return validator.validiere(this);
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public String getBildpfad() {
        return bildpfad;
    }

   /* public Spielleiter getSpielleiter() {
        return spielleiter;
    }

    public HashSet<Trait> getAlleTraits() {
        return alleTraits;
    }

    public HashSet<Skill> getSkills() {
        return skills;
    }*/

    public Long getId() {
        return id;
    }

    public ArrayList<Schadensart> getWeaknesses() {
        return weaknesses;
    }

    public void addSchadenszauber(Schadenszauber zauber) {
        if(!weaknesses.contains(zauber.getTyp())) {
          //  this.zauber.add(zauber);
        }
    }

    public void addStatusTrait(StatusTrait trait) {
        if(trait.bestimmtSchwäche())
            this.weaknesses.add(trait.getTyp());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
        result = prime * result + ((bildpfad == null) ? 0 : bildpfad.hashCode());
        result = prime * result + level;
       /* result = prime * result + ((spielleiter == null) ? 0 : spielleiter.hashCode());
        result = prime * result + ((alleTraits == null) ? 0 : alleTraits.hashCode());
        result = prime * result + ((skills == null) ? 0 : skills.hashCode());*/
        result = prime * result + ((weaknesses == null) ? 0 : weaknesses.hashCode());
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
        Monster other = (Monster) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (alignment != other.alignment)
            return false;
        if (bildpfad == null) {
            if (other.bildpfad != null)
                return false;
        } else if (!bildpfad.equals(other.bildpfad))
            return false;
        if (level != other.level)
            return false;
       /* if (spielleiter == null) {
            if (other.spielleiter != null)
                return false;
        } else if (!spielleiter.equals(other.spielleiter))
            return false;
        if (alleTraits == null) {
            if (other.alleTraits != null)
                return false;
        } else if (!alleTraits.equals(other.alleTraits))
            return false;
        if (skills == null) {
            if (other.skills != null)
                return false;
        } else if (!skills.equals(other.skills))
            return false; */
        if (weaknesses == null) {
            if (other.weaknesses != null)
                return false;
        } else if (!weaknesses.equals(other.weaknesses))
            return false;
        return true;
    }

    
    
}
