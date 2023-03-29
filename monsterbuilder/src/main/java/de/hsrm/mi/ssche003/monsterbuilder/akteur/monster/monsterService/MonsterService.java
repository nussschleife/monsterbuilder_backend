package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.monsterService;

import java.util.List;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.monster.Monster;
import de.hsrm.mi.ssche003.monsterbuilder.exception.MonsterServiceException;
import de.hsrm.mi.ssche003.monsterbuilder.model.MonsterDTO;

public interface MonsterService {
    public Monster findeMonsterMitId(Long id) throws MonsterServiceException;

    public void deleteMonsterMitId(Long id);

    public Monster editMonster(MonsterDTO monster) throws MonsterServiceException;

    public List<Monster> findeAlleMonster();

}
