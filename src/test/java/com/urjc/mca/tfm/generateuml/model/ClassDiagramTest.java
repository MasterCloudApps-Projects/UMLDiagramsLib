package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


class ClassDiagramTest {

    @Test
    void printClassName(){
        Model model = new Model("model");
        model.addPackage("package")
                .addUnit("Entity1")
                .addUnit("Entity2");
        ClassDiagram classDiagram = new ClassDiagram();

        classDiagram.addClasses(model.getUnitList()).print();

        String result = "class package.Entity1\n" +
                        "class package.Entity2\n";
        assertThat(classDiagram.print(), is(result));

    }

    @Test
    void printBase(){
        Model model = new Model("model");
        model.addPackage("package")
                .addUnit("Entity1")
                .addBase("Base");
        ClassDiagram classDiagram = new ClassDiagram();

        classDiagram.addClasses(model.getUnitList()).print();

        String result = "class package.Entity1\n" +
                        "class package.Base\n" +
                        "package.Base <|-- package.Entity1\n";
        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printPart(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model("model");
        model.addPackage("package")
                .addUnit("Entity1")
                .addPart("Part");

        classDiagram.addClasses(model.getUnitList()).print();

        String result = "class package.Entity1\n" +
                        "class package.Part\n" +
                        "package.Entity1 *--> package.Part\n";
        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printElement(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model("model");
        model.addPackage("package")
                .addUnit("Entity1")
                .addElement("Element");

        classDiagram.addClasses(model.getUnitList()).print();

        String result = "class package.Entity1\n" +
                        "class package.Element\n" +
                        "package.Entity1 o--> package.Element\n";
        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printAssociates(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model("model");
        model.addPackage("package")
                .addUnit("Entity1")
                .addAssociate("Associate");

        classDiagram.addClasses(model.getUnitList()).print();

        String result = "class package.Entity1\n" +
                        "class package.Associate\n" +
                        "package.Entity1 --> package.Associate\n";
        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printUsed(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model("model");
        model.addPackage("package")
                .addUnit("Entity1")
                .addUsed("Used");

        classDiagram.addClasses(model.getUnitList()).print();

        String result = "class package.Entity1\n" +
                        "class package.Used\n" +
                        "package.Entity1 ..> package.Used\n";
        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnDiagramClassPractice1Design(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model("mastermind");
        model.addPackage("mastermind")
                .addUnit("Mastermind").addBase("WithConsoleModel").addPart("SecretCombination").addPart("ProposedCombination")
                .addPart("Result").addUsed("Message")
                .addUnit("Combination").addBase("WithConsoleModel").addPart("Color").addPart("SecretCombination")
                .addUnit("SecretCombination").addBase("Combination").addUsed("ProposedCombination")
                .addUsed("Message").addUsed("Result")
                .addUnit("ProposedCombination").addBase("Combination").addUsed("Error").addUsed("Message");


        classDiagram.addClasses(model.getUnitList()).print();

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
        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnSecretCombinationInDiagramClassPractice1Design(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model("mastermind");
        model.addPackage("mastermind")
                .addUnit("Mastermind").addBase("WithConsoleModel").addPart("SecretCombination").addPart("ProposedCombination")
                .addPart("Result").addUsed("Message")
                .addUnit("Combination").addBase("WithConsoleModel").addPart("Color")
                .addPart("SecretCombination")
                .addUnit("SecretCombination")
                .addBase("Combination").addUsed("ProposedCombination")
                .addUsed("Message").addUsed("Result")
                .addUnit("ProposedCombination")
                .addBase("Combination").addUsed("Error").addUsed("Message");

        classDiagram.addClasses(model.getUnit("SecretCombination").getEferents()).print();

        String resultPrint = "class mastermind.SecretCombination\n" +
                "mastermind.Combination <|-- mastermind.SecretCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Message\n" +
                "mastermind.SecretCombination ..> mastermind.ProposedCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Result\n";
        assertThat(classDiagram.print(), is(resultPrint));
    }


    @Test
    void shouldBeReturnDiagramClassPractice1DesignWithTwoModels(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model("mastermind");
        Model modelUtils = new Model("mastermind.utils");
        modelUtils.addUnit("WithConsoleModel");
        model.addPackage("mastermind")
                .addUnit("Mastermind").addPackage("mastermind.utils").addBase("WithConsoleModel").addPackage("mastermind").addPart("SecretCombination").addPart("ProposedCombination")
                .addPart("Result").addUsed("Message")
                .addUnit("Combination").addBase("WithConsoleModel").addPart("Color").addPart("SecretCombination")
                .addUnit("SecretCombination").addBase("Combination").addUsed("ProposedCombination")
                .addUsed("Message").addUsed("Result")
                .addUnit("ProposedCombination").addBase("Combination").addUsed("Error").addUsed("Message");

        classDiagram.addClasses(model.getUnitList()).print();

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
        assertThat(classDiagram.print(), is(resultPrint));
    }
    @Test
    void shouldBeReturnDiagramClassPractice1DesignWithTwoModelsWhenAddModel(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model("mastermind");
        model.addPackage("mastermind")
                .addUnit("Mastermind")
                .addPackage("mastermind.utils")
                .addBase("WithConsoleModel")
                .addPackage("mastermind")
                .addPart("SecretCombination").addPart("ProposedCombination")
                .addPart("Result").addUsed("Message")
                .addUnit("Combination").addBase("WithConsoleModel").addPart("Color")
                .addPart("SecretCombination")
                .addUnit("SecretCombination").addBase("Combination").addUsed("ProposedCombination")
                .addUsed("Message").addUsed("Result")
                .addUnit("ProposedCombination").addBase("Combination").addUsed("Error").addUsed("Message");

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


        //modelUtils.addPackage("mastermind.utils").addEntity("WithConsoleModel");
        classDiagram.addModel(model).print();

        assertThat(classDiagram.print(), is(resultPrint));
    }

//    @Test
//    void test(){
//        ClassDiagram classDiagram = new ClassDiagram();
//        Package model1 = new Package("model1");
//        Package model2 = new Package("model2");
//
//        model2.addEntity("entity2");
//        model1.addEntity("entity1").addPart("Part1").addBase(model2.getEntity("entity2"));
//
////        classDiagram.addModel(model1).addModel(model2);
//        classDiagram.addClasses(model1.getEntityList());
//
//        System.out.println(classDiagram.print());
//    }
}