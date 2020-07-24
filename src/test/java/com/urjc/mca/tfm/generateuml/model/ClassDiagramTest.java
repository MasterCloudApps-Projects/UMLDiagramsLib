package com.urjc.mca.tfm.generateuml.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


import static org.junit.jupiter.api.Assertions.*;

class ClassDiagramTest {

    @Test
    public void printClassName(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity firstEntity = new Entity("Entity1");
        Entity secondEntity = new Entity("Entity2");

        String result = "class Entity1\nclass Entity2\n";
        model.addEntity(firstEntity).addEntity(secondEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));

    }

    @Test
    public void printBase(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity firstEntity = new Entity("Entity1");
        Entity baseEntity = new Entity("Base");

        String result = "class Entity1\nBase <|-- Entity1\nclass Base\n";
        model.addEntity(firstEntity).addBase(baseEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

    @Test
    public void printPart(){
        ClassDiagram classDiagram = new ClassDiagram();
        Model model = new Model();
        Entity firstEntity = new Entity("Entity1");
        Entity partEntity = new Entity("Part");

        String result = "class Entity1\nEntity1 *--> Part\nclass Part\n";
        model.addEntity(firstEntity).addPart(partEntity);
        classDiagram.addClasses(model.getEntityList()).print();

        assertThat(classDiagram.print(), is(result));
    }

}