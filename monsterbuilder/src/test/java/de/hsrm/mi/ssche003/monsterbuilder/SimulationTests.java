package de.hsrm.mi.ssche003.monsterbuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
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

import org.python.core.Options;

public class SimulationTests {

    final int[] AKTIONEN = { 1, 2, 3, 4, 5 };
    final TestDTO TEST = new TestDTO(AKTIONEN);
    static final Logger logger = LoggerFactory.getLogger(SimulationTests.class);
    private class TestDTO {
        private int[] aktionsnummern;

        TestDTO(int[] aktionen) {
            this.aktionsnummern = aktionen;
        }

        public int[] getAktionsnummern() {
            return aktionsnummern;
        }

        public void setAktionsnummern(int[] aktionsnummern) {
            this.aktionsnummern = aktionsnummern;
        }

    }

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
    @DisplayName("Schreibe in Python Skript und erhalte entsprechende Antwort")
    public void testeJython_write() throws Exception {
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
