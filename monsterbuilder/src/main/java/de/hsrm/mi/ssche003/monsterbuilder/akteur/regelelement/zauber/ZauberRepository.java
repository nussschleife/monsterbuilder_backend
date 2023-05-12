package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.zauber;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ZauberRepository extends JpaRepository<Zauber, Long>{
    @Query("select s from Zauber s where TYPE(s) = Effektzauber")
    Optional<Effektzauber> findFirstEffektzauber();
}
