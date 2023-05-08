package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber.Effektzauber;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;

@Entity
public class SavingThrow {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private SaveType typ;
    int schwierigkeit;

    @OneToOne(mappedBy = "save")
    private Effektzauber effektzauber;

    public int getSchwierigkeit() {
        return schwierigkeit;
    }

    public void setSchwierigkeit(int schwierigkeit) {
        this.schwierigkeit = schwierigkeit;
    }

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

    public SaveType getTyp() {
        return typ;
    }

    public void setTyp(SaveType typ) {
        this.typ = typ;
    }

    public Effektzauber getEffektzauber() {
        return effektzauber;
    }

    public void setEffektzauber(Effektzauber effektzauber) {
        this.effektzauber = effektzauber;
    }



    
}
