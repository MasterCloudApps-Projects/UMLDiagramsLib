package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("should be return abstract unit")
    void shouldBeReturnAbstractUnit() {
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .setAbstractUnit();
        ClassDiagram classDiagram = new ClassDiagram();

        classDiagram.addDomain(domain);

        String result = "abstract class package.Unit1\n";
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

        classDiagram.addUnits(domain.getEfferent("SecretCombination"));

        String resultPrint = "class mastermind.SecretCombination\n" +
                "mastermind.Combination <|-- mastermind.SecretCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Message\n" +
                "mastermind.SecretCombination ..> mastermind.ProposedCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Result\n";
        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAfferentUnitForX(){
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
    void shouldBeReturnAllAfferent(){
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

        classDiagram.addUnits(domain.getAllAfferent());

        String resultPrint = "class \"afferent X.Todo_de_X\"\n" +
                "class \"afferent X.Usa_X\"\n" +
                "class \"afferent X.Asociado_a_X\"\n" +
                "class \"afferent X.Descendiente_de_X\"\n" +
                "class \"afferent Base_de_X.X\"\n" +
                "class \"afferent Parte_de_X.X\"\n" +
                "class \"afferent Asociada_de_X.X\"\n" +
                "class \"afferent Usada_por_X.X\"\n" +
                "\"afferent X.Todo_de_X\" *--> \"afferent X.X\"\n" +
                "\"afferent X.Usa_X\" ..> \"afferent X.X\"\n" +
                "\"afferent X.Asociado_a_X\" --> \"afferent X.X\"\n" +
                "\"afferent X.X\" <|-- \"afferent X.Descendiente_de_X\"\n" +
                "\"afferent Base_de_X.Base_de_X\" <|-- \"afferent Base_de_X.X\"\n" +
                "\"afferent Parte_de_X.X\" *--> \"afferent Parte_de_X.Parte_de_X\"\n" +
                "\"afferent Asociada_de_X.X\" --> \"afferent Asociada_de_X.Asociada_de_X\"\n" +
                "\"afferent Usada_por_X.X\" ..> \"afferent Usada_por_X.Usada_por_X\"\n";
        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAfferentUnitForBase_de_X(){
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

        classDiagram.addUnits(domain.getAfferent("Base_de_X"));

        String resultPrint = "class X\n" +
                "Base_de_X <|-- X\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnEfferentUnit(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("mastermind");
        domain.addUnit("X")
                .addBase("Base_de_X")
                .addPart("Parte_de_X")
                .addAssociate("Asociada_de_X")
                .addUsed("Usada_por_X")
                .addUnit("Todo_de_X")
                .addBase("Based de todo de X")
                .addPart("X")
                .addUnit("Usa_X")
                .addUsed("X")
                .addUnit("Asociado_a_X")
                .addAssociate("X")
                .addUnit("Descendiente_de_X")
                .addBase("X");

        classDiagram.addUnits(domain.getEfferent("X"));
        String resultPrint = "class X\n" +
                "Base_de_X <|-- X\n" +
                "X *--> Parte_de_X\n" +
                "X --> Asociada_de_X\n" +
                "X ..> Usada_por_X\n";
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

        String resultPrint = "class mastermind.utils.WithConsoleModel\n" +
                "class mastermind.Mastermind\n" +
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

        classDiagram.addDomain(domain).print();

        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAttributeWithPublicVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PUBLIC);

        classDiagram.addDomain(domain);

        String resultPrint = "class unit{\n" +
                            "+ attribute\n" +
                            "}\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }
    @Test
    void shouldBeReturnAttributeWithPrivateVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PRIVATE);

        classDiagram.addDomain(domain);

        String resultPrint = "class unit{\n" +
                            "- attribute\n" +
                            "}\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }
    @Test
    void shouldBeReturnAttributeWithProtectedVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PROTECTED);

        classDiagram.addDomain(domain);

        String resultPrint = "class unit{\n" +
                            "# attribute\n" +
                            "}\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }
    @Test
    void shouldBeReturnAttributeWithPackageVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PACKAGE);

        classDiagram.addDomain(domain);

        String resultPrint = "class unit{\n" +
                            "~ attribute\n" +
                            "}\n";
        assertThat(classDiagram.print(), is(resultPrint));

    }
    @Test
    void shouldBeReturnAttributeWithoutVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.EMPTY_VISIBILITY);

        classDiagram.addDomain(domain);

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

        classDiagram.addDomain(domain);

        String resultPrint = "class \"my unit\"\n";
        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAttributeWithProtectedVisibilityAndSpacesInName(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("my unit").addAttribute("my attribute").addVisibility(Visibility.PROTECTED);

        classDiagram.addDomain(domain);

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

        classDiagram.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "function\n" +
                "}\n";

        assertThat(classDiagram.print(), is(resultPrint));
    }
    @Test
    void shouldBeReturnUnitWithFunctionAndVisibility(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC);

        classDiagram.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "+ function\n" +
                "}\n";

        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnUnitWithFunctionAndVisibilityAndReturnType(){
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType( "String");

        classDiagram.addDomain(domain);

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
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String")
                .addParameters(parameters);

        classDiagram.addDomain(domain);

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
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String")
                .addParameters(parameters).setStatic(true);

        classDiagram.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "+ {static} function(String, int): String\n" +
                "}\n";

        assertThat(classDiagram.print(), is(resultPrint));
    }


    @Test
    void shouldBeReturnAllEfferentUnits(){
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

        classDiagram.addUnits(domain.getAllEfferents());
        System.out.println(classDiagram.print());

        String resultPrint = "class \"efferent X.X\"\n" +
                "class \"efferent Base_de_X.Base_de_X\"\n" +
                "class \"efferent Parte_de_X.Parte_de_X\"\n" +
                "class \"efferent Asociada_de_X.Asociada_de_X\"\n" +
                "class \"efferent Usada_por_X.Usada_por_X\"\n" +
                "class \"efferent Todo_de_X.Todo_de_X\"\n" +
                "class \"efferent Usa_X.Usa_X\"\n" +
                "class \"efferent Asociado_a_X.Asociado_a_X\"\n" +
                "class \"efferent Descendiente_de_X.Descendiente_de_X\"\n" +
                "\"efferent X.Base_de_X\" <|-- \"efferent X.X\"\n" +
                "\"efferent X.X\" *--> \"efferent X.Parte_de_X\"\n" +
                "\"efferent X.X\" --> \"efferent X.Asociada_de_X\"\n" +
                "\"efferent X.X\" ..> \"efferent X.Usada_por_X\"\n" +
                "\"efferent Todo_de_X.Todo_de_X\" *--> \"efferent Todo_de_X.X\"\n" +
                "\"efferent Usa_X.Usa_X\" ..> \"efferent Usa_X.X\"\n" +
                "\"efferent Asociado_a_X.Asociado_a_X\" --> \"efferent Asociado_a_X.X\"\n" +
                "\"efferent Descendiente_de_X.X\" <|-- \"efferent Descendiente_de_X.Descendiente_de_X\"\n";

        assertThat(classDiagram.print(), is(resultPrint));
    }

    @Test
    @DisplayName("should be return only packages")
    void shouldBeReturnOnlyPackages() {
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("mastermind");
        domain.addPackage("package1").addUnit("unit1").addUnit("unit2").addUsed("used").addPackage("package2").addUnit("unit3")
                .addPackage("package3").addUnit("unit4").addUsed("unit1");

        classDiagram.addDomain(domain);

        String resultPrint ="package package1 {} \n" +
                "package package2 {} \n" +
                "package package3 {} \n" +
                "package3 ..> package1\n";
        assertThat(classDiagram.printPackage(), is(resultPrint));
    }

    @Test
    @DisplayName("should be return concrete package and one level relation to other packages")
    void shouldBeReturnConcretePackageAndOneLevelRelationToOtherPackages() {
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("mastermain solution v15.6");
        domain.addPackage("mastermind")
                .addUnit("MastermindStandalone")
                .addBase("Mastermind")
                .addPackage("mastermind.controllers")
                .addUnit("AcceptorController")
                .addUsed("ControllersVisitor")
                .addUnit("Logic")
                .addPackage("masterind")
                .addUnit("Mastermind")
                .addUsed("AcceptorController")
                .addPart("Logic");

        String result = "class mastermind.MastermindStandalone\n" +
                "class mastermind.Mastermind\n" +
                "class mastermind.controllers.AcceptorController\n" +
                "class mastermind.controllers.Logic\n" +
                "mastermind.Mastermind <|-- mastermind.MastermindStandalone\n" +
                "mastermind.Mastermind *--> mastermind.controllers.Logic\n" +
                "mastermind.Mastermind ..> mastermind.controllers.AcceptorController\n";

        System.out.println(classDiagram.addDomain(domain).print("mastermind"));

        assertThat(classDiagram.print("mastermind"), is(result));
    }
    
    @Test
    @DisplayName("should be return one unit and base with the same name and different package")
    void shouldBeReturnOneUnitAndBaseWithTheSameNameAndDifferentPackage() {
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addBase("unit", "secondpackage");
        classDiagram.addDomain(domain);

        String result = "class mypackage.unit\n" +
                "class secondpackage.unit\n" +
                "secondpackage.unit <|-- mypackage.unit\n";

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    @DisplayName("should be return one unit and part with the same name and different package")
    void shouldBeReturnOneUnitAndPartWithTheSameNameAndDifferentPackage() {
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addPart("unit", "secondpackage");

        classDiagram.addDomain(domain);

        String result = "class mypackage.unit\n" +
                "class secondpackage.unit\n" +
                "mypackage.unit *--> secondpackage.unit\n";

        assertThat(classDiagram.print(), is(result));
    }
    @Test
    @DisplayName("should be return one unit and element with the same name and different package")
    void shouldBeReturnOneUnitAndElementWithTheSameNameAndDifferentPackage() {
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addElement("unit", "secondpackage");

        classDiagram.addDomain(domain);

        String result = "class mypackage.unit\n" +
                "class secondpackage.unit\n" +
                "mypackage.unit o--> secondpackage.unit\n";

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    @DisplayName("should be return one unit and associate with the same name and different package")
    void shouldBeReturnOneUnitAndAssociateWithTheSameNameAndDifferentPackage() {
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addAssociate("unit", "secondpackage");

        classDiagram.addDomain(domain);

        String result = "class mypackage.unit\n" +
                "class secondpackage.unit\n" +
                "mypackage.unit --> secondpackage.unit\n";

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    @DisplayName("should be return one unit and used with the same name and different package")
    void shouldBeReturnOneUnitAndUsedWithTheSameNameAndDifferentPackage() {
        ClassDiagram classDiagram = new ClassDiagram();
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addUsed("unit", "secondpackage");

        classDiagram.addDomain(domain);

        String result = "class mypackage.unit\n" +
                "class secondpackage.unit\n" +
                "mypackage.unit ..> secondpackage.unit\n";

        assertThat(classDiagram.print(), is(result));
    }
}