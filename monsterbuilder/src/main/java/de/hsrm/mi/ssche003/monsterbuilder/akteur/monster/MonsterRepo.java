package de.hsrm.mi.ssche003.monsterbuilder.akteur.monster;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MonsterRepo extends JpaRepository<Monster, Long> {
    Optional<Monster> findById(Long id);
}
