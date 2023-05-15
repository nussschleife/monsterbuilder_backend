package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.angriff;

import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.RegelelementRepository;

public interface AngriffRepository extends RegelelementRepository<Angriff>{
    Optional<Angriff> findFirstByOrderByIdAsc();
}
