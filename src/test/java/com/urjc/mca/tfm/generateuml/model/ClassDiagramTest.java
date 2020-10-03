package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


class ClassDiagramTest {

    @Test
    void printClassName(){
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addUnit("Unit2");
        ClassDiagram classDiagram = new ClassDiagram();

        classDiagram.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                        "class package.Unit2\n";
        assertThat(classDiagram.print(), is(result));

    }

    @Test
    void printBase(){
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addBase("Base");
        ClassDiagram classDiagram = new ClassDiagram();

        classDiagram.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                        "class package.Base\n" +
                        "package.Base <|-- package.Unit1\n";
        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printPart(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addPart("Part");

        classDiagram.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                        "class package.Part\n" +
                        "package.Unit1 *--> package.Part\n";
        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printElement(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addElement("Element");

        classDiagram.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                        "class package.Element\n" +
                        "package.Unit1 o--> package.Element\n";
        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printAssociates(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addAssociate("Associate");

        classDiagram.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                        "class package.Associate\n" +
                        "package.Unit1 --> package.Associate\n";
        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void printUsed(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addUsed("Used");

        classDiagram.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                        "class package.Used\n" +
                        "package.Unit1 ..> package.Used\n";
        assertThat(classDiagram.print(), is(result));
    }

    @Test
    void shouldBeReturnDiagramClassPractice1Design(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("mastermind");
        domain.addPackage("mastermind")
                .addUnit("Mastermind").addBase("WithConsoleModel").addPart("SecretCombination").addPart("ProposedCombination")
                .addPart("Result").addUsed("Message")
                .addUnit("Combination").addBase("WithConsoleModel").addPart("Color").addPart("SecretCombination")
                .addUnit("SecretCombination").addBase("Combination").addUsed("ProposedCombination")
                .addUsed("Message").addUsed("Result")
                .addUnit("ProposedCombination").addBase("Combination").addUsed("Error").addUsed("Message");


        classDiagram.addUnits(domain.getUnitList()).print();

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
        Domain domain = new Domain("mastermind");
        domain.addPackage("mastermind")
                .addUnit("Mastermind").addBase("WithConsoleModel").addPart("SecretCombination").addPart("ProposedCombination")
                .addPart("Result").addUsed("Message")
                .addUnit("Combination").addBase("WithConsoleModel").addPart("Color")
                .addPart("SecretCombination")
                .addUnit("SecretCombination")
                .addBase("Combination").addUsed("ProposedCombination")
                .addUsed("Message").addUsed("Result")
                .addUnit("ProposedCombination")
                .addBase("Combination").addUsed("Error").addUsed("Message");

        classDiagram.addUnits(domain.getUnit("SecretCombination").getEfferent());

        String resultPrint = "class mastermind.SecretCombination\n" +
                "mastermind.Combination <|-- mastermind.SecretCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Message\n" +
                "mastermind.SecretCombination ..> mastermind.ProposedCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Result\n";
        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAfferentUnit(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("mastermind");
        domain.addUnit("X")
                .addBase("Base_de_X")
                .addPart("Parte_de_X")
                .addAssociate("Asociada_de_X")
                .addUsed("Usada_por_X")
                .addUnit("Todo_de_X")
                .addPart("X")
                .addUnit("Usa_X")
                .addUsed("X")
                .addUnit("Asociado_a_X")
                .addAssociate("X")
                .addUnit("Descendiente_de_X")
                .addBase("X");

        classDiagram.addUnits(domain.getAfferent("X"));
        String resultPrint = "class Todo_de_X\n" +
                "class Usa_X\n" +
                "class Asociado_a_X\n" +
                "class Descendiente_de_X\n" +
                "Todo_de_X *--> X\n" +
                "Usa_X ..> X\n" +
                "Asociado_a_X --> X\n" +
                "X <|-- Descendiente_de_X\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnDiagramClassPractice1DesignWithTwoModels(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("mastermind");
        Domain domainUtils = new Domain("mastermind.utils");
        domainUtils.addUnit("WithConsoleModel");
        domain
                .addPackage("mastermind.utils")
                    .addUnit("WithConsoleModel")
                .addPackage("mastermind")
                    .addUnit("Mastermind")
//                    .addBase("WithConsoleModel").fromPackage("X")
//                    .addBase("WithConsoleModel","package")
                    .addBase("WithConsoleModel")
                    .addPart("SecretCombination")
                    .addPart("ProposedCombination")
                    .addPart("Result")
                    .addUsed("Message")
                    .addUnit("Combination")
                    .addBase("WithConsoleModel")
                    .addPart("Color")
                    .addPart("SecretCombination")
                    .addUnit("SecretCombination")
                    .addBase("Combination")
                    .addUsed("ProposedCombination")
                    .addUsed("Message")
                    .addUsed("Result")
                    .addUnit("ProposedCombination")
                    .addBase("Combination")
                    .addUsed("Error")
                    .addUsed("Message");

        classDiagram.addUnits(domain.getUnitList()).print();

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
        Domain domain = new Domain("mastermind");
        domain.addPackage("mastermind")
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

        classDiagram.addModel(domain).print();

        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAttributeWithPublicVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute", Visibility.PUBLIC);

        classDiagram.addModel(domain);

        String resultPrint = "class unit{\n" +
                            "+ attribute\n" +
                            "}\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }
    @Test
    void shouldBeReturnAttributeWithPrivateVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute", Visibility.PRIVATE);

        classDiagram.addModel(domain);

        String resultPrint = "class unit{\n" +
                            "- attribute\n" +
                            "}\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }
    @Test
    void shouldBeReturnAttributeWithProtectedVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute", Visibility.PROTECTED);

        classDiagram.addModel(domain);

        String resultPrint = "class unit{\n" +
                            "# attribute\n" +
                            "}\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }
    @Test
    void shouldBeReturnAttributeWithPackageVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute", Visibility.PACKAGE);

        classDiagram.addModel(domain);

        String resultPrint = "class unit{\n" +
                            "~ attribute\n" +
                            "}\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }
    @Test
    void shouldBeReturnAttributeWithoutVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute", Visibility.EMPTY_VISIBILITY);

        classDiagram.addModel(domain);

        String resultPrint = "class unit{\n" +
                            "attribute\n" +
                            "}\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnNameWithSpaces(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("my unit");

        classDiagram.addModel(domain);

        String resultPrint = "class \"my unit\"\n";
        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAttributeWithProtectedVisibilityAndSpacesInName(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("my unit").addAttribute("my attribute", Visibility.PROTECTED);

        classDiagram.addModel(domain);

        String resultPrint = "class \"my unit\"{\n" +
                "# my attribute\n" +
                "}\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnUnitWithFunction(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function");

        classDiagram.addModel(domain);

        String resultPrint = "class unit{\n" +
                "function\n" +
                "}\n";

        assertThat(classDiagram.print(), is(resultPrint));
    }
    @Test
    void shouldBeReturnUnitWithFunctionAndVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function", Visibility.PUBLIC);

        classDiagram.addModel(domain);

        String resultPrint = "class unit{\n" +
                "+ function\n" +
                "}\n";

        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnUnitWithFunctionAndVisibilityAndReturnType(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function", Visibility.PUBLIC, "String");

        classDiagram.addModel(domain);

        String resultPrint = "class unit{\n" +
                "+ function: String\n" +
                "}\n";

        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnUnitWithFunctionAndVisibilityAndReturnTypeAndParameters(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        String[] parameters = {"String", "int"};
        domain.addUnit("unit").addFunction("function", Visibility.PUBLIC, "String", parameters);

        classDiagram.addModel(domain);

        String resultPrint = "class unit{\n" +
                "+ function(String, int): String\n" +
                "}\n";

        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnUnitStaticWithFunctionAndVisibilityAndReturnTypeAndParameters(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        String[] parameters = {"String", "int"};
        domain.addUnit("unit").addFunction("function", Visibility.PUBLIC, "String", parameters, true);

        classDiagram.addModel(domain);

        String resultPrint = "class unit{\n" +
                "+ {static} function(String, int): String\n" +
                "}\n";

        assertThat(classDiagram.print(), is(resultPrint));
    }
}