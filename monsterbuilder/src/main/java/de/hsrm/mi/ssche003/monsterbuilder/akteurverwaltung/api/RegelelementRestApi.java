package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.api;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.dto.RegelelementInitResponse;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.condition.Prone;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.regelelementService.RegelelementService;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.schaden.Schadensart;
import de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.sprache.Sprache;

@RestController
@RequestMapping("/api/regelelement")
public class RegelelementRestApi {

    static final Logger logger = org.slf4j.LoggerFactory.getLogger(RegelelementRestApi.class);
    @Autowired RegelelementService regelelementService;
    
    @GetMapping("/init")
    public RegelelementInitResponse getInitialValues() {
        logger.info("INIT REGELELEMENTE");
        RegelelementInitResponse init = new RegelelementInitResponse();
        init.setSprachen(regelelementService.findeAlleVonTyp(new Sprache()));
        init.setSchadensarten(regelelementService.findeAlleVonTyp(new Schadensart()));
        init.setConditions(regelelementService.findeAlleNamenVonElement(new Prone()));
        return init;
    }
}
