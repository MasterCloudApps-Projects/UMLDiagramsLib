package com.urjc.mca.tfm.generateuml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

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
            if (Files.exists(Paths.get(repositoryFolder + obtainNameNewProject(url)))){
                deleteFolder(Paths.get(repositoryFolder + obtainNameNewProject(url)).toAbsolutePath());
            }
            runCommand(directory, "git", "clone", url);
        } catch (MalformedURLException | InterruptedException e) {
            logger.debug("context", e);
            Thread.currentThread().interrupt();
        }
        return obtainNewPath(url);
    }

    private void deleteFolder(Path path) throws IOException {
        try (Stream<Path> input = Files.walk(path)){
            input.sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
    }
    private String obtainNewPath(String url) {
        return (repositoryFolder + obtainNameNewProject(url) +"/src/main/java");
    }

    private String obtainNameNewProject(String url) {
        String[] aux = url.split("/");
        if(aux[aux.length - 1].substring(aux[aux.length - 1].length()-4).equals(".git"))
            return aux[aux.length - 1].substring(0, aux[aux.length - 1].length() - 4);
        else
            return aux[aux.length - 1];
    }

    private void runCommand(Path directory, String... command) throws IOException, InterruptedException {
        Objects.requireNonNull(directory, "directory");
        if (!Files.exists(directory)) {
            Files.delete(directory);
//            throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
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
