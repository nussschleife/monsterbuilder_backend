package de.hsrm.mi.ssche003.monsterbuilder.akteur;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

public class AbilityScore {
    @Id @GeneratedValue
    private Long id;
    @Version
    private Long version;
    @Positive @Max(30)
    private byte charisma;
    @Positive @Max(30)
    private byte dexterity;
    @Positive @Max(30)
    private byte constitution;
    @Positive @Max(30)
    private byte strength;
    @Positive @Max(30)
    private byte wisdom;
    @Positive @Max(30)
    private byte intelligence;

    @OneToOne
    private Akteur akteur;
}
 