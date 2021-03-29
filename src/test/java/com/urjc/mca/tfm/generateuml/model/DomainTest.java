package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource({
            "Part",
            "Element",
            "Associate",
            "Used"
    })
    void shouldBeReturnTheFirstSourceUnitAdded(String unit) {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addPart(unit).addPart(unit);

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().size(), is(1));
        assertThat(unitDomain.getPartList().contains(new Unit(unit)), is(true));
    }

    @ParameterizedTest
    @CsvSource({
            "Part, Part1",
            "Element, Element1",
            "Associate, Associate1",
            "Used, Used1"
    })
    void shouldBeReturnTheFirstAndSecondSourceUnitAdded(String unit, String unit1) {

        Domain domain = new Domain("domain");
        domain.addUnit("unit").addPart(unit).addPart(unit1);

        Unit unitDomain = domain.getUnit("unit");

        assertThat(unitDomain.getPartList().size(), is(2));
        assertThat(unitDomain.getPartList().contains(new Unit(unit)), is(true));
        assertThat(unitDomain.getPartList().contains(new Unit(unit1)), is(true));
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
    @DisplayName("should be return efferent and name for unit in package")
    void shouldBeReturnEfferentAndNameForUnitInPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addBase("base").addUnit("base").addUsed("used");

        List<Unit> list = domain.getAllEfferents();
        assertThat(list.get(0).getMyPackage(), is("efferent unit"));
        assertThat(list.get(1).getMyPackage(), is("efferent base"));
        assertThat(list.get(2).getMyPackage(), is("efferent used"));
    }

    @Test
    @DisplayName("should be return afferent and name for unit in package")
    void shouldBeReturnAfferentAndNameForUnitInPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addBase("base").addUnit("base").addUsed("used");

        List<Unit> list = domain.getAllAfferent();
        assertThat(list.get(0).getMyPackage(), is("afferent base"));
        assertThat(list.get(1).getMyPackage(), is("afferent used"));
    }

    @Test
    @DisplayName("should be return unit with package at create unit")
    void shouldBeReturnUnitWithPackageAtCreateUnit() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage");

        Unit unit = domain.getUnit("unit");

        assertThat(unit.getMyPackage(), is("mypackage"));
        assertThat(unit.name, is("unit"));
    }

    @Test
    @DisplayName("should be return two units with the same name and different package")
    void shouldBeReturnTwoUnitsWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addUnit("unit", "secondpackage");

        List<Unit> unitList = domain.getUnitList();

        assertThat(unitList.get(0).getMyPackage(), is("mypackage"));
        assertThat(unitList.get(0).name, is("unit"));
        assertThat(unitList.get(1).getMyPackage(), is("secondpackage"));
        assertThat(unitList.get(1).name, is("unit"));
    }

    @Test
    @DisplayName("should be return one unit and base with the same name and different package")
    void shouldBeReturnOneUnitAndBaseWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addBase("unit", "secondpackage");

        List<Unit> unitList = domain.getUnitList();

        assertThat(unitList.get(0).getMyPackage(), is("mypackage"));
        assertThat(unitList.get(0).name, is("unit"));
        assertThat(unitList.get(1).getMyPackage(), is("secondpackage"));
        assertThat(unitList.get(1).name, is("unit"));
    }

    @Test
    @DisplayName("should be return one unit and part with the same name and different package")
    void shouldBeReturnOneUnitAndPartWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addPart("unit", "secondpackage");

        List<Unit> unitList = domain.getUnitList();

        assertThat(unitList.get(0).getMyPackage(), is("mypackage"));
        assertThat(unitList.get(0).name, is("unit"));
        assertThat(unitList.get(1).getMyPackage(), is("secondpackage"));
        assertThat(unitList.get(1).name, is("unit"));
    }

    @Test
    @DisplayName("should be return one unit and element with the same name and different package")
    void shouldBeReturnOneUnitAndElementWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addElement("unit", "secondpackage");

        List<Unit> unitList = domain.getUnitList();

        assertThat(unitList.get(0).getMyPackage(), is("mypackage"));
        assertThat(unitList.get(0).name, is("unit"));
        assertThat(unitList.get(1).getMyPackage(), is("secondpackage"));
        assertThat(unitList.get(1).name, is("unit"));
    }

    @Test
    @DisplayName("should be return one unit and associate with the same name and different package")
    void shouldBeReturnOneUnitAndAssociateWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addAssociate("unit", "secondpackage");

        List<Unit> unitList = domain.getUnitList();

        assertThat(unitList.get(0).getMyPackage(), is("mypackage"));
        assertThat(unitList.get(0).name, is("unit"));
        assertThat(unitList.get(1).getMyPackage(), is("secondpackage"));
        assertThat(unitList.get(1).name, is("unit"));
    }

    @Test
    @DisplayName("should be return one unit and used with the same name and different package")
    void shouldBeReturnOneUnitAndUsedWithTheSameNameAndDifferentPackage() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit", "mypackage").addUsed("unit", "secondpackage");

        List<Unit> unitList = domain.getUnitList();

        assertThat(unitList.get(0).getMyPackage(), is("mypackage"));
        assertThat(unitList.get(0).name, is("unit"));
        assertThat(unitList.get(1).getMyPackage(), is("secondpackage"));
        assertThat(unitList.get(1).name, is("unit"));
    }

    @Test
    @DisplayName("should be return the annotation added")
    void shouldBeReturnTheAnnotationAdded() {
        Domain domain = new Domain("domain");
        domain.addUnit("unit").addAnnotation("annotation");

        List<Unit> units = domain.getUnitList();

        assertThat(units.get(0).getAnnotation(), is("annotation"));
    }
}