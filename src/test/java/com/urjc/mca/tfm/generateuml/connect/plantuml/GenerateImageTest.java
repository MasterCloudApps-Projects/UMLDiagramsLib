package com.urjc.mca.tfm.generateuml.connect.plantuml;

import com.urjc.mca.tfm.generateuml.model.Domain;
import com.urjc.mca.tfm.generateuml.view.ClassDiagram;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerateImageTest {

    private static final String SRC_MAIN_RESOURCES_IMAGES = "src/main/resources/images/";
    private static final String UNIT = "unit";
    private static final String BASE = "base";
    private static final String DOMAIN = "domain";
    private static final String IMAGE = "image-";
    private static final String SVG = ".svg";
    static List<String> nameList = new ArrayList<>();
    static List<String> pathList = new ArrayList<>();

    @AfterAll
    static void deleteFiles(){
        nameList.forEach(n -> {
            if(Files.exists(Path.of(SRC_MAIN_RESOURCES_IMAGES +n).toAbsolutePath())) {
                try {
                    Files.delete(Path.of(SRC_MAIN_RESOURCES_IMAGES +n).toAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        pathList.forEach(p ->{
            try {
                FileUtils.deleteDirectory(new File(p));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    @DisplayName("Should be create a image with class diagram")
    @Disabled
    void shouldBeCreateAImageWithClassDiagram() throws NoSuchMethodException, ScriptException, IOException {
        Domain domain = new Domain(DOMAIN);
        ClassDiagram classDiagram = new ClassDiagram();

        domain.addUnit(UNIT).addBase(BASE);
        classDiagram.addDomain(domain);
        String imageName = GenerateImage.downloadImage(classDiagram.print());
        nameList.add(imageName);

        assertTrue(Files.exists(Path.of(SRC_MAIN_RESOURCES_IMAGES +imageName).toAbsolutePath()));
    }

    @Disabled
    @Test
    @DisplayName("Should be create a image with class diagram with name param")
    void shouldBeCreateAImageWithClassDiagramWithNameParam() throws NoSuchMethodException, ScriptException, IOException {
        Domain domain = new Domain(DOMAIN);
        ClassDiagram classDiagram = new ClassDiagram();
        String name = IMAGE + new Random().nextInt() + SVG;
        domain.addUnit(UNIT).addBase(BASE);
        classDiagram.addDomain(domain);
        String imageName = GenerateImage.downloadImage(classDiagram.print(), name);
        nameList.add(imageName);

        assertTrue(Files.exists(Path.of(SRC_MAIN_RESOURCES_IMAGES +imageName).toAbsolutePath()));
    }

    @Disabled
    @Test
    @DisplayName("Should be create a image with class diagram with name param and folder")
    void shouldBeCreateAImageWithClassDiagramWithNameParamAndFolder() throws NoSuchMethodException, ScriptException, IOException {
        Domain domain = new Domain(DOMAIN);
        ClassDiagram classDiagram = new ClassDiagram();
        String name = IMAGE + new Random().nextInt() + SVG;
        String folder = SRC_MAIN_RESOURCES_IMAGES.concat("prueba/");
        domain.addUnit(UNIT).addBase(BASE);
        classDiagram.addDomain(domain);
        String imageName = GenerateImage.downloadImage(classDiagram.print(), name, folder);
        nameList.add(imageName);
        pathList.add(folder);

        assertTrue(Files.exists(Path.of(folder+imageName).toAbsolutePath()));
    }
}