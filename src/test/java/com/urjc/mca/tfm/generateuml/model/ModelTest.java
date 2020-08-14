package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class ModelTest {

    @Test
    void shouldBeReturnTheFirstUnitWhenAddClass(){

        Domain model = new Domain("model");
        model.addUnit("unit").addUnit("unit");

        assertThat(model.getUnitList().size(), is(1));
        assertThat(model.getUnitList().get(0).name, is("unit"));
    }

    @Test
    void shouldBeReturnTheFirstBaseUnit(){

        Domain model = new Domain("model");
        model.addUnit("unit").addBase("Base").addBase("Base");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getBase().size(), is(1));
        assertThat(unitModel.getBase().contains(new Unit("Base")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstBaseAndSecondBaseUnit(){

        Domain model = new Domain("model");
        model.addUnit("unit").addBase("Base").addBase("Base2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getBase().size(), is(2));
        assertThat(unitModel.getBase().contains(new Unit("Base")), is(true));
        assertThat(unitModel.getBase().contains(new Unit("Base2")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstPartUnit(){

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Part").addPart("Part");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Part")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondPartUnit(){

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Part").addPart("Part2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Part")), is (true));
        assertThat(unitModel.getPartList().contains(new Unit("Part2")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstElementUnit(){

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Element").addPart("Element");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Element")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondElementUnit(){

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Element").addPart("Element2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Element")), is (true));
        assertThat(unitModel.getPartList().contains(new Unit("Element2")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAssociateUnit(){

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Associate").addPart("Associate");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Associate")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondAssociateUnit(){

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Associate").addPart("Associate2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Associate")), is (true));
        assertThat(unitModel.getPartList().contains(new Unit("Associate2")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstUsedUnit(){

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Used").addPart("Used");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Used")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondUsedUnit(){

        Domain model = new Domain("model");
        model.addUnit("unit").addPart("Used").addPart("Used2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Used")), is (true));
        assertThat(unitModel.getPartList().contains(new Unit("Used2")), is (true));
    }

    @Test
    void shouldBeReturnBasePartElementAssociateUsedWithStringCreateMethod(){

        Domain model = new Domain("model");
        model.addUnit("unit").addBase("Base").addAssociate("Associate").addElement("Element").addPart("Part")
                .addUsed("Used");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().contains(new Unit("Part")), is (true));
        assertThat(unitModel.getBase().contains(new Unit("Base")), is (true));
        assertThat(unitModel.getElements().contains(new Unit("Element")), is (true));
        assertThat(unitModel.getAssociates().contains(new Unit("Associate")), is (true));
        assertThat(unitModel.getUsed().contains(new Unit("Used")), is (true));
    }

    @Test
    void shouldBeReturnUnitWithPackage(){
        Domain model = new Domain("");
        model.addPackage("package").addUnit("unit");

        Unit unitModel = model.getUnit("unit");
        assertThat(unitModel.getMyPackage(), is ("package"));
    }

    @Test
    void shouldBeReturnUnitWithoutPackage(){
        Domain model = new Domain("");
        model.addPackage("package").addUnit("unit").nonPackage().addUnit("unit2");

        Unit unitModel = model.getUnit("unit");
        Unit unit2Model = model.getUnit("unit2");

        assertThat(unitModel.getMyPackage(), is ("package"));
        assertThat(unit2Model.getMyPackage(), is (""));
    }

    @Test
    void shouldBeReturnAUnitWithAttribute(){
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));

    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityPublic(){
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute",Visibility.PUBLIC);

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);
        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.visibility, is(Visibility.PUBLIC));
    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityPrivate(){
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute",Visibility.PRIVATE);

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);
        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.visibility, is(Visibility.PRIVATE));
    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityProtected(){
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute",Visibility.PROTECTED);

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);
        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.visibility, is(Visibility.PROTECTED));
    }

    @Test
    void shouldBeReturnAUnitWithAttributeAndVisibilityPackage(){
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute",Visibility.PACKAGE);

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.visibility, is(Visibility.PACKAGE));
    }

//    @ParameterizedTest
//    @EnumSource(Type.class)
    @Test
    void shouldBeReturnStringTypeAttribute(){
        Domain model = new Domain("model");
        model.addUnit("unit").addAttribute("attribute",Visibility.PACKAGE, "String");

        Unit unitModel = model.getUnit("unit");
        Attribute attribute = unitModel.getAttributes().stream().filter(new Attribute("attribute")::equals).findAny().orElse(null);

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));
        assertThat(attribute.type, is("String"));
    }
}