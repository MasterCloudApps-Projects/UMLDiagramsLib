package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class ModelTest {

    @Test
    void shouldBeReturnTheFirstUnitWhenAddClass(){

        Model model = new Model("model");
        model.addUnit("unit").addUnit("unit");

        assertThat(model.getUnitList().size(), is(1));
        assertThat(model.getUnitList().get(0).name, is("unit"));
    }

    @Test
    void shouldBeReturnTheFirstBaseUnit(){

        Model model = new Model("model");
        model.addUnit("unit").addBase("Base").addBase("Base");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getBase().size(), is(1));
        assertThat(unitModel.getBase().contains(new Unit("Base")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstBaseAndSecondBaseUnit(){

        Model model = new Model("model");
        model.addUnit("unit").addBase("Base").addBase("Base2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getBase().size(), is(2));
        assertThat(unitModel.getBase().contains(new Unit("Base")), is(true));
        assertThat(unitModel.getBase().contains(new Unit("Base2")), is(true));
    }

    @Test
    void shouldBeReturnTheFirstPartUnit(){

        Model model = new Model("model");
        model.addUnit("unit").addPart("Part").addPart("Part");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Part")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondPartUnit(){

        Model model = new Model("model");
        model.addUnit("unit").addPart("Part").addPart("Part2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Part")), is (true));
        assertThat(unitModel.getPartList().contains(new Unit("Part2")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstElementUnit(){

        Model model = new Model("model");
        model.addUnit("unit").addPart("Element").addPart("Element");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Element")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondElementUnit(){

        Model model = new Model("model");
        model.addUnit("unit").addPart("Element").addPart("Element2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Element")), is (true));
        assertThat(unitModel.getPartList().contains(new Unit("Element2")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAssociateUnit(){

        Model model = new Model("model");
        model.addUnit("unit").addPart("Associate").addPart("Associate");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Associate")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondAssociateUnit(){

        Model model = new Model("model");
        model.addUnit("unit").addPart("Associate").addPart("Associate2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Associate")), is (true));
        assertThat(unitModel.getPartList().contains(new Unit("Associate2")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstUsedUnit(){

        Model model = new Model("model");
        model.addUnit("unit").addPart("Used").addPart("Used");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(1));
        assertThat(unitModel.getPartList().contains(new Unit("Used")), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondUsedUnit(){

        Model model = new Model("model");
        model.addUnit("unit").addPart("Used").addPart("Used2");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getPartList().size(), is(2));
        assertThat(unitModel.getPartList().contains(new Unit("Used")), is (true));
        assertThat(unitModel.getPartList().contains(new Unit("Used2")), is (true));
    }

    @Test
    void shouldBeReturnBasePartElementAssociateUsedWithStringCreateMethod(){

        Model model = new Model("model");
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
    public void shouldBeReturnUnitWithPackage(){
        Model model = new Model("");
        model.addPackage("package").addUnit("unit");

        Unit unitModel = model.getUnit("unit");
        assertThat(unitModel.getMyPackage(), is ("package"));
    }

    @Test
    public void shouldBeReturnUnitWithoutPackage(){
        Model model = new Model("");
        model.addPackage("package").addUnit("unit").nonPackage().addUnit("unit2");

        Unit unitModel = model.getUnit("unit");
        Unit unit2Model = model.getUnit("unit2");

        assertThat(unitModel.getMyPackage(), is ("package"));
        assertThat(unit2Model.getMyPackage(), is (""));
    }

    @Test
    public void shouldBeReturnAUnitWithAttribute(){
        Model model = new Model("model");
        model.addUnit("unit").addAttribute("attribute");

        Unit unitModel = model.getUnit("unit");

        assertThat(unitModel.getAttributes().contains(new Attribute("attribute")), is(true));

    }
}