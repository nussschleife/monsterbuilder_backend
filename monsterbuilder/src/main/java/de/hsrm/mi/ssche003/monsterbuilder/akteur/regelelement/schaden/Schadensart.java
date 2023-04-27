package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.Regelelement;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.Angriff;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Angriffzauber;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;

@Entity @Table(name="Schadensart", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Schadensart extends Regelelement{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore
    private Long id;
    @Version @JsonIgnore
    private Long version;

    @OneToMany(mappedBy = "schadensart") @JsonIgnore
    private Set<Angriffzauber> angriffzauber = new HashSet<>();

    @OneToMany(mappedBy = "schadensart") @JsonIgnore
    private Set<Angriff> angriffe = new HashSet<>();

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
    public Set<Angriffzauber> getAngriffzauber() {
        return angriffzauber;
    }
    public void setAngriffzauber(Set<Angriffzauber> angriffzauber) {
        this.angriffzauber = angriffzauber;
    }

    
}
