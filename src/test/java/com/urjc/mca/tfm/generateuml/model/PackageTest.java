package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class PackageTest {

    @Test
    void shouldBeReturnTheFirstEntityWhenAddClass(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit secondEntity = new Unit("Entity");

        model.addEntity(firstEntity).addEntity(secondEntity);

        assertThat(model.getEntityList().size(), is(1));
        assertThat(model.getEntityList().get(0), is(firstEntity));
    }

    @Test
    void shouldBeReturnTheFirstBaseEntity(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit firstBase = new Unit("Base");
        Unit secondBase = new Unit("Base");

        model.addEntity(firstEntity).addBase(firstBase).addBase(secondBase);

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getBase().size(), is(1));
        assertThat(entityModel.getBase().contains(firstBase), is(true));
    }

    @Test
    void shouldBeReturnTheFirstBaseAndSecondBaseEntity(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit firstBase = new Unit("Base");
        Unit secondBase = new Unit("Base2");

        model.addEntity(firstEntity).addBase(firstBase).addBase(secondBase);

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getBase().size(), is(2));
        assertThat(entityModel.getBase().contains(firstBase), is(true));
        assertThat(entityModel.getBase().contains(secondBase), is(true));
    }

    @Test
    void shouldBeReturnTheFirstPartEntity(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit firstPart = new Unit("Part");
        Unit secondPart = new Unit("Part");

        model.addEntity(firstEntity).addPart(firstPart).addPart(secondPart);

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains(firstPart), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondPartEntity(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit firstPart = new Unit("Part");
        Unit secondPart = new Unit("Part2");

        model.addEntity(firstEntity).addPart(firstPart).addPart(secondPart);

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains(firstPart), is (true));
        assertThat(entityModel.getPartList().contains(secondPart), is (true));
    }

    @Test
    void shouldBeReturnTheFirstElementEntity(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit firstElement = new Unit("Element");
        Unit secondElement = new Unit("Element");

        model.addEntity(firstEntity).addPart(firstElement).addPart(secondElement);

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains(firstElement), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondElementEntity(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit firstElement = new Unit("Element");
        Unit secondElement = new Unit("Element2");

        model.addEntity(firstEntity).addPart(firstElement).addPart(secondElement);

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains(firstElement), is (true));
        assertThat(entityModel.getPartList().contains(secondElement), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAssociateEntity(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit firstAssociate = new Unit("Associate");
        Unit secondAssociate = new Unit("Associate");

        model.addEntity(firstEntity).addPart(firstAssociate).addPart(secondAssociate);

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains(firstAssociate), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondAssociateEntity(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit firstAssociate = new Unit("Element");
        Unit secondAssociate = new Unit("Element2");

        model.addEntity(firstEntity).addPart(firstAssociate).addPart(secondAssociate);

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains(firstAssociate), is (true));
        assertThat(entityModel.getPartList().contains(secondAssociate), is (true));
    }

    @Test
    void shouldBeReturnTheFirstUsedEntity(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit firstUsed = new Unit("Used");
        Unit secondUsed = new Unit("Used");

        model.addEntity(firstEntity).addPart(firstUsed).addPart(secondUsed);

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains(firstUsed), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondUsedEntity(){

        Package model = new Package("model");
        Unit firstEntity = new Unit("Entity");
        Unit firstUsed = new Unit("Used");
        Unit secondUsed = new Unit("Used2");

        model.addEntity(firstEntity).addPart(firstUsed).addPart(secondUsed);

        Unit entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains(firstUsed), is (true));
        assertThat(entityModel.getPartList().contains(secondUsed), is (true));
    }

    @Test
    void shouldBeReturnBasePartElementAssociateUsedWithStringCreateMethod(){

        Package model = new Package("model");
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