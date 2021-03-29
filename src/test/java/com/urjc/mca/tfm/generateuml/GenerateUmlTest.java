package com.urjc.mca.tfm.generateuml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.script.ScriptException;
import java.io.IOException;

@SpringBootTest(classes = GenerateUml.class)
@ComponentScan(basePackages = {"com.urjc.mca.tfm.generateuml"})
@EnableAutoConfiguration
class GenerateUmlTest {

    @Autowired
    GenerateUml generateUml;

    @Test
    @DisplayName("mastermind with Composite")
    void mastermindWithComposite() throws NoSuchMethodException, ScriptException, IOException {
        generateUml.run("https://github.com/pjcalvo84/mastermindWithComposite.git", "mastermind with composite");
    }

    @Test
    @DisplayName("mastermind with domain model")
    void mastermindWithDomainModel() throws NoSuchMethodException, ScriptException, IOException {
        generateUml.run("https://github.com/pjcalvo84/mastermaindDomainModel.git", "mastermind domain model");
    }
}