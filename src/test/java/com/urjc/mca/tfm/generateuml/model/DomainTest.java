package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DomainTest {

    @Test
    void shouldBeReturnTheFirstUnitWhenAddClass() {

        Domain model = new Domain("model");
        model.addUnit("unit").addUnit("unit");

        assertThat(model.getUnitList().size(), is(1));
        assertThat(model.getUnitList().get(0).name, is("unit"));
    }

    @Test
    void shouldBeReturnTheFirstBaseUnit() {

        Domain model = new Domain("model");
        model.addUnit("unit").addBase("Base").addBase("Base");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getBase().size(), is(1));
        assertThat(unitModel.getBase().contains(new Unit("Base")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstBaseAndSecondBaseUnit() {

        Domain model = new Domain("model");
        model.addUnit("unit").addBase("Base").addBase("Base2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getBase().size(), is(2));
        assertThat(unitModel.getBase().contains(new Unit("Base")), is(true));
        assertThat(unitModel.getBase().contains(new Unit("Base2")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstPartUnit() {

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Part").addPart("Part");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Part")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondPartUnit() {

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Part").addPart("Part2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Part")), is(true));
        assertThat(unitModel.getPartList().contains(new Unit("Part2")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstElementUnit() {

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Element").addPart("Element");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Element")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondElementUnit() {

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Element").addPart("Element2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Element")), is(true));
        assertThat(unitModel.getPartList().contains(new Unit("Element2")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstAssociateUnit() {

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Associate").addPart("Associate");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Associate")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondAssociateUnit() {

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Associate").addPart("Associate2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Associate")), is(true));
        assertThat(unitModel.getPartList().contains(new Unit("Associate2")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstUsedUnit() {

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Used").addPart("Used");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Used")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondUsedUnit() {

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Used").addPart("Used2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Used")), is(true));
        assertThat(unitModel.getPartList().contains(new Unit("Used2")), is(true));
    }

    @Test
    void shouldBeReturnBasePartElementAssociateUsedWithStringCreateMethod() {

        Domain model = new Domain("model");
        model.addUnit("unit").addBase("Base").addAssociate("Associate").addElement("Element").addPart("Part")
                .addUsed("Used");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().contains(new Unit("Part")), is(true));
        assertThat(unitModel.getBase().contains(new Unit("Base")), is(true));
        assertThat(unitModel.getElements().contains(new Unit("Element")), is(true));
        assertThat(unitModel.getAssociates().contains(new Unit("Associate")), is(true));
        assertThat(unitModel.getUsed().contains(new Unit("Used")), is(true));
    }

    @Test
    void shouldBeReturnUnitWithPackage() {
        Domain model = new Domain("");
        model.addPackage("package").addUnit("unit");

        Unit unitModel = model.getUnit("unit");
        assertThat(unitModel.getMyPackage(), is("package"));
    }

    @Test
    void shouldBeReturnUnitWithoutPackage() {
        Domain model = new Domain("");
        model.addPackage("package").addUnit("unit").nonPackage().addUnit("unit2");

        Unit unitModel = model.getUnit("unit");
        Unit unit2Model = model.getUnit("unit2");

        assertThat(unitModel.getMyPackage(), is("package"));
        assertThat(unit2Model.getMyPackage(), is(""));
    }

    @Test
    void shouldBeReturnAUnitWithAttribute() {
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));

    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityPublic() {
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PUBLIC);

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);
        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.visibility, is(Visibility.PUBLIC));
    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityPrivate() {
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PRIVATE);

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);
        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.visibility, is(Visibility.PRIVATE));
    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityProtected() {
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PROTECTED);

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);
        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.visibility, is(Visibility.PROTECTED));
    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityPackage() {
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PACKAGE);

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.visibility, is(Visibility.PACKAGE));
    }

    //    @ParameterizedTest
//    @EnumSource(Type.class)
    @Test
    void shouldBeReturnStringTypeAttribute() {
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PACKAGE).setType("String");

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.type, is("String"));
    }

    @Test
    void shouldBeReturnStaticAttribute() {
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PACKAGE).setType("String").setStatic(true);

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.type, is("String"));
        assertThat(attribute.staticAttribute, is(true));
    }

    @Test
    void shouldBeReturnNotStaticAttribute() {
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute").addVisibility(Visibility.PACKAGE).setType("String");

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.type, is("String"));
        assertThat(attribute.staticAttribute, is(false));
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
        assertThat(function.visibility, is(visibility));
    }

    @Test
    void shouldBeReturnFunctionAndReturnType() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").addReturnType("String");

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.returnTypeName, is("String"));
    }

    @Test
    void shouldBeReturnFunctionPublicVisibilityAndReturnType() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String");

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.visibility, is(Visibility.PUBLIC));
        assertThat(function.returnTypeName, is("String"));
    }

    @Test
    void shouldBeReturnFunctionWithParameters() {
        Domain domain = new Domain("domain");
        String[] parameters = {"String", "int"};
        domain.addUnit("unit").addFunction("function").addParameters(parameters);

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.parameters, is(parameters));
    }

    @Test
    void shouldBeReturnFunctionPublicVisibilityReturnTypeStringWithParameters() {
        Domain domain = new Domain("domain");
        String[] parameters = {"String", "int"};
        domain.addUnit("unit").addFunction("function").addVisibility(Visibility.PUBLIC).addReturnType("String").addParameters(parameters);

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.visibility, is(Visibility.PUBLIC));
        assertThat(function.returnTypeName, is("String"));
        assertThat(function.parameters, is(parameters));
    }

    @Test
    void shouldBeReturnStaticFunction() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addFunction("function").setStatic(true);

        Unit unitModel = domain.getUnit("unit");
        Function function = unitModel.getFunctions().stream().filter(new Function("function")::equals).findAny().orElse(null);

        assertThat(unitModel.getFunctions().contains(new Function("function")), is(true));
        assertThat(function.staticFunction, is(true));
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
        assertThat(function.visibility, is(Visibility.PUBLIC));
        assertThat(function.returnTypeName, is("String"));
        assertThat(function.parameters, is(parameters));
        assertThat(function.staticFunction, is(true));
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
        assertThat(function.visibility, is(Visibility.PUBLIC));
        assertThat(function.returnTypeName, is("String"));
        assertThat(function.parameters, is(parameters));
        assertThat(function.staticFunction, is(false));
    }

    @Test
    @DisplayName("should be return null pointer exception")
    void shouldBeReturnNullPointerException() {
        Domain domain = new Domain("domain");
        assertThrows(NullPointerException.class, () ->{
            domain.addUnit("unit").addFunction("function").addUnit("unit 2").setStatic(true);
                });
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

}