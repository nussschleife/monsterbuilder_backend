package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterService;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.exception.MonsterServiceException;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.trait.Trait;

public interface MonsterService {
    public Monster findeMonsterMitId(Long id) throws MonsterServiceException;

    public void deleteMonsterMitId(Long id);

    public Monster editMonster(Monster monster) throws MonsterServiceException;

    public List<Monster> findeAlleMonster();

    public List<Trait> findeAlleTraits();

    public Optional<Trait> findeTraitMitNamen(String name);

}
