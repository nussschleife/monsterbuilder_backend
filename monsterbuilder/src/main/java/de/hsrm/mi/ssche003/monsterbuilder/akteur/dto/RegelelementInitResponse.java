package de.hsrm.mi.ssche003.monsterbuilder.akteur.dto;

import java.util.List;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.abilityScore.AbilityScoreName;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.condition.Condition;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.savingThrow.SaveType;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Wuerfel;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;

public class RegelelementInitResponse {
    private List<String> conditions;
    private List<Sprache> sprachen;
    private AbilityScoreName[] abilityScoreNamen;
    private List<Schadensart> schadensarten;
    private Wuerfel[] wuerfel;
    private Alignment[] alleAlignments;
    private SaveType[] savingThrows;

    public RegelelementInitResponse() {
        alleAlignments = Alignment.values();
        abilityScoreNamen = AbilityScoreName.values();
        wuerfel = Wuerfel.values();
        savingThrows = SaveType.values();
    }


    public List<Sprache> getSprachen() {
        return sprachen;
    }
    public void setSprachen(List<Sprache> sprachen) {
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

    public List<Schadensart> getSchadensarten() {
        return schadensarten;
    }

    public void setSchadensarten(List<Schadensart> schadensarten) {
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



    public SaveType[] getSavingThrows() {
        return savingThrows;
    }



    public void setSavingThrows(SaveType[] savingThrows) {
        this.savingThrows = savingThrows;
    }
    
}

