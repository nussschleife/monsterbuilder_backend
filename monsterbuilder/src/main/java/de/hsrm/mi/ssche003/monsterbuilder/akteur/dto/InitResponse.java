package de.hsrm.mi.ssche003.monsterbuilder.akteur.dto;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.Sprache;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.trait.Trait;

public class InitResponse {
    private Sprache[] sprachen; //TODO: Sprachen sollen eine Tabelle werden
    private Alignment[] alleAlignments;
    private Trait[] alleTraits;

    public InitResponse() {
        sprachen = Sprache.values();
        alleAlignments = Alignment.values();
    }

    public Sprache[] getSprachen() {
        return sprachen;
    }
    public void setSprachen(Sprache[] sprachen) {
        this.sprachen = sprachen;
    }
    public Alignment[] getAlleAlignments() {
        return alleAlignments;
    }
    public void setAlleAlignments(Alignment[] alleAlignments) {
        this.alleAlignments = alleAlignments;
    }
    public Trait[] getAlleTraits() {
        return alleTraits;
    }
    public void setAlleTraits(Trait[] alleTraits) {
        this.alleTraits = alleTraits;
    }

    
}
