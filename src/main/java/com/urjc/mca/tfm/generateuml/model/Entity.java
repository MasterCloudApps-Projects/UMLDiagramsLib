package com.urjc.mca.tfm.generateuml.model;

import java.util.HashSet;
import java.util.Set;

public class Entity {

    public final String name;
    private Set<Entity> partList = new HashSet<>();
    private Set<Entity> base = new HashSet<>();
    private Set<Entity> elements = new HashSet<>();

    public Entity(String name) {
        this.name = name;
    }

//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("Class " + name)
//                .append(printBase())
//                .append(printPart());
//        System.out.println(stringBuilder);
//        base.forEach(e -> System.out.println(e.name));
//        partList.forEach(e -> System.out.println(e.name));
//        return name;
//    }

    public void addPart(Entity entity) {
        partList.add(entity);
    }

    public void addElement(Entity entity){
        elements.add(entity);
    }

    public void addBase(Entity entity) {
        base.add(entity);
    }

    public Set<Entity> getBase() {
        return this.base;
    }

    public Set<Entity> getPartList() {
        return this.partList;
    }

    public Set<Entity> getElements(){
        return this.elements;
    }

    private String printBase() {
        StringBuilder stringBuilder = new StringBuilder();
        base.forEach(b -> stringBuilder.append(b.name + " <|-- " + name));
        return stringBuilder.toString();
    }

    private String printPart() {
        StringBuilder stringBuilder = new StringBuilder();
        partList.forEach(p -> stringBuilder.append(name + " *--> " + p.name));
        return stringBuilder.toString();
    }

}
