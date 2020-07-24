package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


class ClassDiagramTest {

    @Test
    void printClassName(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity firstEntity = new Entity("Entity1");
        Entity secondEntity = new Entity("Entity2");

        String result = "class Entity1\nclass Entity2\n";
        model.addEntity(firstEntity).addEntity(secondEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));

    }

    @Test
    void printBase(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity firstEntity = new Entity("Entity1");
        Entity baseEntity = new Entity("Base");

        String result = "class Entity1\nclass Base\nBase <|-- Entity1\n";
        model.addEntity(firstEntity).addBase(baseEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printPart(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity firstEntity = new Entity("Entity1");
        Entity partEntity = new Entity("Part");

        String result = "class Entity1\nclass Part\nEntity1 *--> Part\n";
        model.addEntity(firstEntity).addPart(partEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printElement(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity firstEntity = new Entity("Entity1");
        Entity elementEntity = new Entity("Element");

        String result = "class Entity1\nclass Element\nEntity1 o--> Element\n";
        model.addEntity(firstEntity).addElement(elementEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printAssociates(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity firstEntity = new Entity("Entity1");
        Entity associateEntity = new Entity("Associate");

        String result = "class Entity1\nclass Associate\nEntity1 --> Associate\n";
        model.addEntity(firstEntity).addAssociate(associateEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printUsed(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity firstEntity = new Entity("Entity1");
        Entity usedEntity = new Entity("Used");

        String result = "class Entity1\nclass Used\nEntity1 ..> Used\n";
        model.addEntity(firstEntity).addUsed(usedEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void test1(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity classA = new Entity("classA");
        Entity classB = new Entity("classB");
        Entity classC = new Entity("classC");
        Entity classD = new Entity("classD");

        String result = "class classB\nclass classA\nclass classC\nclass classD\n" +
                "classA <|-- classB\nclassB *--> classC\nclassB *--> classD\n";

        model.addEntity(classB).addBase(classA).addPart(classC).addPart(classD);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void test2(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity masterMind = new Entity("Mastermind");
        Entity withConsoleModel = new Entity("WithConsoleModel");
        Entity secretCombination = new Entity("SecretCombination");
        Entity proposedCombination = new Entity("ProposedCombination");
        Entity result = new Entity("Result");
        String resultPrint = "class Mastermind\n" +
                "class WithConsoleModel\n" +
                "class SecretCombination\n" +
                "class ProposedCombination\n" +
                "class Result\n" +
                "WithConsoleModel <|-- Mastermind\n" +
                "Mastermind *--> SecretCombination\n" +
                "Mastermind *--> ProposedCombination\n" +
                "Mastermind *--> Result\n";


        model.addEntity(masterMind).addBase(withConsoleModel).addPart(secretCombination).addPart(proposedCombination)
                .addPart(result);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void test3(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity masterMind = new Entity("Mastermind");
        Entity withConsoleModel = new Entity("WithConsoleModel");
        Entity secretCombination = new Entity("SecretCombination");
        Entity proposedCombination = new Entity("ProposedCombination");
        Entity result = new Entity("Result");
        String resultPrint = "class Mastermind\n" +
                "class WithConsoleModel\n" +
                "class SecretCombination\n" +
                "class ProposedCombination\n" +
                "class Result\n" +
                "class Message\n" +
                "class Combination\n" +
                "class Color\n" +
                "class Error\n" +
                "WithConsoleModel <|-- Mastermind\n" +
                "Mastermind *--> SecretCombination\n" +
                "Mastermind *--> ProposedCombination\n" +
                "Mastermind *--> Result\n" +
                "Mastermind ..> Message\n" +
                "Combination <|-- SecretCombination\n" +
                "SecretCombination ..> Message\n" +
                "SecretCombination ..> ProposedCombination\n" +
                "SecretCombination ..> Result\n" +
                "Combination <|-- ProposedCombination\n" +
                "ProposedCombination ..> Message\n" +
                "ProposedCombination ..> Error\n" +
                "WithConsoleModel <|-- Combination\n" +
                "Combination *--> Color\n" +
                "Combination *--> SecretCombination\n";


        model.addEntity(masterMind).addBase(withConsoleModel).addPart(secretCombination).addPart(proposedCombination)
                .addPart(result).addUsed("Message").addEntity("Combination").addBase(withConsoleModel).addPart("Color")
        .addPart(secretCombination).addEntity(secretCombination).addBase("Combination").addUsed(proposedCombination)
        .addUsed("Message").addUsed(result).addEntity(proposedCombination).addBase("Combination").addUsed("Error").addUsed("Message");
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(resultPrint));
    }
}