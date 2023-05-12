package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.AggressiveAktion;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.effekt.Condition;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow.SavingThrow;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity @DiscriminatorValue("EFFEKT")
public class Effektzauber extends Zauber implements AggressiveAktion {
    @ManyToOne
    Condition condition;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "zauber_save", referencedColumnName = "id")
    SavingThrow save;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public SavingThrow getSave() {
        return save;
    }

    public void setSave(SavingThrow save) {
        this.save = save;
    }

    @Override
    public Akteur ausfuehren(Akteur gegner, int modifikator) {
        //modifikator beeinflusst save dc
        this.save.setSchwierigkeit(this.save.getSchwierigkeit() + modifikator);
        if(!gegner.macheSavingThrow(save)) {
            condition.wirkeCondition(gegner);
            gegner.addCondition(condition);
        }
        this.save.setSchwierigkeit(this.save.getSchwierigkeit() - modifikator);
        return gegner;
    }

}
