package com.urjc.mca.tfm.generateuml.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Entity {

    public final String name;
    private List<Entity> partList = new ArrayList<>();
    private Set<Entity> base = new HashSet<>();

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Class " + name)
                .append(printBase())
                .append(name + " *--> " + partList.get(0).name);
        System.out.println(stringBuilder);
        base.forEach(e -> System.out.println(e.name));
        partList.forEach(e -> System.out.println(e.name));
        return name;
    }

    private String printBase() {
        StringBuilder stringBuilder = new StringBuilder();
        base.forEach(b -> stringBuilder.append(b.name + " <|-- " + name));
        return stringBuilder.toString();
    }

    public void addPart(Entity entity) {
        partList.add(entity);
    }

    public void addBase(Entity entity) {
        base.add(entity);
    }

    public Set<Entity> getBase() {
        return this.base;
    }

    public List<Entity> getPartList() {
        return this.partList;
    }
}
