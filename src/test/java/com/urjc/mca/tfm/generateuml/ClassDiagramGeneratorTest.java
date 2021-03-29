package com.urjc.mca.tfm.generateuml;

import com.urjc.mca.tfm.generateuml.model.Domain;
import com.urjc.mca.tfm.generateuml.model.Visibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@SpringBootTest(classes = ClassDiagramGenerator.class)
class ClassDiagramGeneratorTest {

    @Autowired
    ClassDiagramGenerator classDiagramGenerator;

    @BeforeEach
    void reset(){
        classDiagramGenerator.clearUnits();
    }
    @Test
    void printClassName() {
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addUnit("Unit2");

        classDiagramGenerator.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                "class package.Unit2\n";
        assertThat(classDiagramGenerator.print(), is(result));

    }

    @Test
    @DisplayName("should be return abstract unit")
    void shouldBeReturnAbstractUnit() {
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .setAbstractUnit();

        classDiagramGenerator.addDomain(domain);

        String result = "abstract class package.Unit1\n";
        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    void printBase() {
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addBase("Base");

        classDiagramGenerator.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                "class package.Base\n" +
                "package.Base <|-- package.Unit1\n";
        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    void printPart() {
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addPart("Part");

        classDiagramGenerator.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                "class package.Part\n" +
                "package.Unit1 *--> package.Part\n";
        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    void printElement() {
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addElement("Element");

        classDiagramGenerator.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                "class package.Element\n" +
                "package.Unit1 o--> package.Element\n";
        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    void printAssociates() {
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addAssociate("Associate");

        classDiagramGenerator.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                "class package.Associate\n" +
                "package.Unit1 --> package.Associate\n";
        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    void printUsed() {
        Domain domain = new Domain("domain");
        domain.addPackage("package")
                .addUnit("Unit1")
                .addUsed("Used");

        classDiagramGenerator.addUnits(domain.getUnitList()).print();

        String result = "class package.Unit1\n" +
                "class package.Used\n" +
                "package.Unit1 ..> package.Used\n";
        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    void shouldBeReturnDiagramClassPractice1Design() {
        Domain domain = new Domain("mastermind");
        domain.addPackage("mastermind")
                .addUnit("Mastermind")
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


        classDiagramGenerator.addUnits(domain.getUnitList()).print();

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
        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnSecretCombinationInDiagramClassPractice1Design() {
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

        classDiagramGenerator.addUnits(domain.getEfferent("SecretCombination"));

        String resultPrint = "class mastermind.SecretCombination\n" +
                "mastermind.Combination <|-- mastermind.SecretCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Message\n" +
                "mastermind.SecretCombination ..> mastermind.ProposedCombination\n" +
                "mastermind.SecretCombination ..> mastermind.Result\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAfferentUnitForX() {
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

        classDiagramGenerator.addUnits(domain.getAfferent("X"));
        System.out.println(classDiagramGenerator.print());
        String resultPrint = "class Todo_de_X\n" +
                "class Usa_X\n" +
                "class Asociado_a_X\n" +
                "class Descendiente_de_X\n" +
                "Todo_de_X *--> X\n" +
                "Usa_X ..> X\n" +
                "Asociado_a_X --> X\n" +
                "X <|-- Descendiente_de_X\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAllAfferent() {
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

        classDiagramGenerator.addUnits(domain.getAllAfferent());

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
        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAfferentUnitForBase_de_X() {
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

        classDiagramGenerator.addUnits(domain.getAfferent("Base_de_X"));

        String resultPrint = "class X\n" +
                "Base_de_X <|-- X\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnEfferentUnit() {
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

        classDiagramGenerator.addUnits(domain.getEfferent("X"));
        System.out.println(classDiagramGenerator.print());
        String resultPrint = "class X\n" +
                "Base_de_X <|-- X\n" +
                "X *--> Parte_de_X\n" +
                "X --> Asociada_de_X\n" +
                "X ..> Usada_por_X\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnDiagramClassPractice1DesignWithTwoModels() {
        Domain domain = new Domain("mastermind");
        Domain domainUtils = new Domain("mastermind.utils");
        domainUtils.addUnit("WithConsoleModel");
        domain
                .addPackage("mastermind.utils")
                    .addUnit("WithConsoleModel")
                .addPackage("mastermind")
                    .addUnit("Mastermind")
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

        classDiagramGenerator.addUnits(domain.getUnitList()).print();

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
        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnDiagramClassPractice1DesignWithTwoModelsWhenAddDomain() {
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

        classDiagramGenerator.addDomain(domain).print();

        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAttributeWithPublicVisibility() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PUBLIC);

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "+ attribute\n" +
                "}\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnAttributeWithPrivateVisibility() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PRIVATE);

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "- attribute\n" +
                "}\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnAttributeWithProtectedVisibility() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PROTECTED);

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "# attribute\n" +
                "}\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnAttributeWithPackageVisibility() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PACKAGE);

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "~ attribute\n" +
                "}\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnAttributeWithoutVisibility() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.EMPTY_VISIBILITY);

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "attribute\n" +
                "}\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnNameWithSpaces() {
        Domain domain = new Domain("domain");
        domain.addUnit("my unit");

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class \"my unit\"\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnAttributeWithProtectedVisibilityAndSpacesInName() {
        Domain domain = new Domain("domain");
        domain.addUnit("my unit").addAttribute("my attribute").addVisibility(Visibility.PROTECTED);

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class \"my unit\"{\n" +
                "# my attribute\n" +
                "}\n";
        assertThat(classDiagramGenerator.print(), is(resultPrint));

    }

    @Test
    void shouldBeReturnUnitWithFunction() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function");

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "function\n" +
                "}\n";

        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnUnitWithFunctionAndVisibility() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC);

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "+ function\n" +
                "}\n";

        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnUnitWithFunctionAndVisibilityAndReturnType() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String");

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "+ function: String\n" +
                "}\n";

        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnUnitWithFunctionAndVisibilityAndReturnTypeAndParameters() {
        Domain domain = new Domain("domain");
        String[] parameters = {"String", "int"};
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String")
                .addParameters(parameters);

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "+ function(String, int): String\n" +
                "}\n";

        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    void shouldBeReturnUnitStaticWithFunctionAndVisibilityAndReturnTypeAndParameters() {
        Domain domain = new Domain("domain");
        String[] parameters = {"String", "int"};
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String")
                .addParameters(parameters).setStatic(true);

        classDiagramGenerator.addDomain(domain);

        String resultPrint = "class unit{\n" +
                "+ {static} function(String, int): String\n" +
                "}\n";

        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }


    @Test
    void shouldBeReturnAllEfferentUnits() {
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

        classDiagramGenerator.addUnits(domain.getAllEfferents());
        System.out.println(classDiagramGenerator.print());

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

        assertThat(classDiagramGenerator.print(), is(resultPrint));
    }

    @Test
    @DisplayName("should be return only packages")
    void shouldBeReturnOnlyPackages() {
        Domain domain = new Domain("mastermind");
        domain.addPackage("package1").addUnit("unit1").addUnit("unit2").addUsed("used").addPackage("package2").addUnit("unit3")
                .addPackage("package3").addUnit("unit4").addUsed("unit1");

        classDiagramGenerator.addDomain(domain);
        System.out.println(classDiagramGenerator.printPackage());
        String resultPrint = "package package1 {} \n" +
                "package package2 {} \n" +
                "package package3 {} \n" +
                "package3 ..> package1\n";
        assertThat(classDiagramGenerator.printPackage(), is(resultPrint));
    }

    @Test
    @DisplayName("should be return concrete package and one level relation to other packages")
    void shouldBeReturnConcretePackageAndOneLevelRelationToOtherPackages() {
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

        System.out.println(classDiagramGenerator.addDomain(domain).print("mastermind"));

        assertThat(classDiagramGenerator.print("mastermind"), is(result));
    }
    
    @Test
    @DisplayName("should be return one unit and base with the same name and different package")
    void shouldBeReturnOneUnitAndBaseWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addBase("unit", "secondpackage");
        classDiagramGenerator.addDomain(domain);

        String result = "class mypackage.unit\n" +
                "class secondpackage.unit\n" +
                "secondpackage.unit <|-- mypackage.unit\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    @DisplayName("should be return one unit and part with the same name and different package")
    void shouldBeReturnOneUnitAndPartWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addPart("unit", "secondpackage");

        classDiagramGenerator.addDomain(domain);

        String result = "class mypackage.unit\n" +
                "class secondpackage.unit\n" +
                "mypackage.unit *--> secondpackage.unit\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }
    @Test
    @DisplayName("should be return one unit and element with the same name and different package")
    void shouldBeReturnOneUnitAndElementWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addElement("unit", "secondpackage");

        classDiagramGenerator.addDomain(domain);

        String result = "class mypackage.unit\n" +
                "class secondpackage.unit\n" +
                "mypackage.unit o--> secondpackage.unit\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    @DisplayName("should be return one unit and associate with the same name and different package")
    void shouldBeReturnOneUnitAndAssociateWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addAssociate("unit", "secondpackage");

        classDiagramGenerator.addDomain(domain);

        String result = "class mypackage.unit\n" +
                "class secondpackage.unit\n" +
                "mypackage.unit --> secondpackage.unit\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    @DisplayName("should be return one unit and used with the same name and different package")
    void shouldBeReturnOneUnitAndUsedWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addUsed("unit", "secondpackage");

        classDiagramGenerator.addDomain(domain);

        String result = "class mypackage.unit\n" +
                "class secondpackage.unit\n" +
                "mypackage.unit ..> secondpackage.unit\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    @DisplayName("should be return Entity annotation color")
    void shouldBeReturnEntityAnnotationColor() {
        Domain domain = new Domain("domain");
        domain.addUnit("Entity", "mypackage").addAnnotation("Entity");
        classDiagramGenerator.addDomain(domain);

        String result="class mypackage.Entity #FFFF00\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    @DisplayName("should be return Component annotation color")
    void shouldBeReturnComponentAnnotationColor() {
        Domain domain = new Domain("domain");
        domain.addUnit("Component", "mypackage").addAnnotation("Component");
        classDiagramGenerator.addDomain(domain);

        String result="class mypackage.Component #FF9900\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    @DisplayName("should be return Controller annotation color")
    void shouldBeReturnControllerAnnotationColor() {
        Domain domain = new Domain("domain");
        domain.addUnit("Controller", "mypackage").addAnnotation("Controller");
        classDiagramGenerator.addDomain(domain);

        String result="class mypackage.Controller #33CC00\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    @DisplayName("should be return RestController annotation color")
    void shouldBeReturnRestControllerAnnotationColor() {
        Domain domain = new Domain("domain");
        domain.addUnit("RestController", "mypackage").addAnnotation("RestController");
        classDiagramGenerator.addDomain(domain);

        String result="class mypackage.RestController #66FF33\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    @DisplayName("should be return Service annotation color")
    void shouldBeReturnServiceAnnotationColor() {
        Domain domain = new Domain("domain");
        domain.addUnit("Service", "mypackage").addAnnotation("Service");
        classDiagramGenerator.addDomain(domain);

        String result="class mypackage.Service #FF3300\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }

    @Test
    @DisplayName("should be return Repository annotation color")
    void shouldBeReturnRepositoryAnnotationColor() {
        Domain domain = new Domain("domain");
        domain.addUnit("Repository", "mypackage").addAnnotation("Repository");
        classDiagramGenerator.addDomain(domain);

        String result="class mypackage.Repository #3399FF\n";

        assertThat(classDiagramGenerator.print(), is(result));
    }

}