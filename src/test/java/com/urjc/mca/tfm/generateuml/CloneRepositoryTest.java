package com.urjc.mca.tfm.generateuml;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = CloneRepository.class)
class CloneRepositoryTest {

    static String nameRepository;

    @Autowired
    CloneRepository cloneRepository;

    @AfterAll
    static void deleteFiles() {
        try {
            FileUtils.deleteDirectory(new File("src/main/resources/repository/" + nameRepository));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Disabled
    @Test
    @DisplayName("Should be create a repository")
    void shouldBeCreateARepository() throws IOException {
        String url = "https://github.com/pjcalvo84/damas";
//        String url = "https://github.com/pjcalvo84/mastermindWithComposite.git";
        cloneRepository.clone(url);
        String[] list = url.split("/");
        nameRepository = list[list.length - 1];
        assertTrue(Files.exists(Path.of("src/main/resources/repositories/" + nameRepository)));
    }
}