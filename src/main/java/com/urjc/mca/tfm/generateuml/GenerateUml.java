package com.urjc.mca.tfm.generateuml;

import javax.script.ScriptException;
import java.io.IOException;

public class GenerateUml {

    public void run(String url,String imageName) throws IOException, NoSuchMethodException, ScriptException {

        ClassDiagramGenerator classDiagramGenerator = new ClassDiagramGenerator();
        classDiagramGenerator.addDomain(JavaAnalyzerEclipseAST.run(CloneRepository.clone(url)));
        GenerateImage.downloadImage(classDiagramGenerator.print(), imageName + ".svg");
    }
}
