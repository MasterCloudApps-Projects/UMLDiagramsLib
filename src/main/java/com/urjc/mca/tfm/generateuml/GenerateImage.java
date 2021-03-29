package com.urjc.mca.tfm.generateuml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

@Component
public class GenerateImage {

    private final Logger LOG = LoggerFactory.getLogger(GenerateImage.class);
    private Properties props = new Properties();

    private ScriptEngineManager manager = new ScriptEngineManager();

    @Value("${img.folder}")
    private String path;
    
    @Value("${js.folder}")
    private String jsPath;
    
    @Value("${img.name.default}")
    private String name;
    
    @Value("${rawdeflate.file}")
    private String rawdeflate;
    
    @Value("${plantuml.file}")
    private String plantumlJs;
    
    @Value("${plantuml.url.js}")
    private String urlJs;
    
    @Value("${plantuml.url.api}")
    private String urlApi;
    
    @Value("${plantuml.url}")
    private String url;
    
    @Value("${plantuml.url.format.image}")
    private String format;


    private void loadFromProperties(){
        try(InputStream configStream =GenerateImage.class.getResourceAsStream( "/application.properties")){
            props.load(configStream);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inicialized(){
        loadFromProperties();
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
    private void downloadFilesToEncode() throws IOException {

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

    private String deflate(String classDiagram) throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get(createPath(jsPath,rawdeflate)).toAbsolutePath(), StandardCharsets.UTF_8));

        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("deflate", classDiagram);

    }

    private String encode64(String classDiagram) throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get(createPath(jsPath,plantumlJs)).toAbsolutePath(), StandardCharsets.UTF_8));

        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("encode64", classDiagram);
    }

    public String downloadImage(String classDiagramEncode) throws NoSuchMethodException, ScriptException, IOException {
        return downloadImage(classDiagramEncode, null, null);
    }

    public String downloadImage(String classDiagramEncode, String name) throws NoSuchMethodException, ScriptException, IOException {
        return downloadImage(classDiagramEncode, name, null);
    }

    public String downloadImage(String classDiagramEncode, String nameUser, String pathUser) throws NoSuchMethodException, ScriptException, IOException {
        inicialized();
        downloadFilesToEncode();
        checkAndCreatePath(pathUser);
        checkName(nameUser);
        String encodingClassDiagram = encode64(deflate(classDiagramEncode));
        try (InputStream in = new URL(createPath(url,urlApi,format,"/") + encodingClassDiagram).openStream()) {
            Files.copy(in, Paths.get(path + name));
        } catch (IOException e) {
            LOG.debug("context",e);
        }
        return name;
    }

    private void checkName(String nameToCheck) {
        if(nameToCheck !=null)
            name = nameToCheck;
        else
            name = generateRandomName();
    }

    private void checkAndCreatePath(String pathToCheck) throws IOException {
        if(pathToCheck != null){
            path = pathToCheck;
            if(!Files.exists(Path.of(pathToCheck)))
                Files.createDirectory(Path.of(pathToCheck));
        }
    }

    private String generateRandomName() {
        int number = new SecureRandom().nextInt();
        while (Files.exists(Paths.get(createPath(path, name) + number + "." + format).toAbsolutePath()))
            number = new SecureRandom().nextInt();
        return name + number + "." + format;
    }

    private String createPath(String... args){
        return Arrays.stream(args).collect(Collectors.joining());
    }
}
