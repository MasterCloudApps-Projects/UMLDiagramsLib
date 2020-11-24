package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Unit implements Serializable {

    private static final String LINE_BREAK = "\n";
    private static final String DOT = ".";
    private static final String CLASS_AND_SPACE = "class ";
    private static final String ABSTRACT_AND_SPACE = "abstract ";

    public final String name;
    private Set<Unit> partList = new HashSet<>();
    private Set<Unit> base = new HashSet<>();
    private Set<Unit> elements = new HashSet<>();
    private Set<Unit> associates = new HashSet<>();
    private Set<Unit> used = new HashSet<>();
    private String myPackage;
    private transient Set<Attribute> attributes = new HashSet<>();
    private transient Set<Function> functions = new HashSet<>();
    private boolean abstractUnit = false;

    public Unit(String name) {
        this.name = name;
    }

    public Unit(String unitName, String packageDescription) {
        this.name = unitName;
        this.myPackage = packageDescription;
    }

    public void addPart(Unit unit) {
        partList.add(unit);
    }

    public void addElement(Unit unit){
        elements.add(unit);
    }

    public void addAssociate(Unit unit){
        associates.add(unit);
    }

    public void addBase(Unit unit) {
        base.add(unit);
    }

    public void addUsed(Unit unit){
        used.add(unit);
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

    public void setAbstractUnit(){
        this.abstractUnit = true;
    }

    public boolean getAbstractUnit(){
        return this.abstractUnit;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(name, unit.name);
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
        if(abstractUnit)
            stringBuilder.append(ABSTRACT_AND_SPACE);
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
        return containsWhiteSpacesInName() ? printNameWithSpaces() : printNameWithoutSpaces();
    }

    private String printNameWithSpaces() {
        return !StringUtils.isEmpty(this.myPackage) ? "\"" + this.myPackage + DOT + this.name + "\"" : "\"" + this.name + "\"";
    }

    private String printNameWithoutSpaces() {
        return !StringUtils.isEmpty(this.myPackage) ? this.myPackage + DOT + this.name : this.name;
    }

    private boolean containsAttributeOrFunction(){
        return !this.getFunctions().isEmpty() || !this.getAttributes().isEmpty();
    }

    public boolean containsWhiteSpacesInName(){
        return (!StringUtils.isEmpty(myPackage) && myPackage.matches(".*\\s.*"))
                || name.matches(".*\\s.*");
    }

   public Unit makeClone() throws IOException, ClassNotFoundException {
       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
       ObjectOutputStream out = new ObjectOutputStream(outputStream);
       out.writeObject(this);

       ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
       ObjectInputStream in = new ObjectInputStream(inputStream);
       return (Unit) in.readObject();
   }
}
