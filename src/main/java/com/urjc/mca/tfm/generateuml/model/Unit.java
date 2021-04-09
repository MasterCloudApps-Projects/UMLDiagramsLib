package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Unit implements Serializable {

    private static final String LINE_BREAK = "\n";
    private static final String DOT = ".";
    private static final String CLASS_AND_SPACE = "class ";
    private static final String ABSTRACT_AND_SPACE = "abstract ";
    private static final String WHITE_SPACE_IN_NAME_MATCHER = ".*\\s.*";

    public final String name;
    //Element composition
    private Set<Unit> partList = new HashSet<>();
    private Set<Unit> base = new HashSet<>();
    //Element aggregate into unit
    private Set<Unit> elements = new HashSet<>();
    //Posee un elemento y se le pasa en el constructor
    private Set<Unit> associates = new HashSet<>();
    //dependecy/use
    private Set<Unit> used = new HashSet<>();
    private String myPackage;
    private Set<Attribute> attributes = new HashSet<>();
    private Set<Function> functions = new HashSet<>();
    private boolean abstractUnit = false;
    private String annotation;

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

    public void addAnnotation(String annotation){
        this.annotation = annotation;
    }

    public String getAnnotation(){
        return annotation;
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

    public String toStringClassFormat(Map<String, String> annotationColor){
        StringBuilder stringBuilder = new StringBuilder();
        if(abstractUnit)
            stringBuilder.append(ABSTRACT_AND_SPACE);
        stringBuilder.append(CLASS_AND_SPACE);
        stringBuilder.append(printName());
        if(!StringUtils.isEmpty(annotation)){
            stringBuilder.append(" ").append(annotationColor.get(annotation));
        }
        if(containsAttributeOrFunction()){
            stringBuilder.append("{" + LINE_BREAK);
            this.attributes.forEach(attribute -> stringBuilder.append(attribute.toString()));
            if(!attributes.isEmpty())
                stringBuilder.append(LINE_BREAK);
            this.functions.forEach(function -> stringBuilder.append(function.toString()));
            if(!functions.isEmpty())
                stringBuilder.append(LINE_BREAK);
            stringBuilder.append("}");
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
        return (!StringUtils.isEmpty(myPackage) && regexMatcher(myPackage)
                || regexMatcher(name));
    }

    private boolean regexMatcher(final String matcher){
        return java.util.regex.Pattern.compile(WHITE_SPACE_IN_NAME_MATCHER).matcher(matcher).matches();
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
