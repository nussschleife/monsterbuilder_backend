package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster.trait;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TraitRepository extends JpaRepository<Trait, Long>{
    
    Optional<Trait> findByName(String name);

    Optional<Trait> findById(Long id);

    List<Trait> findAll();

}
