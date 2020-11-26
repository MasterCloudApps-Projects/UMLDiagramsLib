package com.urjc.mca.tfm.generateuml.connect.plantuml;

import com.urjc.mca.tfm.generateuml.model.Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class GenerateImage {

    private static final Logger LOG = LoggerFactory.getLogger(Domain.class);

    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static String path;
    private static String name;

    private static void inicialized(){
        path="src/main/resources/images/";
        name="image";
    }
    private static void downloadFilesToEncode() throws IOException {
        try (InputStream in = new URL("https://www.planttext.com/js/rawdeflate.min.js").openStream()) {
            Files.deleteIfExists(Path.of("src/main/resources/js/rawdeflate.min.js").toAbsolutePath());
            Files.copy(in, Paths.get("src/main/resources/js/rawdeflate.min.js").toAbsolutePath());
        } catch (MalformedURLException | FileNotFoundException e) {
            LOG.debug("context",e);
        }

        try (InputStream in = new URL("https://www.planttext.com/js/plantuml.min.js").openStream()) {
            Files.deleteIfExists(Path.of("src/main/resources/js/plantuml.min.js.js").toAbsolutePath());
            Files.copy(in, Paths.get("src/main/resources/js/plantuml.min.js.js").toAbsolutePath());
        } catch (MalformedURLException | FileNotFoundException e) {
            LOG.debug("context", e);
        }
    }

    private static String deflate(String classDiagram) throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get("src/main/resources/js/rawdeflate.min.js").toAbsolutePath(), StandardCharsets.UTF_8));

        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("deflate", classDiagram);

    }

    private static String encode64(String classDiagram) throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get("src/main/resources/js/plantuml.min.js.js").toAbsolutePath(), StandardCharsets.UTF_8));

        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("encode64", classDiagram);
    }

    public static String downloadImage(String classDiagramEncode) throws NoSuchMethodException, ScriptException, IOException {
        return downloadImage(classDiagramEncode, null, null);
    }

    public static String downloadImage(String classDiagramEncode, String name) throws NoSuchMethodException, ScriptException, IOException {
        return downloadImage(classDiagramEncode, name, null);
    }

    public static String downloadImage(String classDiagramEncode, String name, String pathUser) throws NoSuchMethodException, ScriptException, IOException {
        inicialized();
        downloadFilesToEncode();
        checkAndCreatePath(pathUser);
        checkName(name);
        String encodingClassDiagram = encode64(deflate(classDiagramEncode));
        try (InputStream in = new URL("https://www.planttext.com/api/plantuml/svg/" + encodingClassDiagram).openStream()) {
            Files.copy(in, Paths.get(path + name));
        } catch (MalformedURLException e) {
            LOG.debug("context",e);
        } catch (IOException e) {
            LOG.debug("context",e);
        }
        return name;
    }

    private static void checkName(String nameToCheck) {
        if(nameToCheck !=null)
            name = nameToCheck;
        else
            name = generateRandomName();
    }

    private static void checkAndCreatePath(String pathToCheck) throws IOException {
        if(pathToCheck != null){
            path = pathToCheck;
            if(!Files.exists(Path.of(pathToCheck)))
                Files.createDirectory(Path.of(pathToCheck));
        }
    }

    private static String generateRandomName() {
        int number = new SecureRandom().nextInt();
        while (Files.exists(Paths.get("resources/input/images/image" + number + ".svg").toAbsolutePath()))
            number = new SecureRandom().nextInt();
        return "image" + number + ".jpg";
    }
}
