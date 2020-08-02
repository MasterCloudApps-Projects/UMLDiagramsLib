package com.urjc.mca.tfm.generateuml.model;

import java.util.*;

public class Entity {

    public final String name;
    private Set<Entity> partList = new HashSet<>();
    private Set<Entity> base = new HashSet<>();
    private Set<Entity> elements = new HashSet<>();
    private Set<Entity> associates = new HashSet<>();
    private Set<Entity> used = new HashSet<>();
    private Package model;

    public Entity(String name) {
        this.name = name;
    }

    public void addPart(Entity entity) {
        partList.add(entity);
    }

    public void addElement(Entity entity){
        elements.add(entity);
    }

    public void addAssociate(Entity entity){
        associates.add(entity);
    }

    public void addBase(Entity entity) {
        base.add(entity);
    }

    public void addUsed(Entity entity){
        used.add(entity);
    }

    public Package getModel() {
        return model;
    }

    public void setModel(Package model) {
        this.model = model;
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

    public Set<Entity> getAssociates(){
        return this.associates;
    }

    public Set<Entity> getUsed(){
        return this.used;
    }

    public List<Entity> getEferents(){

        //Cogemos las "bases" o no es necesario y solo tomamos los descendientes?
        List<Entity> eferents = new ArrayList<>();
        eferents.add(this);
//        partList.forEach(p -> eferents.add(p));
//        elements.forEach(e -> eferents.add(e));
//        associates.forEach(a -> eferents.add(a));
//        used.forEach(u -> eferents.add(u));
        return eferents;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
