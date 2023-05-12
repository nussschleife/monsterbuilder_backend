package de.hsrm.mi.ssche003.monsterbuilder.akteur.api;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.dto.RegelelementInitResponse;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.effekt.Prone;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.regelelementService.RegelelementService;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.sprache.Sprache;

@RestController
@RequestMapping("/api/regelelement")
public class RegelelementRestApi {

    static final Logger logger = org.slf4j.LoggerFactory.getLogger(RegelelementRestApi.class);
    @Autowired RegelelementService regelelementService;
    
    @GetMapping("/init")
    public RegelelementInitResponse getInitialValues() {
        logger.info("INIT REGELELEMENTE");
        RegelelementInitResponse init = new RegelelementInitResponse();
        init.setSprachen(regelelementService.findeAlleNamenVonElement(new Sprache()));
        init.setSchadensarten(regelelementService.findeAlleNamenVonElement(new Schadensart()));
        init.setConditions(regelelementService.findeAlleNamenVonElement(new Prone()));
        return init;
    }
}
