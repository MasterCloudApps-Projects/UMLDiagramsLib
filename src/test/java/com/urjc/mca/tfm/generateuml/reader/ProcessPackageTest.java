package com.urjc.mca.tfm.generateuml.reader;

import com.urjc.mca.tfm.generateuml.model.Domain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcessPackageTest {

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
        ProcessPackage processPackage = new ProcessPackage(myPackage);

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
    @DisplayName("Should be return a aggregation in domain")
    void shouldBeReturnAAggregationInDomain() {
        String myPackage = GENERAL_PACKAGE + AGGREGATION;
        ProcessPackage processPackage = new ProcessPackage(myPackage);

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
        ProcessPackage processPackage = new ProcessPackage(myPackage);

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
        ProcessPackage processPackage = new ProcessPackage(myPackage);

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
        ProcessPackage processPackage = new ProcessPackage(myPackage);

        Domain domain = processPackage.run();
        assertTrue(domain.getUnit(myPackage + ".A").getUsed().contains(domain.getUnit(myPackage + ".B")));
        assertEquals(2, domain.getUnitList().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getPartList().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getAssociates().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getElements().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getBase().size());
        assertEquals(0, domain.getUnit(myPackage + ".A").getUsed().size());
    }
}