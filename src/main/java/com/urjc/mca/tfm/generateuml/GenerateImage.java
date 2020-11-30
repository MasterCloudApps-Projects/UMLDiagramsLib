package com.urjc.mca.tfm.generateuml;

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
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

public class GenerateImage {

    private static final Logger LOG = LoggerFactory.getLogger(GenerateImage.class);
    private static Properties props = new Properties();

    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static String path;
    private static String jsPath;
    private static String name;
    private static String rawdeflate;
    private static String plantumlJs;
    private static String urlJs;
    private static String urlApi;
    private static String url;
    private static String format;


    private static void loadFromPropeties(){
        try(InputStream configStream =GenerateImage.class.getResourceAsStream( "/application.properties")){
            props.load(configStream);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void inicialized(){
        loadFromPropeties();
        path=props.getProperty("img.folder");
        jsPath=props.getProperty("js.folder");
        name=props.getProperty("img.name.default");
        rawdeflate=props.getProperty("rawdeflate.file");
        plantumlJs=props.getProperty("plantuml.file");
        urlJs=props.getProperty("plantuml.url.js");
        urlApi=props.getProperty("plantuml.url.api");
        url=props.getProperty("plantuml.url");
        format=props.getProperty("plantuml.url.format.image");
    }
    private static void downloadFilesToEncode() throws IOException {

        try (InputStream in = new URL(createPath(url,urlJs,rawdeflate)).openStream()) {
            Files.deleteIfExists(Path.of(createPath(jsPath,rawdeflate)).toAbsolutePath());
            Files.copy(in, Paths.get(createPath(jsPath,rawdeflate)).toAbsolutePath());
        } catch (MalformedURLException | FileNotFoundException e) {
            LOG.debug("context",e);
        }

        try (InputStream in = new URL(createPath(url, urlJs,plantumlJs)).openStream()) {
            Files.deleteIfExists(Path.of(createPath(jsPath,plantumlJs)).toAbsolutePath());
            Files.copy(in, Paths.get(createPath(jsPath,plantumlJs)).toAbsolutePath());
        } catch (MalformedURLException | FileNotFoundException e) {
            LOG.debug("context", e);
        }
    }

    private static String deflate(String classDiagram) throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get(createPath(jsPath,rawdeflate)).toAbsolutePath(), StandardCharsets.UTF_8));

        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("deflate", classDiagram);

    }

    private static String encode64(String classDiagram) throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get(createPath(jsPath,plantumlJs)).toAbsolutePath(), StandardCharsets.UTF_8));

        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("encode64", classDiagram);
    }

    public static String downloadImage(String classDiagramEncode) throws NoSuchMethodException, ScriptException, IOException {
        return downloadImage(classDiagramEncode, null, null);
    }

    public static String downloadImage(String classDiagramEncode, String name) throws NoSuchMethodException, ScriptException, IOException {
        return downloadImage(classDiagramEncode, name, null);
    }

    public static String downloadImage(String classDiagramEncode, String nameUser, String pathUser) throws NoSuchMethodException, ScriptException, IOException {
        inicialized();
        downloadFilesToEncode();
        checkAndCreatePath(pathUser);
        checkName(nameUser);
        String encodingClassDiagram = encode64(deflate(classDiagramEncode));
        try (InputStream in = new URL(createPath(url,urlApi,format,"/") + encodingClassDiagram).openStream()) {
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
        while (Files.exists(Paths.get(createPath(path, name) + number + "." + format).toAbsolutePath()))
            number = new SecureRandom().nextInt();
        return name + number + "." + format;
    }

    private static String createPath(String... args){
        return Arrays.stream(args).collect(Collectors.joining());
    }
}
