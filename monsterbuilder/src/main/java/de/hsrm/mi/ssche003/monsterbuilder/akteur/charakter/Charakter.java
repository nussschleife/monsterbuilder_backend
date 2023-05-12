package de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter;


import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Akteur;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.gruppe.Gruppe;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff.AggressiveAktion;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Charakter extends Akteur{

    //Feat, class, background, ancestry

    @ManyToOne @JsonIgnore
    private Gruppe gruppe;

    public Gruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((gruppe == null) ? 0 : gruppe.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Charakter other = (Charakter) obj;
        if (gruppe == null) {
            if (other.gruppe != null)
                return false;
        } else if (!gruppe.equals(other.gruppe))
            return false;
        return true;
    }

    @Override
    public Akteur angriffAusfuehren(AggressiveAktion angriff, Akteur gegner) {
        if(gegner instanceof Monster) {
            super.angriffAusfuehren(angriff, gegner);
        }
        return gegner;
    }


}
