package com.urjc.mca.tfm.generateuml.connect.repository;

import com.urjc.mca.tfm.generateuml.connect.plantuml.GenerateImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class CloneRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CloneRepository.class);
    private static String repositoryFolder;
    private static Properties props = new Properties();

    private static void loadFromPropeties(){
        try(InputStream configStream = GenerateImage.class.getResourceAsStream( "/application.properties")){
            props.load(configStream);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void inicialized() {
        loadFromPropeties();
        repositoryFolder = props.getProperty("repositories.folder");
    }

    public static void clone(String url) throws IOException {
        inicialized();
        try {
            Path directory = Paths.get(repositoryFolder);
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }
            runCommand(directory, "git", "clone", url);
        } catch (MalformedURLException | InterruptedException e) {
            LOG.debug("context", e);
            Thread.currentThread().interrupt();
        }
    }

    public static void runCommand(Path directory, String... command) throws IOException, InterruptedException {
        Objects.requireNonNull(directory, "directory");
        if (!Files.exists(directory)) {
            throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
        }
        ProcessBuilder pb = new ProcessBuilder()
                .command(command)
                .directory(directory.toFile());
        Process p = pb.start();
        StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
        StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
        outputGobbler.start();
        errorGobbler.start();
        int exit = p.waitFor();
        errorGobbler.join();
        outputGobbler.join();
        if (exit != 0) {
            throw new AssertionError(String.format("runCommand returned %d", exit));
        }
    }

    private static class StreamGobbler extends Thread {

        private final InputStream is;
        private final String type;

        private StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(type + "> " + line);
                }
            } catch (IOException ioe) {
                LOG.debug("context", ioe);
            }
        }
    }
}
