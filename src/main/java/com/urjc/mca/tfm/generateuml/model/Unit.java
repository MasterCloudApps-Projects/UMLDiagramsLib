package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.*;

public class Unit {

    private static final String LINE_BREAK = "\n";
    private static final String DOT = ".";
    private static final String CLASS_AND_SPACE = "class ";

    public final String name;
    private Set<Unit> partList = new HashSet<>();
    private Set<Unit> base = new HashSet<>();
    private Set<Unit> elements = new HashSet<>();
    private Set<Unit> associates = new HashSet<>();
    private Set<Unit> used = new HashSet<>();
    private String myPackage;
    private Set<Attribute> attributes = new HashSet<>();
    private Set<Function> functions = new HashSet<>();

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

    public List<Unit> getEfferent(){
        List<Unit> efferents = new ArrayList<>();
        efferents.add(this);
        return efferents;
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

    public void addAttribute(Attribute name) {
        this.attributes.add(name);
    }

    public Set<Attribute> getAttributes(){
        return this.attributes;
    }

    public void addFunction(Function function){
        this.functions.add(function);
    }

    public Set<Function> getFunctions(){
        return this.functions;
    }

    public String toStringClassFormat(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CLASS_AND_SPACE);
        stringBuilder.append(printName());
        if(containsAttributeOrFunction()){
            stringBuilder.append("{" + LINE_BREAK);
            this.attributes.forEach(attribute -> stringBuilder.append(attribute.toString()));
            this.functions.forEach(function -> stringBuilder.append(function.toString()));
            stringBuilder.append("\n}");
        }
        stringBuilder.append(LINE_BREAK);
        return stringBuilder.toString();
    }

    public String printName() {
        return !StringUtils.isEmpty(this.myPackage) ? this.myPackage + DOT + this.name : this.name;
    }

    private boolean containsAttributeOrFunction(){
        return !this.getFunctions().isEmpty() || !this.getAttributes().isEmpty();
    }
}
