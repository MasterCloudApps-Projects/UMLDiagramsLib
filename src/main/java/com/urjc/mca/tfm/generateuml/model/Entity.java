package com.urjc.mca.tfm.generateuml.model;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    public String name;
    private List<Entity> partList = new ArrayList<>();
    private List<Entity> base = new ArrayList<>();
//    private List<Entity> base;


    public Entity() {
    }

    public Entity(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Class " + name)
                .append(base.get(0).name + " <|-- " + name)
                .append(name + " *--> " + partList.get(0).name);
        System.out.println(stringBuffer);
        base.forEach(e -> System.out.println(e.name));
        partList.forEach(e -> System.out.println(e.name));
        return name;
    }

    public void addPart(Entity entity){
        partList.add(entity);
    }

    public void addBase(Entity entity){
        base.add(entity);
    }

    public List<Entity> getBase(){
        return this.base;
    }

    public List<Entity> getPartList(){
        return this.partList;
    }
}
