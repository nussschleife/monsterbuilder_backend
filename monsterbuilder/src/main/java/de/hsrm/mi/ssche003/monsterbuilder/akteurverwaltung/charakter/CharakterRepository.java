package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharakterRepository extends JpaRepository<Charakter, Long>{
    Optional<Charakter> findById(Long id);
    Optional<Charakter> findByName(String name);
}
