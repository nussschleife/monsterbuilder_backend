package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegelelementRepository <T extends Regelelement> extends JpaRepository<T, Long> {

    Optional<T> findById(long id);
    List<T> findAll();
    
}
