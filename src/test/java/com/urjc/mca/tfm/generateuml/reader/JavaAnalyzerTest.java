package com.urjc.mca.tfm.generateuml.reader;

import com.urjc.mca.tfm.generateuml.JavaAnalyzer;
import com.urjc.mca.tfm.generateuml.JavaAnalyzerEclipseAST;
import com.urjc.mca.tfm.generateuml.model.Domain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaAnalyzerTest {

    private static final String GENERAL_PACKAGE = "com.urjc.mca.tfm.generateuml.arqUnit";
    private static final String ASSOCIATE = ".associate";
    private static  final String AGGREGATION = ".aggregation";
    private static final String BASE = ".base";
    private static final String COMPOSITION = ".composition";
    private static final String DEPENDECY = ".dependency";

    @Test
    @DisplayName("Should be return a associate in domain")
    void shouldBeReturnAAssociateInDomain() {
        String myPackage = GENERAL_PACKAGE + ASSOCIATE;
        JavaAnalyzer processPackage = new JavaAnalyzer(myPackage);

        Domain domain = processPackage.run();
        assertTrue(domain.getUnit(myPackage + ".A").getAssociates().contains(domain.getUnit(myPackage + ".B")));
        assertEquals(0, domain.getUnit(myPackage + ".A").getPartList().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getElements().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getBase().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getUsed().size());
        assertEquals(1, domain.getUnit(myPackage + ".A").getAssociates().size());
        assertEquals(2, domain.getUnitList().size());

    }

    @Test
    @DisplayName("Should be return a associate in domain with eclipse AST")
    void shouldBeReturnAAssociateInDomainWithEclipseAST() throws IOException {
        String myPackage = GENERAL_PACKAGE + ASSOCIATE;

        Domain domain = JavaAnalyzerEclipseAST.run(myPackage.replace(".","/"));
        assertTrue(domain.getUnit("A").getAssociates().contains(domain.getUnit( "B")));
        assertEquals(0, domain.getUnit( "A").getPartList().size());
        assertEquals(0, domain.getUnit("A").getElements().size());
        assertEquals(0, domain.getUnit( "A").getBase().size());
        assertEquals(0, domain.getUnit( "A").getUsed().size());
        assertEquals(1, domain.getUnit( "A").getAssociates().size());
        assertEquals(2, domain.getUnitList().size());

    }
    @Test
    @DisplayName("Test name")
    void testName() throws IOException {
        String myPackage = GENERAL_PACKAGE + ASSOCIATE;

        Domain domain = JavaAnalyzerEclipseAST.run("/Users/pablo.calvo.local/Documents/proyectos/iberia/mis proyectos/master/TFM/generateuml/src/main/java/repositories/damas/src/main/java");
        domain.toString();
    }

    @Test
    @DisplayName("Should be return a aggregation in domain")
    void shouldBeReturnAAggregationInDomain() {
        String myPackage = GENERAL_PACKAGE + AGGREGATION;
        JavaAnalyzer processPackage = new JavaAnalyzer(myPackage);

        Domain domain = processPackage.run();
        assertTrue(domain.getUnit(myPackage + ".A").getElements().contains(domain.getUnit(myPackage + ".B")));
        assertEquals(1, domain.getUnit(myPackage + ".A").getElements().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getPartList().size());
        assertEquals(1, domain.getUnit(myPackage + ".A").getAssociates().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getBase().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getUsed().size());
        assertEquals(3, domain.getUnitList().size());
    }

    @Test
    @DisplayName("Should be return a two bases in domain")
    void shouldBeReturnATwoBasesInDomain() {
        String myPackage = GENERAL_PACKAGE + BASE;
        JavaAnalyzer processPackage = new JavaAnalyzer(myPackage);

        Domain domain = processPackage.run();
        assertTrue(domain.getUnit(myPackage + ".A").getBase().contains(domain.getUnit(myPackage + ".B")));
        assertTrue(domain.getUnit(myPackage + ".A").getBase().contains(domain.getUnit(myPackage + ".C")));
        assertEquals(2, domain.getUnit(myPackage + ".A").getBase().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getPartList().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getElements().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getAssociates().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getUsed().size());
        assertEquals(3, domain.getUnitList().size());
    }

    @Test
    @DisplayName("Shoulb be return a composition in domain")
    void shoulbBeReturnACompositionInDomain() {
        String myPackage = GENERAL_PACKAGE + COMPOSITION;
        JavaAnalyzer processPackage = new JavaAnalyzer(myPackage);

        Domain domain = processPackage.run();
        assertTrue(domain.getUnit(myPackage + ".A").getPartList().contains(domain.getUnit(myPackage + ".B")));
        assertEquals(2, domain.getUnitList().size());
        assertEquals(1, domain.getUnit(myPackage + ".A").getPartList().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getAssociates().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getElements().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getBase().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getUsed().size());
    }

    @Test
    @DisplayName("Should be return a dependecy in domain")
    void shouldBeReturnADependecyInDomain() {
        String myPackage = GENERAL_PACKAGE + DEPENDECY;
        JavaAnalyzer processPackage = new JavaAnalyzer(myPackage);

        Domain domain = processPackage.run();
        assertTrue(domain.getUnit(myPackage + ".A").getUsed().contains(domain.getUnit(myPackage + ".B")));
        assertEquals(2, domain.getUnitList().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getPartList().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getAssociates().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getElements().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getBase().size());
        assertEquals(1, domain.getUnit(myPackage + ".A").getUsed().size());
    }
}