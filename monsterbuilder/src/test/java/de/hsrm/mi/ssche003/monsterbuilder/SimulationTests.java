package de.hsrm.mi.ssche003.monsterbuilder;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.List;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.python.core.Options;

public class SimulationTests {

    static final Logger logger = LoggerFactory.getLogger(SimulationTests.class);

    @Test
    @DisplayName("Jython Engine Integriert")
    public void givenPythonScriptEngineIsAvailable_whenScriptInvoked_thenOutputDisplayed() throws Exception {
        Options.importSite = false;
        StringWriter writer = new StringWriter();
        ScriptContext context = new SimpleScriptContext();
        context.setWriter(writer);

        ScriptEngineManager manager = new ScriptEngineManager();
    
        ScriptEngine engine = manager.getEngineByName("jython");
        engine.eval(new FileReader(resolvePythonScriptPath("src/main/resources/akteurAktionen.py")), context);
        assertEquals("Hello Baeldung Readers!!", writer.toString().trim());
    }

    private String resolvePythonScriptPath(String path){
        File file = new File(path);
        return file.getPath();
    }
    
}
