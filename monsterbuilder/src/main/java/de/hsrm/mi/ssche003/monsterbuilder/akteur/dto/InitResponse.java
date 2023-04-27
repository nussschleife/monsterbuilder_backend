package de.hsrm.mi.ssche003.monsterbuilder.akteur.dto;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.Trait;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScoreName;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Wuerfel;

public class InitResponse {
    private String[] sprachen;
    private Alignment[] alleAlignments;
    private Trait[] alleTraits;
    private AbilityScoreName[] abilityScoreNamen;
    private String[] schadensarten;
    private Wuerfel[] wuerfel;

    public InitResponse() {
        alleAlignments = Alignment.values();
        abilityScoreNamen = AbilityScoreName.values();
        wuerfel = Wuerfel.values();
    }

    public String[] getSprachen() {
        return sprachen;
    }
    public void setSprachen(String[] sprachen) {
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

    public AbilityScoreName[] getAbilityScoreNamen() {
        return abilityScoreNamen;
    }

    public void setAbilityScoreNamen(AbilityScoreName[] abilityScoreNames) {
        this.abilityScoreNamen = abilityScoreNames;
    }

    public String[] getSchadensarten() {
        return schadensarten;
    }

    public void setSchadensarten(String[] schadensarten) {
        this.schadensarten = schadensarten;
    }

    public Wuerfel[] getWuerfel() {
        return wuerfel;
    }

    public void setWuerfel(Wuerfel[] wuerfel) {
        this.wuerfel = wuerfel;
    }
    
}
