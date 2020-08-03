package com.urjc.mca.tfm.generateuml.model;

import java.util.*;

public class Unit {

    public final String name;
    private Set<Unit> partList = new HashSet<>();
    private Set<Unit> base = new HashSet<>();
    private Set<Unit> elements = new HashSet<>();
    private Set<Unit> associates = new HashSet<>();
    private Set<Unit> used = new HashSet<>();
    private String myPackage;

    public Unit(String name) {
        this.name = name;
    }

    public void addPart(Unit entity) {
        partList.add(entity);
    }

    public void addElement(Unit entity){
        elements.add(entity);
    }

    public void addAssociate(Unit entity){
        associates.add(entity);
    }

    public void addBase(Unit entity) {
        base.add(entity);
    }

    public void addUsed(Unit entity){
        used.add(entity);
    }

    public String getMyPackage() {
        return myPackage;
    }

    public void setMyPackage(String myPackage) {
        this.myPackage = myPackage;
    }

    public Set<Unit> getBase() {
        return this.base;
    }

    public Set<Unit> getPartList() {
        return this.partList;
    }

    public Set<Unit> getElements(){
        return this.elements;
    }

    public Set<Unit> getAssociates(){
        return this.associates;
    }

    public Set<Unit> getUsed(){
        return this.used;
    }

    public List<Unit> getEferents(){

        //Cogemos las "bases" o no es necesario y solo tomamos los descendientes?
        List<Unit> eferents = new ArrayList<>();
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
        Unit entity = (Unit) o;
        return Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
