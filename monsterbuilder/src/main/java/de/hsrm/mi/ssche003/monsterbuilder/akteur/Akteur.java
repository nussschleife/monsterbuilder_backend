package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScore;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.WaffenAngriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.condition.Condition;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow.SavingThrow;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Wuerfel;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Zauber;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Akteur {
    @jakarta.persistence.Id @GeneratedValue(strategy = GenerationType.TABLE)
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

    @Transient @JsonIgnore
    protected List<Condition> conditions = new ArrayList<>();

    @ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
        name = "akteur_sprache",
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "sprache_id"))
    private Set<Sprache> sprachen = new HashSet<>();
    
    @ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
        name = "akteur_zauber", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "zauber_id"))
    protected Set<Zauber> alleZauber = new HashSet<>();

    @ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
        name = "akteur_angriff", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "angriff_id"))
    protected Set<WaffenAngriff> alleAngriffe = new HashSet<>();
    
    @ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}) 
    @JoinTable(
        name = "akteur_score", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "score_id"))
    protected Set<AbilityScore> abilityScores = new HashSet<>(); 

    @ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
        name = "akteur_save", 
        joinColumns = @JoinColumn(name = "akteur_id"), 
        inverseJoinColumns = @JoinColumn(name = "save_id"))
    protected Set<SavingThrow> savingThrows = new HashSet<>(); 

    
    public Long getVersion() {
        return version;
    }


    public void setSprachen(Set<Sprache> sprachen) {
        this.sprachen = sprachen;
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

    public boolean trifftAngriff(int wurf) {
        return wurf >= this.ruestungsklasse;
    }

    public Akteur aktionAusfuehren(AkteurAktion aktion, Akteur gegner) {
       
            Optional<AbilityScore> score = this.abilityScores.stream().filter(abilityScore -> abilityScore.getScoreName() == aktion.getAbilityScoreName()).findFirst();
            aktion.ausfuehren(gegner, score.isPresent() ? score.get().getScore() : 0);
            return gegner;
        
    }

    public void bekommeSchaden(Schadensart art, int anzahl) {
        this.lebenspunkte -= anzahl;
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
        return true;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


    public void setAlleAngriffe(Set<WaffenAngriff> alleAngriffe) {
        this.alleAngriffe = alleAngriffe;
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

    public Set<AbilityScore> getAbilityScores() {
        return abilityScores;
    }

    public void setAbilityScores(Set<AbilityScore> abilityScores) {
        this.abilityScores = abilityScores;
    }


    public void setSavingThrows(Set<SavingThrow> savingThrows) {
        this.savingThrows = savingThrows;
    }

    public void addAngriff(WaffenAngriff angriff) {
        this.alleAngriffe.add(angriff);
    }

    public void addSprache(Sprache sprache) {
        this.sprachen.add(sprache);
    }

    public Akteur addAbilityScore(AbilityScore score) {
        this.abilityScores.add(score);
        return this;
    }


    public boolean macheSavingThrow(SavingThrow save) {
        Optional<SavingThrow> savingThrow = this.savingThrows.stream().filter(s -> s.getTyp() == save.getTyp()).findFirst();
        int mod = savingThrow.isPresent() ? savingThrow.get().getSchwierigkeit() : 0;
        return Wuerfel.W20.wuerfle() + mod > save.getSchwierigkeit(); //Add save mod from akteur
    }

    @JsonIgnore
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


    public Set<Sprache> getSprachen() {
        return sprachen;
    }


    public Set<Zauber> getAlleZauber() {
        return alleZauber;
    }


    public void setAlleZauber(Set<Zauber> alleZauber) {
        this.alleZauber = alleZauber;
    }


    public Set<WaffenAngriff> getAlleAngriffe() {
        return alleAngriffe;
    }


    public Set<SavingThrow> getSavingThrows() {
        return savingThrows;
    }

}
