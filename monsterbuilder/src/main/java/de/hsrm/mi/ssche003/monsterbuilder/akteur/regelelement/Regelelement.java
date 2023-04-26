package de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Regelelement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 0)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
