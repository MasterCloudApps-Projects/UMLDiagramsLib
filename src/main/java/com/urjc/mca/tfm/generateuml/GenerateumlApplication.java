package com.urjc.mca.tfm.generateuml;

import org.eclipse.core.commands.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import javax.script.ScriptException;
import java.io.IOException;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class GenerateumlApplication implements CommandLineRunner {

    private static Logger log = LoggerFactory
            .getLogger(GenerateumlApplication.class);

    @Autowired
    JavaAnalyzerEclipseAST javaAnalyzerEclipseAST;


    @Autowired
    GenerateUml generateUml;

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(GenerateumlApplication.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws NoSuchMethodException, ScriptException, IOException, ExecutionException {

        if(args.length > 0) {
            generateUml.run(args[0], args[1]);
        }
    }
}
