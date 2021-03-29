package com.urjc.mca.tfm.generateuml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class CloneRepository {

    private final Logger logger = LoggerFactory.getLogger(CloneRepository.class);

    @Value("${repositories.folder}")
    private String repositoryFolder;

    public String clone(String url) throws IOException {
        try {
            Path directory = Paths.get(repositoryFolder);
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }
            runCommand(directory, "git", "clone", url);
        } catch (MalformedURLException | InterruptedException e) {
            logger.debug("context", e);
            Thread.currentThread().interrupt();
        }
        return obtainNewPath(url);
    }

    private String obtainNewPath(String url){
        String[] aux = url.split("/");
        return (repositoryFolder + aux[aux.length-1].substring(0, aux[aux.length-1].length()-4)+"/src/main/java");
    }

    private void runCommand(Path directory, String... command) throws IOException, InterruptedException {
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

    private class StreamGobbler extends Thread {

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
                    logger.info("{} > {}", type, line);
                }
            } catch (IOException ioe) {
                logger.debug("context", ioe);
            }
        }
    }
}
