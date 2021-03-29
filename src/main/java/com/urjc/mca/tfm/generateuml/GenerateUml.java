package com.urjc.mca.tfm.generateuml;

import com.urjc.mca.tfm.generateuml.model.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.ScriptException;
import java.io.IOException;

@Component
public class GenerateUml {

    @Autowired
    JavaAnalyzerEclipseAST javaAnalyzerEclipseAST;

    @Autowired
    GenerateImage generateImage;

    @Autowired
    CloneRepository cloneRepository;

    @Autowired
    ClassDiagramGenerator classDiagramGenerator;

    public void run(String url, String imageName) throws IOException, NoSuchMethodException, ScriptException {

        Domain domain = javaAnalyzerEclipseAST.run(cloneRepository.clone(url));
        classDiagramGenerator.addDomain(domain);
        generateImage.downloadImage(classDiagramGenerator.print(), imageName + ".svg");
    }
}
