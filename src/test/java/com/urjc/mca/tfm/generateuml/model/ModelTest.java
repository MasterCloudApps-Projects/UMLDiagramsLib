package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class ModelTest {

    @Test
    void shouldBeReturnTheFirstEntityWhenAddClass(){

        Model model = new Model("model");
        model.addEntity("Entity").addEntity("Entity");

        assertThat(model.getEntityList().size(), is(1));
        assertThat(model.getEntityList().get(0), is("Entity"));
    }

    @Test
    void shouldBeReturnTheFirstBaseEntity(){

        Model model = new Model("model");
        model.addEntity("Entity").addBase("Base").addBase("Base");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getBase().size(), is(1));
        assertThat(entityModel.getBase().contains("Base"), is(true));
    }

    @Test
    void shouldBeReturnTheFirstBaseAndSecondBaseEntity(){

        Model model = new Model("model");
        model.addEntity("Entity").addBase("Base").addBase("Base2");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getBase().size(), is(2));
        assertThat(entityModel.getBase().contains("Base"), is(true));
        assertThat(entityModel.getBase().contains("Base2"), is(true));
    }

    @Test
    void shouldBeReturnTheFirstPartEntity(){

        Model model = new Model("model");
        model.addEntity("Entity").addPart("Part").addPart("Part");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains("Part"), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondPartEntity(){

        Model model = new Model("model");
        model.addEntity("Entity").addPart("Part").addPart("Part2");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains("Part"), is (true));
        assertThat(entityModel.getPartList().contains("Part2"), is (true));
    }

    @Test
    void shouldBeReturnTheFirstElementEntity(){

        Model model = new Model("model");
        model.addEntity("Entity").addPart("Element").addPart("Element");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains("Element"), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondElementEntity(){

        Model model = new Model("model");
        model.addEntity("Entity").addPart("Element").addPart("Element2");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains("Element"), is (true));
        assertThat(entityModel.getPartList().contains("Element2"), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAssociateEntity(){

        Model model = new Model("model");
        model.addEntity("Entity").addPart("Associate").addPart("Associate");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains("Associate"), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondAssociateEntity(){

        Model model = new Model("model");
        model.addEntity("Entity").addPart("Associate").addPart("Associate2");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains("Associate"), is (true));
        assertThat(entityModel.getPartList().contains("Associate2"), is (true));
    }

    @Test
    void shouldBeReturnTheFirstUsedEntity(){

        Model model = new Model("model");
        model.addEntity("Entity").addPart("Used").addPart("Used");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains("Used"), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondUsedEntity(){

        Model model = new Model("model");
        model.addEntity("Entity").addPart("Used").addPart("Used2");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains("Used"), is (true));
        assertThat(entityModel.getPartList().contains("Used2"), is (true));
    }

    @Test
    void shouldBeReturnBasePartElementAssociateUsedWithStringCreateMethod(){

        Model model = new Model("model");
        model.addEntity("Entity").addBase("Base").addAssociate("Associate").addElement("Element").addPart("Part")
                .addUsed("Used");

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().contains(new Unit("Part")), is (true));
        assertThat(entityModel.getBase().contains(new Unit("Base")), is (true));
        assertThat(entityModel.getElements().contains(new Unit("Element")), is (true));
        assertThat(entityModel.getAssociates().contains(new Unit("Associate")), is (true));
        assertThat(entityModel.getUsed().contains(new Unit("Used")), is (true));
    }
}