package com.urjc.mca.tfm.generateuml;

import com.urjc.mca.tfm.generateuml.model.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenerateumlApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(GenerateumlApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
//        SpringApplication.run(SpringBootConsoleApplication.class, args);
        SpringApplication.run(GenerateumlApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");

        Entity entity = new Entity("Padre");
        entity.addBase(new Entity("Abuelo"));
        entity.addPart(new Entity("Hijo"));
        entity.toString();
    }
}
