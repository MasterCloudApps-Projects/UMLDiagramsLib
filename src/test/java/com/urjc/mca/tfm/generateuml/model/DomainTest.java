package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DomainTest {

    @Test
    void shouldBeReturnTheFirstUnitWhenAddClass() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addUnit("unit");

        assertThat(domain.getUnitList().size(), is(1));
        assertThat(domain.getUnitList().get(0).name, is("unit"));
    }

    @Test
    @DisplayName("should be return unit abstract")
    void shouldBeReturnUnitAbstract() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addUnit("unit").setAbstractUnit();

        assertThat(domain.getUnit("unit").getAbstractUnit(), is(true));
    }

    @Test
    void shouldBeReturnTheFirstBaseUnit() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addBase("Base").addBase("Base");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getBase().size(), is(1));
        assertThat(unitDomain.getBase().contains(new Unit("Base")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstBaseAndSecondBaseUnit() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addBase("Base").addBase("Base2");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getBase().size(), is(2));
        assertThat(unitDomain.getBase().contains(new Unit("Base")), is(true));
        assertThat(unitDomain.getBase().contains(new Unit("Base2")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstPartUnit() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addPart("Part").addPart("Part");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().size(), is(1));
        assertThat(unitDomain.getPartList().contains(new Unit("Part")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondPartUnit() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addPart("Part").addPart("Part2");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().size(), is(2));
        assertThat(unitDomain.getPartList().contains(new Unit("Part")), is(true));
        assertThat(unitDomain.getPartList().contains(new Unit("Part2")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstElementUnit() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addPart("Element").addPart("Element");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().size(), is(1));
        assertThat(unitDomain.getPartList().contains(new Unit("Element")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondElementUnit() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addPart("Element").addPart("Element2");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().size(), is(2));
        assertThat(unitDomain.getPartList().contains(new Unit("Element")), is(true));
        assertThat(unitDomain.getPartList().contains(new Unit("Element2")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstAssociateUnit() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addPart("Associate").addPart("Associate");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().size(), is(1));
        assertThat(unitDomain.getPartList().contains(new Unit("Associate")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondAssociateUnit() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addPart("Associate").addPart("Associate2");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().size(), is(2));
        assertThat(unitDomain.getPartList().contains(new Unit("Associate")), is(true));
        assertThat(unitDomain.getPartList().contains(new Unit("Associate2")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstUsedUnit() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addPart("Used").addPart("Used");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().size(), is(1));
        assertThat(unitDomain.getPartList().contains(new Unit("Used")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondUsedUnit() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addPart("Used").addPart("Used2");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().size(), is(2));
        assertThat(unitDomain.getPartList().contains(new Unit("Used")), is(true));
        assertThat(unitDomain.getPartList().contains(new Unit("Used2")), is(true));
    }

    @Test
    void shouldBeReturnBasePartElementAssociateUsedWithStringCreateMethod() {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addBase("Base").addAssociate("Associate").addElement("Element").addPart("Part")
                .addUsed("Used");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().contains(new Unit("Part")), is(true));
        assertThat(unitDomain.getBase().contains(new Unit("Base")), is(true));
        assertThat(unitDomain.getElements().contains(new Unit("Element")), is(true));
        assertThat(unitDomain.getAssociates().contains(new Unit("Associate")), is(true));
        assertThat(unitDomain.getUsed().contains(new Unit("Used")), is(true));
    }

    @Test
    void shouldBeReturnUnitWithPackage() {
        Domain domain = new Domain("");
        domain.addPackage("package").addUnit("unit");

        Unit unitDomain = domain.getUnit("unit");
        assertThat(unitDomain.getMyPackage(), is("package"));
    }

    @Test
    void shouldBeReturnUnitWithoutPackage() {
        Domain domain = new Domain("");
        domain.addPackage("package").addUnit("unit").nonPackage().addUnit("unit2");

        Unit unitDomain = domain.getUnit("unit");
        Unit unit2Domain = domain.getUnit("unit2");

        assertThat(unitDomain.getMyPackage(), is("package"));
        assertThat(unit2Domain.getMyPackage(), is(""));
    }

    @Test
    void shouldBeReturnAUnitWithAttribute() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute");

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getAttributes().contains(new Attribute("attribute")), is(true));

    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityPublic() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PUBLIC);

        Unit unitDomain = domain.getUnit("unit");
        Attribute attribute = unitDomain.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);
        assertThat(unitDomain.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.getVisibility(), is(Visibility.PUBLIC));
    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityPrivate() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PRIVATE);

        Unit unitModel = domain.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);
        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.getVisibility(), is(Visibility.PRIVATE));
    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityProtected() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PROTECTED);

        Unit unitModel = domain.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);
        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.getVisibility(), is(Visibility.PROTECTED));
    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PACKAGE);

        Unit unitModel = domain.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.getVisibility(), is(Visibility.PACKAGE));
    }

    @Test
    void shouldBeReturnStringTypeAttribute() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PACKAGE).setType("String");

        Unit unitModel = domain.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.getType(), is("String"));
    }

    @Test
    void shouldBeReturnStaticAttribute() {
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PACKAGE).setType("String").setStatic(true);

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.getType(), is("String"));
        assertThat(attribute.isStaticAttribute(), is(true));
    }

    @Test
    void shouldBeReturnNotStaticAttribute() {
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PACKAGE).setType("String");

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.getType(), is("String"));
        assertThat(attribute.isStaticAttribute(), is(false));
    }

    @Test
    void shouldBeReturnFunction() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function");

        Unit unitModel = domain.getUnit("unit");

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
    }

    @ParameterizedTest
    @EnumSource(Visibility.class)
    void shouldBeReturnFunctionWithVisibility(Visibility visibility) {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").addVisibility(visibility);

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.getVisibility(), is(visibility));
    }

    @Test
    void shouldBeReturnFunctionAndReturnType() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").addReturnType("String");

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.getReturnTypeName(), is("String"));
    }

    @Test
    void shouldBeReturnFunctionPublicVisibilityAndReturnType() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String");

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.getVisibility(), is(Visibility.PUBLIC));
        assertThat(function.getReturnTypeName(), is("String"));
    }

    @Test
    void shouldBeReturnFunctionWithParameters() {
        Domain domain = new Domain("domain");
        String[] parameters = {"String", "int"};
        domain.addUnit("unit").addFunction("function").addParameters(parameters);

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.getParameters(), is(parameters));
    }

    @Test
    void shouldBeReturnFunctionPublicVisibilityReturnTypeStringWithParameters() {
        Domain domain = new Domain("domain");
        String[] parameters = {"String", "int"};
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String").addParameters(parameters);

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.getVisibility(), is(Visibility.PUBLIC));
        assertThat(function.getReturnTypeName(), is("String"));
        assertThat(function.getParameters(), is(parameters));
    }

    @Test
    void shouldBeReturnStaticFunction() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").setStatic(true);

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.isStaticFunction(), is(true));
    }

    @Test
    void shouldBeReturnStaticFunctionParametersPublicVisibilityAndStringReturnType() {
        Domain domain = new Domain("domain");
        String[] parameters = {"String", "int"};
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String")
                .addParameters(parameters).setStatic(true);

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.getVisibility(), is(Visibility.PUBLIC));
        assertThat(function.getReturnTypeName(), is("String"));
        assertThat(function.getParameters(), is(parameters));
        assertThat(function.isStaticFunction(), is(true));
    }

    @Test
    void shouldBeReturnNonStaticFunction() {
        Domain domain = new Domain("domain");
        String[] parameters = {"String", "int"};
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String")
                .addParameters(parameters).setStatic(false);
//        domain.addUnit("unit2")
//                .addFunction(nombre).static().return("int")
//                .addUnit("x").parameters("a,v")
//                .addFunction(nombre).static().parameters("a","b").visibility(Visibility.PUBLIC)

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.getVisibility(), is(Visibility.PUBLIC));
        assertThat(function.getReturnTypeName(), is("String"));
        assertThat(function.getParameters(), is(parameters));
        assertThat(function.isStaticFunction(), is(false));
    }

    @Test
    @DisplayName("should be return null pointer exception")
    void shouldBeReturnNullPointerException() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").addUnit("unit 2");
        assertThrows(NullPointerException.class, () -> domain.setStatic(true));
    }
    
    @Test
    void shouldBeReturnAnActor() {
        Domain domain = new Domain("domain");
        domain.addActor("Actor");

        assertThat(domain.getActorList().size(), is(1));
        assertThat(domain.getActorList().get(0).name, is("Actor"));
    }

    @Test
    void shouldBeReturnATwoActors() {
        Domain domain = new Domain("domain");
        domain.addActor("Actor").addActor("Actor2");

        assertThat(domain.getActorList().size(), is(2));
        assertThat(domain.getActorList().get(0).name, is("Actor"));
        assertThat(domain.getActorList().get(1).name, is("Actor2"));
    }

    @Test
    void shouldBeReturnAUseCase() {
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCase("useCase");

        assertThat(domain.getActorList().size(), is(1));
        assertThat(domain.getActorList().get(0).name, is("actor"));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseLeaf("useCase")), is(true));
    }

    @Test
    void shouldBeReturnATwoUseCases() {
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCase("useCase").addUseCase("useCase2");

        assertThat(domain.getActorList().size(), is(1));
        assertThat(domain.getActorList().get(0).name, is("actor"));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseLeaf("useCase")), is(true));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseLeaf("useCase2")), is(true));
    }

    @Test
    void shouldBeReturnATreeUseCasesAndTwoActors() {
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCase("useCase").addUseCase("useCase2").addActor("actor2").addUseCase("useCase3");

        assertThat(domain.getActorList().size(), is(2));
        assertThat(domain.getActorList().get(0).name, is("actor"));
        assertThat(domain.getActorList().get(1).name, is("actor2"));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseLeaf("useCase")), is(true));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseLeaf("useCase2")), is(true));
        assertThat(domain.getActor("actor2").getUseCases().contains(new UseCaseLeaf("useCase3")), is(true));
    }

    @Test
    void shouldBeReturnATwoUseCasesAndTwoActors() {
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCase("useCase").addUseCase("useCase2").addActor("actor2").addUseCase("useCase2");

        assertThat(domain.getActorList().size(), is(2));
        assertThat(domain.getActorList().get(0).name, is("actor"));
        assertThat(domain.getActorList().get(1).name, is("actor2"));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseLeaf("useCase")), is(true));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseLeaf("useCase2")), is(true));
        assertThat(domain.getActor("actor2").getUseCases().contains(new UseCaseLeaf("useCase2")), is(true));
        assertThat(domain.getActor("actor").getUseCases().size(), is(2));
        assertThat(domain.getActor("actor2").getUseCases().size(), is(1));

    }

    @Test
    void shouldBeReturnTwoUseCase() {
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCase("case1").addUseCaseComposite("case1").addUseCase("case2");

        assertThat(domain.getActorList().size(), is(1));
        assertThat(domain.getActorList().get(0).name, is("actor"));
        assertThat(domain.getActor("actor").getUseCases().size(), is(2));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseLeaf("case1")), is(true));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseLeaf("case2")), is(true));

    }

    @Test
    void shouldBeReturnTwoUseCaseLeafAndComposite() {
        Domain domain = new Domain("domain");
        domain.addActor("actor").addUseCaseComposite("case1").addUseCase("case2");

        assertThat(domain.getActorList().size(), is(1));
        assertThat(domain.getActorList().get(0).name, is("actor"));
        assertThat(domain.getActor("actor").getUseCases().size(), is(2));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseComposite("case1")), is(true));
        assertThat(domain.getActor("actor").getUseCases().contains(new UseCaseLeaf("case2")), is(true));

    }

    @Test
    @DisplayName("should be return efferent and name for unit in package")
    void shouldBeReturnEfferentAndNameForUnitInPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addBase("base").addUnit("base").addUsed("used");

        List<Unit> list = domain.getAllEfferents();
        assertThat(list.get(0).getMyPackage(), is ("efferent unit"));
        assertThat(list.get(1).getMyPackage(), is ("efferent base"));
        assertThat(list.get(2).getMyPackage(), is ("efferent used"));
    }

}