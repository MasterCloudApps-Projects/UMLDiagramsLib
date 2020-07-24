package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void shouldBeReturnTheFirstEntityWhenAddClass(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity secondEntity = new Entity("Entity");

        model.addEntity(firstEntity).addEntity(secondEntity);

        assertThat(model.getEntityList().size(), is(1));
        assertThat(model.getEntityList().get(0), is(firstEntity));
    }

    @Test
    void shouldBeReturnTheFirstBaseEntity(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity firstBase = new Entity("Base");
        Entity secondBase = new Entity("Base");

        model.addEntity(firstEntity).addBase(firstBase).addBase(secondBase);

        Entity entityModel = model.getEntity("Entity");

        assertThat(entityModel.getBase().size(), is(1));
        assertThat(entityModel.getBase().contains(firstBase), is(true));
    }

    @Test
    void shouldBeReturnTheFirstBaseAndSecondBaseEntity(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity firstBase = new Entity("Base");
        Entity secondBase = new Entity("Base2");

        model.addEntity(firstEntity).addBase(firstBase).addBase(secondBase);

        Entity entityModel = model.getEntity("Entity");

        assertThat(entityModel.getBase().size(), is(2));
        assertThat(entityModel.getBase().contains(firstBase), is(true));
        assertThat(entityModel.getBase().contains(secondBase), is(true));
    }

    @Test
    void shouldBeReturnTheFirstPartEntity(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity firstPart = new Entity("Part");
        Entity secondPart = new Entity("Part");

        model.addEntity(firstEntity).addPart(firstPart).addPart(secondPart);

        Entity entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains(firstPart), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondPartEntity(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity firstPart = new Entity("Part");
        Entity secondPart = new Entity("Part2");

        model.addEntity(firstEntity).addPart(firstPart).addPart(secondPart);

        Entity entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains(firstPart), is (true));
        assertThat(entityModel.getPartList().contains(secondPart), is (true));
    }

    @Test
    void shouldBeReturnTheFirstElementEntity(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity firstElement = new Entity("Element");
        Entity secondElement = new Entity("Element");

        model.addEntity(firstEntity).addPart(firstElement).addPart(secondElement);

        Entity entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains(firstElement), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondElementEntity(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity firstElement = new Entity("Element");
        Entity secondElement = new Entity("Element2");

        model.addEntity(firstEntity).addPart(firstElement).addPart(secondElement);

        Entity entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains(firstElement), is (true));
        assertThat(entityModel.getPartList().contains(secondElement), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAssociateEntity(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity firstAssociate = new Entity("Associate");
        Entity secondAssociate = new Entity("Associate");

        model.addEntity(firstEntity).addPart(firstAssociate).addPart(secondAssociate);

        Entity entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains(firstAssociate), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondAssociateEntity(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity firstAssociate = new Entity("Element");
        Entity secondAssociate = new Entity("Element2");

        model.addEntity(firstEntity).addPart(firstAssociate).addPart(secondAssociate);

        Entity entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains(firstAssociate), is (true));
        assertThat(entityModel.getPartList().contains(secondAssociate), is (true));
    }

    @Test
    void shouldBeReturnTheFirstUsedEntity(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity firstUsed = new Entity("Used");
        Entity secondUsed = new Entity("Used");

        model.addEntity(firstEntity).addPart(firstUsed).addPart(secondUsed);

        Entity entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(1));
        assertThat(entityModel.getPartList().contains(firstUsed), is (true));
    }

    @Test
    void shouldBeReturnTheFirstAndSecondUsedEntity(){

        Model model = new Model();
        Entity firstEntity = new Entity("Entity");
        Entity firstUsed = new Entity("Used");
        Entity secondUsed = new Entity("Used2");

        model.addEntity(firstEntity).addPart(firstUsed).addPart(secondUsed);

        Entity entityModel = model.getEntity("Entity");

        assertThat(entityModel.getPartList().size(), is(2));
        assertThat(entityModel.getPartList().contains(firstUsed), is (true));
        assertThat(entityModel.getPartList().contains(secondUsed), is (true));
    }
}