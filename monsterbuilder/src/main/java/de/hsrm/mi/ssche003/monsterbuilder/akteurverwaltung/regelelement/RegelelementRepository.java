package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RegelelementRepository <T extends Regelelement> extends JpaRepository<T, Long> {

    Optional<T> findById(long id);
    List<T> findAll();

    @Query("SELECT DISTINCT r.name FROM #{#entityName} r")
    List<String> findeAlleNamen();

    Optional<T> findByName(String name);
    
}
