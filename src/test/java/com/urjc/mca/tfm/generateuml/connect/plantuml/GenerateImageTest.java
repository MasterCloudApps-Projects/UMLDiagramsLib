package com.urjc.mca.tfm.generateuml.connect.plantuml;

import com.urjc.mca.tfm.generateuml.model.Domain;
import com.urjc.mca.tfm.generateuml.view.ClassDiagram;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
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

    static List<String> nameList = new ArrayList<>();
    static List<String> pathList = new ArrayList<>();

    @AfterAll
    static void deleteFiles(){
        nameList.forEach(n -> {
            if(Files.exists(Path.of("resources/input/images/"+n).toAbsolutePath())) {
                try {
                    Files.delete(Path.of("resources/input/images/"+n).toAbsolutePath());
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
    void shouldBeCreateAImageWithClassDiagram() throws NoSuchMethodException, ScriptException, IOException {
        Domain domain = new Domain("domain");
        ClassDiagram classDiagram = new ClassDiagram();

        domain.addUnit("unit").addBase("base");
        classDiagram.addDomain(domain);
        String imageName = GenerateImage.downloadImage(classDiagram.print());
        nameList.add(imageName);

        assertTrue(Files.exists(Path.of("src/main/resources/input/images/"+imageName).toAbsolutePath()));
    }

    @Test
    @DisplayName("Should be create a image with class diagram with name param")
    void shouldBeCreateAImageWithClassDiagramWithNameParam() throws NoSuchMethodException, ScriptException, IOException {
        Domain domain = new Domain("domain");
        ClassDiagram classDiagram = new ClassDiagram();
        String name = "image-" + new Random().nextInt() + ".svg";
        domain.addUnit("unit").addBase("base");
        classDiagram.addDomain(domain);
        String imageName = GenerateImage.downloadImage(classDiagram.print(), name);
        nameList.add(imageName);

        assertTrue(Files.exists(Path.of("src/main/resources/input/images/"+imageName).toAbsolutePath()));
    }

    @Test
    @DisplayName("Should be create a image with class diagram with name param and folder")
    void shouldBeCreateAImageWithClassDiagramWithNameParamAndFolder() throws NoSuchMethodException, ScriptException, IOException {
        Domain domain = new Domain("domain");
        ClassDiagram classDiagram = new ClassDiagram();
        String name = "image-" + new Random().nextInt() + ".svg";
        String folder = "src/main/resources/input/images/prueba/";
        domain.addUnit("unit").addBase("base");
        classDiagram.addDomain(domain);
        String imageName = GenerateImage.downloadImage(classDiagram.print(), name, folder);
        nameList.add(imageName);
        pathList.add(folder);

        assertTrue(Files.exists(Path.of(folder+imageName).toAbsolutePath()));
    }
}