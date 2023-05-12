package de.hsrm.mi.ssche003.monsterbuilder.akteur.dto;

import java.util.List;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScoreName;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Wuerfel;

public class RegelelementInitResponse {
    private List<String> conditions;
    private List<String> sprachen;
    private AbilityScoreName[] abilityScoreNamen;
    private List<String> schadensarten;
    private Wuerfel[] wuerfel;
    private Alignment[] alleAlignments;

    public RegelelementInitResponse() {
        alleAlignments = Alignment.values();
        abilityScoreNamen = AbilityScoreName.values();
        wuerfel = Wuerfel.values();
    }

    public List<String> getSprachen() {
        return sprachen;
    }
    public void setSprachen(List<String> sprachen) {
        this.sprachen = sprachen;
    }
    public Alignment[] getAlleAlignments() {
        return alleAlignments;
    }
    public void setAlleAlignments(Alignment[] alleAlignments) {
        this.alleAlignments = alleAlignments;
    }

    public AbilityScoreName[] getAbilityScoreNamen() {
        return abilityScoreNamen;
    }

    public void setAbilityScoreNamen(AbilityScoreName[] abilityScoreNames) {
        this.abilityScoreNamen = abilityScoreNames;
    }

    public List<String> getSchadensarten() {
        return schadensarten;
    }

    public void setSchadensarten(List<String> schadensarten) {
        this.schadensarten = schadensarten;
    }

    public Wuerfel[] getWuerfel() {
        return wuerfel;
    }

    public void setWuerfel(Wuerfel[] wuerfel) {
        this.wuerfel = wuerfel;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }
    
}

