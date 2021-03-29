package com.urjc.mca.tfm.generateuml;

import com.urjc.mca.tfm.generateuml.model.Domain;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {JavaAnalyzerEclipseAST.class, ClassDiagramGenerator.class})
class JavaAnalyzerEclipseASTTest {

    @Autowired
    JavaAnalyzerEclipseAST javaAnalyzerEclipseAST;

    @Autowired
    ClassDiagramGenerator classDiagramGenerator;

    @Test
    @DisplayName("Should be return a associate in domain with eclipse AST")
    void shouldBeReturnAAssociateInDomainWithEclipseAST() throws IOException {

        Domain domain =  javaAnalyzerEclipseAST.run("src/test/java/com/urjc/mca/tfm/generateuml/arqUnit/associate");

        printDomain(domain);
        assertTrue(domain.getUnit("A").getAssociates().contains(domain.getUnit( "B")));
        assertEquals(0, domain.getUnit( "A").getPartList().size());
        assertEquals(0, domain.getUnit("A").getElements().size());
        assertEquals(0, domain.getUnit( "A").getBase().size());
        assertEquals(0, domain.getUnit( "A").getUsed().size());
        assertEquals(1, domain.getUnit( "A").getAssociates().size());
        assertEquals(2, domain.getUnitList().size());

    }

    void printDomain(Domain domain){
        classDiagramGenerator.addDomain(domain);
        System.out.println(classDiagramGenerator.print());
    }

    @Test
    @DisplayName("Should be return a aggregation in domain")
    void shouldBeReturnAAggregationInDomainWithEclipseAST() {
        Domain domain =  javaAnalyzerEclipseAST.run("src/test/java/com/urjc/mca/tfm/generateuml/arqUnit/aggregation");
        printDomain(domain);

        assertTrue(domain.getUnit("A").getElements().contains(domain.getUnit("B")));
        assertEquals(1, domain.getUnit("A").getElements().size());
        assertEquals(0, domain.getUnit("A").getPartList().size());
        //aqui es 0 xq List lo tenemos capado
        assertEquals(0, domain.getUnit("A").getAssociates().size());
        assertEquals(0, domain.getUnit("A").getBase().size());
        assertEquals(0, domain.getUnit("A").getUsed().size());
        assertEquals(2, domain.getUnitList().size());
    }

    @Test
    @DisplayName("Should be return a two bases in domain")
    void shouldBeReturnATwoBasesInDomainWithEclipseAST() {
        Domain domain =  javaAnalyzerEclipseAST.run("src/test/java/com/urjc/mca/tfm/generateuml/arqUnit/base");

        assertTrue(domain.getUnit("A").getBase().contains(domain.getUnit( "B")));
        assertTrue(domain.getUnit( "A").getBase().contains(domain.getUnit("C")));
        assertEquals(2, domain.getUnit( "A").getBase().size());
        assertEquals(0, domain.getUnit(  "A").getPartList().size());
        assertEquals(0, domain.getUnit( "A").getElements().size());
        assertEquals(0, domain.getUnit(  "A").getAssociates().size());
        assertEquals(0, domain.getUnit( "A").getUsed().size());
        assertEquals(3, domain.getUnitList().size());
    }


    @Test
    @DisplayName("Shoulb be return a composition in domain")
    void shoulbBeReturnACompositionInDomainWithEclipseAST() {
        Domain domain =  javaAnalyzerEclipseAST.run("src/test/java/com/urjc/mca/tfm/generateuml/arqUnit/composition");

        assertTrue(domain.getUnit( "A").getPartList().contains(domain.getUnit( "B")));
        assertEquals(3, domain.getUnitList().size());
        assertEquals(1, domain.getUnit( "A").getPartList().size());
        assertEquals(0, domain.getUnit( "A").getAssociates().size());
        assertEquals(0, domain.getUnit( "A").getElements().size());
        assertEquals(0, domain.getUnit( "A").getBase().size());
        assertEquals(1, domain.getUnit( "A").getUsed().size());
    }


    @Test
    @DisplayName("Should be return a dependecy in domain")
    void shouldBeReturnADependecyInDomainWithEclipseAST() {
        Domain domain =  javaAnalyzerEclipseAST.run("src/test/java/com/urjc/mca/tfm/generateuml/arqUnit/dependency");
        printDomain(domain);

        assertTrue(domain.getUnit("A").getUsed().contains(domain.getUnit("B")));
        assertEquals(5, domain.getUnitList().size());
        assertEquals(0, domain.getUnit("A").getPartList().size());
        assertEquals(0, domain.getUnit("A").getAssociates().size());
        assertEquals(0, domain.getUnit("A").getElements().size());
        assertEquals(0, domain.getUnit("A").getBase().size());
        assertEquals(4, domain.getUnit("A").getUsed().size());
    }

    @Disabled
    @Test
    @DisplayName("Should be return all relations")
    void shouldBeReturnAllRelations() {
        Domain domain =  javaAnalyzerEclipseAST.run("src/test/java/com/urjc/mca/tfm/generateuml/eclipse/ast");
        printDomain(domain);

    }
    @Disabled
    @Test
    @DisplayName("Should be return all relations")
    void shouldBeReturnAllRelations2() {
        Domain domain =  javaAnalyzerEclipseAST.run("src/main/java/com/urjc/mca/tfm/generateuml");
        printDomain(domain);

    }

}