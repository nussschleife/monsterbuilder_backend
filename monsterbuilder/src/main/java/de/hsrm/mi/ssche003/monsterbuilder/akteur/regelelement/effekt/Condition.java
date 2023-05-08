package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.effekt;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Effektzauber;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;

@Entity
@DiscriminatorColumn(name="disc", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Condition { 
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Version
    protected Long version;
    protected int dauer;
    protected String name;

    @Transient @JsonIgnore
    protected Set<Akteur> betroffeneAkteure;

    @OneToMany(mappedBy = "condition")
    Set<Effektzauber> zauber = new HashSet<>();

    public void wirkeCondition(Akteur gegner){gegner.addCondition(this);}
    public void beendeCondition(Akteur gegner){gegner.getConditions().remove(this);}
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<Akteur> getBetroffeneAkteure() {
        return betroffeneAkteure;
    }
    public void setBetroffeneAkteure(Set<Akteur> betroffeneAkteure) {
        this.betroffeneAkteure = betroffeneAkteure;
    }
    
  
}
