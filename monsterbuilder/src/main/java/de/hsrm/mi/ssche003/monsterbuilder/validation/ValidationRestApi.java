package de.hsrm.mi.ssche003.monsterbuilder.validation;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/validation")
public class ValidationRestApi {

    @GetMapping("/init")
    public ResponseEntity<Resource> get_holeValidationsInfos() {
        Resource file = new ClassPathResource("/werteValidation.json");
        return ResponseEntity.ok().body(file);
    }
    
}
