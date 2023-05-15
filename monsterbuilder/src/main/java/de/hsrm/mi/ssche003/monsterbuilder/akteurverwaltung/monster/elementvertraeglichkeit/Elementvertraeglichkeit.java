package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.elementvertraeglichkeit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.schaden.Schadensart;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "disc", discriminatorType = DiscriminatorType.CHAR )
@DiscriminatorValue("e")
public abstract class Elementvertraeglichkeit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @ManyToOne
    private Schadensart schadensart;

    protected int wert;

    @ManyToOne @JsonIgnore
    private Monster monster;

    public int berechneSchadenNeu( int wert) {return wert;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Schadensart getSchadensart() {
        return schadensart;
    }

    public void setSchadensart(Schadensart art) {
        this.schadensart = art;
    }

    public int getWert() {
        return wert;
    }

    public void setWert(int wert) {
        this.wert = wert;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    
}
