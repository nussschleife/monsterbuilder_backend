package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.schaden;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.elementvertraeglichkeit.Elementvertraeglichkeit;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.angriff.Angriff;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity @Table(name="Schadensart", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Schadensart extends Regelelement{

    @OneToMany(mappedBy = "schadensart") @JsonIgnore
    private Set<Angriff> angriffe = new HashSet<>();

    @OneToMany(mappedBy = "schadensart", cascade = CascadeType.ALL) @JsonIgnore
    private Set<Elementvertraeglichkeit> vertraeglichkeiten = new HashSet<>();

    @Override @JsonIgnore
    public Schadensart getInstance() {
        return new Schadensart();
    }
    @Override
    public Schadensart übernehmeBasisWerteVon(Regelelement element) {
        this.setName(element.getName());
        return this;
    }

    
}
