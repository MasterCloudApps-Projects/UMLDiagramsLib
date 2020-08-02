package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


class ClassDiagramTest {

    @Test
    void printClassName(){
        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity1");
        Unit secondEntity = new Unit("Entity2");
        model.addEntity(firstEntity).addEntity(secondEntity);
        ClassDiagram classDiagram = new ClassDiagram();

        classDiagram.addClasses(model.getEntityList()).print();

        String result = "class model.Entity1\nclass model.Entity2\n";
        assertThat(classDiagram.print(), is(result));

    }

    @Test
    void printBase(){
        ClassDiagram classDiagram = new ClassDiagram();
        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity1");
        Unit baseEntity = new Unit("Base");

        String result = "class model.Entity1\nclass model.Base\nmodel.Base <|-- model.Entity1\n";
        model.addEntity(firstEntity).addBase(baseEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printPart(){
        ClassDiagram classDiagram = new ClassDiagram();
        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity1");
        Unit partEntity = new Unit("Part");

        String result = "class model.Entity1\nclass model.Part\nmodel.Entity1 *--> model.Part\n";
        model.addEntity(firstEntity).addPart(partEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printElement(){
        ClassDiagram classDiagram = new ClassDiagram();
        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity1");
        Unit elementEntity = new Unit("Element");

        String result = "class model.Entity1\nclass model.Element\nmodel.Entity1 o--> model.Element\n";
        model.addEntity(firstEntity).addElement(elementEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printAssociates(){
        ClassDiagram classDiagram = new ClassDiagram();
        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity1");
        Unit associateEntity = new Unit("Associate");

        String result = "class model.Entity1\nclass model.Associate\nmodel.Entity1 --> model.Associate\n";
        model.addEntity(firstEntity).addAssociate(associateEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printUsed(){
        ClassDiagram classDiagram = new ClassDiagram();
        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity1");
        Unit usedEntity = new Unit("Used");

        String result = "class model.Entity1\nclass model.Used\nmodel.Entity1 ..> model.Used\n";
        model.addEntity(firstEntity).addUsed(usedEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnDiagramClassPractice1Design(){
        ClassDiagram classDiagram = new ClassDiagram();
        Package model = new Package("mastermind");
        Unit masterMind = new Unit("Mastermind");
        Unit withConsoleModel = new Unit("WithConsoleModel");
        Unit secretCombination = new Unit("SecretCombination");
        Unit proposedCombination = new Unit("ProposedCombination");
        Unit result = new Unit("Result");
        String resultPrint = "class mastermind.Mastermind\n" +
                "class mastermind.WithConsoleModel\n" +
                "class mastermind.SecretCombination\n" +
                "class mastermind.ProposedCombination\n" +
                "class mastermind.Result\n" +
                "class mastermind.Message\n" +
                "class mastermind.Combination\n" +
                "class mastermind.Color\n" +
                "class mastermind.Error\n" +
                "mastermind.WithConsoleModel <|-- mastermind.Mastermind\n" +
                "mastermind.Mastermind *--> mastermind.SecretCombination\n" +
                "mastermind.Mastermind *--> mastermind.ProposedCombination\n" +
                "mastermind.Mastermind *--> mastermind.Result\n" +
                "mastermind.Mastermind ..> mastermind.Message\n" +
                "mastermind.Combination <|-- mastermind.SecretCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Message\n" +
                "mastermind.SecretCombination ..> mastermind.ProposedCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Result\n" +
                "mastermind.Combination <|-- mastermind.ProposedCombination\n" +
                "mastermind.ProposedCombination ..> mastermind.Message\n" +
                "mastermind.ProposedCombination ..> mastermind.Error\n" +
                "mastermind.WithConsoleModel <|-- mastermind.Combination\n" +
                "mastermind.Combination *--> mastermind.Color\n" +
                "mastermind.Combination *--> mastermind.SecretCombination\n";


        model.addEntity(masterMind).addBase(withConsoleModel).addPart(secretCombination).addPart(proposedCombination)
                .addPart(result).addUsed("Message").addEntity("Combination").addBase(withConsoleModel).addPart("Color")
        .addPart(secretCombination).addEntity(secretCombination).addBase("Combination").addUsed(proposedCombination)
        .addUsed("Message").addUsed(result).addEntity(proposedCombination).addBase("Combination").addUsed("Error").addUsed("Message");
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnSecretCombinationInDiagramClassPractice1Design(){
        ClassDiagram classDiagram = new ClassDiagram();
        Package model = new Package("mastermind");
        Unit masterMind = new Unit("Mastermind");
        Unit withConsoleModel = new Unit("WithConsoleModel");
        Unit secretCombination = new Unit("SecretCombination");
        Unit proposedCombination = new Unit("ProposedCombination");
        Unit result = new Unit("Result");
        String resultPrint = "class mastermind.SecretCombination\n" +
                "mastermind.Combination <|-- mastermind.SecretCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Message\n" +
                "mastermind.SecretCombination ..> mastermind.ProposedCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Result\n";


        model.addEntity(masterMind).addBase(withConsoleModel)
                    .addPart(secretCombination).addPart(proposedCombination)
                    .addPart(result).addUsed("Message")
                    .addEntity("Combination").addBase(withConsoleModel).addPart("Color")
                    .addPart(secretCombination)
                .addEntity(secretCombination)
                    .addBase("Combination").addUsed(proposedCombination)
                    .addUsed("Message").addUsed(result)
                .addEntity(proposedCombination)
                    .addBase("Combination").addUsed("Error").addUsed("Message");

        classDiagram.addClasses(model.getEntity(secretCombination.name).getEferents()).print();

        assertThat(classDiagram.print(), is(resultPrint));
    }


    @Test
    void shouldBeReturnDiagramClassPractice1DesignWithTwoModels(){
        ClassDiagram classDiagram = new ClassDiagram();
        Package model = new Package("mastermind");
        Package modelUtils = new Package("mastermind.utils");
        Unit masterMind = new Unit("Mastermind");
        Unit withConsoleModel = new Unit("WithConsoleModel");
        Unit secretCombination = new Unit("SecretCombination");
        Unit proposedCombination = new Unit("ProposedCombination");
        Unit result = new Unit("Result");

        String resultPrint = "class mastermind.Mastermind\n" +
                "class mastermind.utils.WithConsoleModel\n" +
                "class mastermind.SecretCombination\n" +
                "class mastermind.ProposedCombination\n" +
                "class mastermind.Result\n" +
                "class mastermind.Message\n" +
                "class mastermind.Combination\n" +
                "class mastermind.Color\n" +
                "class mastermind.Error\n" +
                "mastermind.utils.WithConsoleModel <|-- mastermind.Mastermind\n" +
                "mastermind.Mastermind *--> mastermind.SecretCombination\n" +
                "mastermind.Mastermind *--> mastermind.ProposedCombination\n" +
                "mastermind.Mastermind *--> mastermind.Result\n" +
                "mastermind.Mastermind ..> mastermind.Message\n" +
                "mastermind.Combination <|-- mastermind.SecretCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Message\n" +
                "mastermind.SecretCombination ..> mastermind.ProposedCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Result\n" +
                "mastermind.Combination <|-- mastermind.ProposedCombination\n" +
                "mastermind.ProposedCombination ..> mastermind.Message\n" +
                "mastermind.ProposedCombination ..> mastermind.Error\n" +
                "mastermind.utils.WithConsoleModel <|-- mastermind.Combination\n" +
                "mastermind.Combination *--> mastermind.Color\n" +
                "mastermind.Combination *--> mastermind.SecretCombination\n";


        modelUtils.addEntity(withConsoleModel);
        model.addEntity(masterMind).addBase(withConsoleModel).addPart(secretCombination).addPart(proposedCombination)
                .addPart(result).addUsed("Message").addEntity("Combination").addBase(withConsoleModel).addPart("Color")
                .addPart(secretCombination).addEntity(secretCombination).addBase("Combination").addUsed(proposedCombination)
                .addUsed("Message").addUsed(result).addEntity(proposedCombination).addBase("Combination").addUsed("Error").addUsed("Message");
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(resultPrint));
    }
    @Test
    void shouldBeReturnDiagramClassPractice1DesignWithTwoModelsWhenAddModel(){
        ClassDiagram classDiagram = new ClassDiagram();
        Package model = new Package("mastermind");
        Package modelUtils = new Package("mastermind.utils");
        Unit masterMind = new Unit("Mastermind");
        Unit withConsoleModel = new Unit("WithConsoleModel");
        Unit secretCombination = new Unit("SecretCombination");
        Unit proposedCombination = new Unit("ProposedCombination");
        Unit result = new Unit("Result");

        String resultPrint = "class mastermind.Mastermind\n" +
                "class mastermind.utils.WithConsoleModel\n" +
                "class mastermind.SecretCombination\n" +
                "class mastermind.ProposedCombination\n" +
                "class mastermind.Result\n" +
                "class mastermind.Message\n" +
                "class mastermind.Combination\n" +
                "class mastermind.Color\n" +
                "class mastermind.Error\n" +
                "mastermind.utils.WithConsoleModel <|-- mastermind.Mastermind\n" +
                "mastermind.Mastermind *--> mastermind.SecretCombination\n" +
                "mastermind.Mastermind *--> mastermind.ProposedCombination\n" +
                "mastermind.Mastermind *--> mastermind.Result\n" +
                "mastermind.Mastermind ..> mastermind.Message\n" +
                "mastermind.Combination <|-- mastermind.SecretCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Message\n" +
                "mastermind.SecretCombination ..> mastermind.ProposedCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Result\n" +
                "mastermind.Combination <|-- mastermind.ProposedCombination\n" +
                "mastermind.ProposedCombination ..> mastermind.Message\n" +
                "mastermind.ProposedCombination ..> mastermind.Error\n" +
                "mastermind.utils.WithConsoleModel <|-- mastermind.Combination\n" +
                "mastermind.Combination *--> mastermind.Color\n" +
                "mastermind.Combination *--> mastermind.SecretCombination\n";


        modelUtils.addEntity(withConsoleModel);
        model.addEntity(masterMind).addBase(withConsoleModel).addPart(secretCombination).addPart(proposedCombination)
                .addPart(result).addUsed("Message")
                .addEntity("Combination").addBase(withConsoleModel).addPart("Color")
                .addPart(secretCombination)
                .addEntity(secretCombination).addBase("Combination").addUsed(proposedCombination)
                .addUsed("Message").addUsed(result)
                .addEntity(proposedCombination).addBase("Combination").addUsed("Error").addUsed("Message");
        classDiagram.addModel(model).print();

        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void test(){
        ClassDiagram classDiagram = new ClassDiagram();
        Package model1 = new Package("model1");
        Package model2 = new Package("model2");

        model2.addEntity("entity2");
        model1.addEntity("entity1").addPart("Part1").addBase(model2.getEntity("entity2"));

//        classDiagram.addModel(model1).addModel(model2);
        classDiagram.addClasses(model1.getEntityList());

        System.out.println(classDiagram.print());
    }
}