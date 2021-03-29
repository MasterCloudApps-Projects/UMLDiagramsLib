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

    public void run(String url, String imageName) throws IOException, NoSuchMethodException, ScriptException {

        ClassDiagramGenerator classDiagramGenerator = new ClassDiagramGenerator();
        Domain domain = javaAnalyzerEclipseAST.run(CloneRepository.clone(url));
        classDiagramGenerator.addDomain(domain);
        generateImage.downloadImage(classDiagramGenerator.print(), imageName + ".svg");
    }
}
