package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.elementvertraeglichkeit.Elementvertraeglichkeit;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.WaffenAngriff;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity @Table(name="Schadensart", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Schadensart extends Regelelement{

    @OneToMany(mappedBy = "schadensart") @JsonIgnore
    private Set<WaffenAngriff> angriffe = new HashSet<>();

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
