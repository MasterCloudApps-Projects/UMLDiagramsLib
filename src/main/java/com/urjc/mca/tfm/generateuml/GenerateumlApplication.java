package com.urjc.mca.tfm.generateuml;

import com.urjc.mca.tfm.generateuml.model.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenerateumlApplication implements CommandLineRunner {

    private static Logger log = LoggerFactory
            .getLogger(GenerateumlApplication.class);

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
//        SpringApplication.run(SpringBootConsoleApplication.class, args);
        SpringApplication.run(GenerateumlApplication.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("EXECUTING : command line runner");

        Unit entity = new Unit("Padre");
        entity.addBase(new Unit("Abuelo"));
        entity.addPart(new Unit("Hijo"));
        entity.toString();
    }
}
