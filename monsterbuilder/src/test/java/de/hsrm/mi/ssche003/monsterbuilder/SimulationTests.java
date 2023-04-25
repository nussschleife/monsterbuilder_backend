package de.hsrm.mi.ssche003.monsterbuilder;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.hsrm.mi.ssche003.monsterbuilder.akteur.Alignment;
import de.hsrm.mi.ssche003.monsterbuilder.akteur.dto.MonsterDTO;

import org.python.core.Options;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class SimulationTests {

    final int[] AKTIONEN = { 1, 2, 3, 4, 5 };
    static final Logger logger = LoggerFactory.getLogger(SimulationTests.class);
    String name = "Blizzard Arbor";
    int hp = 11;
    byte level = 4;
    byte falschesLevel = -4;
    int falsche_hp = -1;
    byte ac = 18;
    byte falsche_ac = -1;
    byte geschwindigkeit = 30;
    byte falscheGeschwindigeit = -1;

    @Test
    @DisplayName("Jython Engine Integriert, Lese Output aus Python Skript")
    public void testeJython_read() throws Exception {
        Options.importSite = false;
        StringWriter writer = new StringWriter();
        ScriptContext context = new SimpleScriptContext();
        context.setWriter(writer);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("jython");
        engine.eval(new FileReader(resolvePythonScriptPath("src/test/resources/test.py")), context);
        assertEquals("test", writer.toString().trim());
    }

    @Test
    @DisplayName("Jython sende Java Objekt an Python und bekomme Objekt als Antwort zurück")
    public void testeJython_write() throws Exception {
        String path = "src/test/resources/writeJythonTest.py";
        String name = "writeJythonTest.py";
        Options.importSite = false;
        PythonInterpreter interpreter = new PythonInterpreter();
        MonsterDTO monster = new MonsterDTO(name).setAlignment(Alignment.CHAOTIC_EVIL).setGeschwindigkeit_ft(geschwindigkeit).setLebenspunkte(hp).setRuestungsklasse(ac).setId(1l);
        interpreter.execfile(path);
        interpreter.set("monster", monster);
        PyObject someFunc = interpreter.get("calcAction");
        assertTrue(	someFunc != null);
        PyObject aktionPy = someFunc.__call__();
        MonsterDTO aktion = (MonsterDTO) aktionPy.__tojava__(MonsterDTO.class);
        assertTrue(aktion.getName() == "run");
        interpreter.close();
    }

    @Test
    @DisplayName("Aufruf Python Skript mit ProcessBuilder")
    public void read_python_with_processBuilder() throws Exception {
    
        File file = new File("src/test/resources/test.py");
        ProcessBuilder processBuilder = new ProcessBuilder("python", file.getAbsolutePath());
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());

        assertFalse(results.isEmpty());
        assertTrue(results.contains("test"));
        int exitCode = process.waitFor();
        assertEquals(0, exitCode, "No errors should be detected");
    }

    @Test
    @DisplayName("Speichere MonsterDTO als .json Datei und greife in Skript darauf zu")
    public void send_dto_to_script() throws Exception { 
        //das mit in datei speichern ergibt nicht so viel sinn, sonst müsste für jeden Aufruf von jedem Nutzer eine Datei angelegt werden. Vllt doch json direkt senden?

        //MonsterDTO erstellen
        MonsterDTO monster = new MonsterDTO(name).setAlignment(Alignment.CHAOTIC_EVIL).setGeschwindigkeit_ft(geschwindigkeit).setLebenspunkte(hp).setRuestungsklasse(ac).setId(1l);

        //Skript ausführen mit json String als Argument -> geht nicht wegen parser error
        //Json in Datei speichern und auslesen
        File file = new File("monster.json");
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(file, monster );
        ProcessBuilder processBuilder = new ProcessBuilder("python", "src/test/resources/objTest.py", file.getAbsolutePath()); //problem: ist string und wird von python nicht erkannt
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());

        assertFalse(results.isEmpty());
        assertTrue(results.contains("stark"));
        int exitCode = process.waitFor();
        assertEquals(0, exitCode, "No errors should be detected");
    }

@Test
@DisplayName("Schreibe in Python Skript mit ProcessBuilder")
public void write_python_with_processBuilder() throws Exception {

    File file = new File("src/test/resources/writeTest.py");
    String arg = "anobject.json";
    String cmd = "python";
    String[] commands = {arg, cmd};
    ProcessBuilder processBuilder = new ProcessBuilder("python", file.getAbsolutePath(), arg );
    processBuilder.redirectErrorStream(true);

    Process process = processBuilder.start();
    List<String> results = readProcessOutput(process.getInputStream());

    assertFalse(results.isEmpty());
    //parsen von json zu int[], dann assertEquals
    //assertEquals(results.get(0), toJSON(AKTIONEN));
    int exitCode = process.waitFor();
    assertEquals(0, exitCode, "No errors should be detected");
}



    private List<String> readProcessOutput(InputStream inputStream) throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines = new ArrayList<String>();
        reader.lines().forEach((line) -> {
            lines.add(line);
        });
        return lines;
       
    }

    private String resolvePythonScriptPath(String path){
        File file = new File(path);
        return file.getPath();
    }


    public String toJSON(Object test) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(test);
    }
    
}
