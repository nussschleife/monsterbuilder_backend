package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.dto;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.trait.Trait;

public class AkteurInitResponse {

    private Trait[] alleTraits;

    public AkteurInitResponse() {
    }

    public Trait[] getAlleTraits() {
        return alleTraits;
    }
    public void setAlleTraits(Trait[] alleTraits) {
        this.alleTraits = alleTraits;
    }
    
}
