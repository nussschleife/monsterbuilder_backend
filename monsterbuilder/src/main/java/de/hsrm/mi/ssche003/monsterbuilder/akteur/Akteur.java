package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.Angriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow.SavingThrow;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Zauber;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@MappedSuperclass	
public class Akteur {
    @jakarta.persistence.Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Version @JsonIgnore
    private Long version;
    @NotNull @NotEmpty
    private String name;
    @PositiveOrZero
    private int lebenspunkte;
    @Positive
    private byte ruestungsklasse;
    @PositiveOrZero //TODO: modulo 5 - custom validator @ValidMovement
    private byte geschwindigkeit_ft; //Geschwindigkeit in 5-Fuß Abstufungen

    @Enumerated(EnumType.STRING)
    private Alignment alignment;

    protected int level;

    @ManyToMany
    @JoinTable(
        name = "akteur_sprache", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "sprache_id"))
    private Set<Sprache> sprachen = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "akteur_zauber", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "zauber_id"))
    protected Set<Zauber> alleZauber = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "akteur_angriff", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "angriff_id"))
    protected Set<Angriff> alleAngriffe = new HashSet<>();
    
    @ManyToMany //TODO: prüfen dass jedes im enum einmal da ist und nur einmal
    @JoinTable(
        name = "akteur_score", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "score_id"))
    protected Set<AbilityScore> abilityScores = new HashSet<>(); 
    

    
    public Set<Zauber> getAlleZauber() {
        return alleZauber;
    }

    public void setAlleZauber(Set<Zauber> alleZauber) {
        this.alleZauber = alleZauber;
    }

    public Set<Angriff> getAlleAngriffe() {
        return alleAngriffe;
    }

    public void setAlleAngriffe(Set<Angriff> alleAngriffe) {
        this.alleAngriffe = alleAngriffe;
    }

    public void addAngriff(Angriff angriff) {
        this.alleAngriffe.add(angriff);
    }

    public Long getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public int getLebenspunkte() {
        return lebenspunkte;
    }

    public byte getRuestungsklasse() {
        return ruestungsklasse;
    }

    public byte getGeschwindigkeit_ft() {
        return geschwindigkeit_ft;
    }

    public Set<Sprache> getSprachen() {
        return sprachen;
    }

    public Set<AbilityScore> getAbilityScores() {
        return abilityScores;
    }

    public Akteur setName(String name) {
        this.name = name;
        return this;
    }

    public Akteur setLebenspunkte(int lebenspunkte) {
        this.lebenspunkte = lebenspunkte;
        return this;
    }

    public Akteur setRuestungsklasse(byte ruestungsklasse) {
        this.ruestungsklasse = ruestungsklasse;
        return this;
    }

    public Akteur setGeschwindigkeit_ft(byte geschwindigkeit_ft) {
        this.geschwindigkeit_ft = geschwindigkeit_ft;
        return this;
    }


    public int wuerfleInitiative() {
        return (int) (Math.random()*20) + 1; //+ini modifier wenn nötig
    }

    public boolean ausweichen(SavingThrow event, int schwierigkeit) {
        return false;
    }


   public void setSprachen(Set<Sprache> sprachen) {
        this.sprachen = sprachen;
    }

    public void addSprache(Sprache sprache) {
        this.sprachen.add(sprache);
    }

    public void setAbilityScores(Set<AbilityScore> abilityScores) {
        this.abilityScores = abilityScores;
    }

    /* public Akteur setZauber(HashSet<Zauber> zauber) {
        this.zauber = zauber;
        return this;
    }
*/
    public Akteur setAbilityScore(Set<AbilityScore> score) {
        this.abilityScores = score;
        return this;
    }

    public Akteur addAbilityScore(AbilityScore score) {
        if(this.abilityScores.contains(score))
            this.abilityScores.remove(score);
        this.abilityScores.add(score);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Id == null) ? 0 : Id.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + lebenspunkte;
        result = prime * result + ruestungsklasse;
        result = prime * result + geschwindigkeit_ft;
        result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
        result = prime * result + level;
        result = prime * result + ((sprachen == null) ? 0 : sprachen.hashCode());
        result = prime * result + ((alleZauber == null) ? 0 : alleZauber.hashCode());
        result = prime * result + ((alleAngriffe == null) ? 0 : alleAngriffe.hashCode());
        result = prime * result + ((abilityScores == null) ? 0 : abilityScores.hashCode());
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
        Akteur other = (Akteur) obj;
        if (Id == null) {
            if (other.Id != null)
                return false;
        } else if (!Id.equals(other.Id))
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
        if (lebenspunkte != other.lebenspunkte)
            return false;
        if (ruestungsklasse != other.ruestungsklasse)
            return false;
        if (geschwindigkeit_ft != other.geschwindigkeit_ft)
            return false;
        if (alignment != other.alignment)
            return false;
        if (level != other.level)
            return false;
        if (sprachen == null) {
            if (other.sprachen != null)
                return false;
        } else if (!sprachen.equals(other.sprachen))
            return false;
        if (alleZauber == null) {
            if (other.alleZauber != null)
                return false;
        } else if (!alleZauber.equals(other.alleZauber))
            return false;
        if (alleAngriffe == null) {
            if (other.alleAngriffe != null)
                return false;
        } else if (!alleAngriffe.equals(other.alleAngriffe))
            return false;
        if (abilityScores == null) {
            if (other.abilityScores != null)
                return false;
        } else if (!abilityScores.equals(other.abilityScores))
            return false;
        return true;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    

}
