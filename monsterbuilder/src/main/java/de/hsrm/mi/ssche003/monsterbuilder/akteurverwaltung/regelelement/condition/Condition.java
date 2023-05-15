package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.condition;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.zauber.Effektzauber;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonDeserialize(using = ConditionDeserializer.class)
public abstract class Condition extends Regelelement{ 
    
    protected int dauer;

    @Transient @JsonIgnore
    protected Set<Akteur> betroffeneAkteure = new HashSet<>();

    @OneToMany(mappedBy = "condition") @JsonIgnore
    Set<Effektzauber> zauber = new HashSet<>();

    public abstract Akteur wirkeCondition(Akteur gegner);
  
    public void beendeCondition(Akteur gegner){gegner.getConditions().remove(this); this.betroffeneAkteure.remove(gegner);}
    public void erhoeheDauer(int runden){this.dauer += runden;}
    public void verringereDauer(int runden){
        this. dauer -= runden; 
        if(dauer <= 0) {
            for(Akteur akteur : betroffeneAkteure) {
                beendeCondition(akteur);
            }
            
        }
    }
    
    public int getDauer() {
        return dauer;
    }
    public void setDauer(int dauer) {
        this.dauer = dauer;
    }
 
    public Set<Akteur> getBetroffeneAkteure() {
        return betroffeneAkteure;
    }
    public void setBetroffeneAkteure(Set<Akteur> betroffeneAkteure) {
        this.betroffeneAkteure = betroffeneAkteure;
    }
    
    @Override @JsonIgnore
    public Regelelement getInstance() {
        return null;
    }

  
}
