package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter.gruppe;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GruppeRepository extends JpaRepository<Gruppe, Long>{
    Optional<Gruppe> findById(Long id);
    List<Gruppe> findByName(String name);
}
