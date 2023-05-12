package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff;

import java.util.Optional;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.RegelelementRepository;

public interface AngriffRepository extends RegelelementRepository<WaffenAngriff>{
    Optional<WaffenAngriff> findFirstByOrderByIdAsc();
}
