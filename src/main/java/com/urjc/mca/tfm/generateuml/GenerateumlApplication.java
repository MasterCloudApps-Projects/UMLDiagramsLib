package com.urjc.mca.tfm.generateuml;

import org.eclipse.core.commands.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.script.ScriptException;
import java.io.IOException;

@SpringBootApplication
public class GenerateumlApplication implements CommandLineRunner {

    private static Logger log = LoggerFactory
            .getLogger(GenerateumlApplication.class);

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(GenerateumlApplication.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws NoSuchMethodException, ScriptException, IOException, ExecutionException {

        GenerateUml generateUml = new GenerateUml();
        generateUml.run("https://github.com/pjcalvo84/damas.git", "damas");
    }
}
