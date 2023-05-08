package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.WaffenAngriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.effekt.Condition;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.AggressiveAktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow.SavingThrow;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Wuerfel;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Effektzauber;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Zauber;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
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
    private int ruestungsklasse;
    @PositiveOrZero //TODO: modulo 5 - custom validator @ValidMovement
    private int geschwindigkeit_ft; //Geschwindigkeit in 5-Fuß Abstufungen

    @Enumerated(EnumType.STRING)
    private Alignment alignment;

    protected int level;

    @ManyToMany( cascade = CascadeType.MERGE)
    @JoinTable(
        name = "akteur_sprache",
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "sprache_id"))
    private Set<Sprache> sprachen = new HashSet<>();
    
    @ManyToMany( cascade = CascadeType.MERGE)
    @JoinTable(
        name = "akteur_zauber", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "zauber_id"))
    protected Set<Zauber> alleZauber = new HashSet<>();

    @ManyToMany( cascade = CascadeType.MERGE)
    @JoinTable(
        name = "akteur_angriff", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "angriff_id"))
    protected Set<WaffenAngriff> alleAngriffe = new HashSet<>();
    
    @ManyToMany( cascade = CascadeType.MERGE) //TODO: prüfen dass jedes im enum einmal da ist und nur einmal
    @JoinTable(
        name = "akteur_score", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "score_id"))
    protected Set<AbilityScore> abilityScores = new HashSet<>(); 

    @Transient @JsonIgnore
    protected List<Condition> conditions = new ArrayList<>();

    
    
    public Set<Zauber> getAlleZauber() {
        return alleZauber;
    }

    public void setAlleZauber(Set<Zauber> alleZauber) {
        this.alleZauber = alleZauber;
    }

    public Set<WaffenAngriff> getAlleAngriffe() {
        return alleAngriffe;
    }

    public void setAlleAngriffe(Set<WaffenAngriff> alleAngriffe) {
        this.alleAngriffe = alleAngriffe;
    }

    public void addAngriff(WaffenAngriff angriff) {
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

    public int getRuestungsklasse() {
        return ruestungsklasse;
    }

    public int getGeschwindigkeit_ft() {
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

    public Akteur setRuestungsklasse(int ruestungsklasse) {
        this.ruestungsklasse = ruestungsklasse;
        return this;
    }

    public Akteur setGeschwindigkeit_ft(int geschwindigkeit_ft) {
        this.geschwindigkeit_ft = geschwindigkeit_ft;
        return this;
    }

    public boolean macheSavingThrow(SavingThrow save) {
        return Wuerfel.W20.wuerfle() > save.getSchwierigkeit(); //Add save mod from akteur
    }

    public boolean trifftAngriff(int wurf) {
        return wurf >= this.ruestungsklasse;
    }

    public Akteur zaubern(Zauber zauber, Akteur gegner) {
        //modifikator suchen
        int wurf = Wuerfel.W20.wuerfle();
        Optional<AbilityScore> score = this.abilityScores.stream().filter(abilityScore -> abilityScore.getScoreName() == zauber.getAbilityScoreName()).findFirst();
          
        if(score.isPresent())
            wurf += score.get().getScore();
        //Gegner verwunden
        if(gegner.trifftAngriff(wurf)) {
            if(zauber instanceof Effektzauber) {
                // gegner.setzeEffekt(((Effektzauber)zauber).getEffekt());
                 //effekt.wirke();
            }
        }
            
        return gegner;
    }

    public Akteur angriffAusfuehren(AggressiveAktion aktion, Akteur gegner) {
        Optional<AbilityScore> score = this.abilityScores.stream().filter(abilityScore -> abilityScore.getScoreName() == aktion.getAbilityScoreName()).findFirst();
        aktion.ausfuehren(gegner, score.isPresent() ? score.get().getScore() : 0);
        return gegner;
    }

    public void bekommeSchaden(Schadensart art, int anzahl) {
        this.lebenspunkte -= anzahl;
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

    public int wuerfleInitiative() {
        return (int) Wuerfel.W20.wuerfle();
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

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(Condition condition) {
        this.conditions.add(condition);
    }

}
