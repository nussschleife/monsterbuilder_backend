package de.hsrm.mi.ssche003.monsterbuilder.skill;

import java.util.HashSet;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter.Charakter;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Skill {

    @ManyToMany(mappedBy = "skills")
    private HashSet<Monster> alleMonster = new HashSet<>();
    @ManyToMany(mappedBy = "skills")
    private HashSet<Charakter> alleCharaktere = new HashSet<>();
    
}
