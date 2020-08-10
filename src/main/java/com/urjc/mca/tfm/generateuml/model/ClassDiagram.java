package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassDiagram {

    private static final String LINE_BREAK = "\n";
    private static final String DOT = ".";
    private static final String PART_RELATIONSHIP = " *--> ";
    private static final String BASE_RELATIONSHIP = " <|-- ";
    private static final String ELEMENT_RELATIONSHIP = " o--> ";
    private static final String ASSOCIATION_RELATIONSHIP = " --> ";
    private static final String USE_RELATIONSHIP = " ..> ";
    private static final String CLASS_AND_SPACE = "class ";

    List<Unit> classes = new ArrayList<>();

    public ClassDiagram addClass(Unit entity) {
        classes.add(entity);
        return this;
    }

    public ClassDiagram addClasses(List<Unit> entities) {
        entities.forEach(this::addClass);
        return this;
    }

    public ClassDiagram addModel(Domain model) {
        this.addClasses(model.getUnitList());
        return this;
    }

    public String print() {
        StringBuilder className = new StringBuilder();
        StringBuilder relations = new StringBuilder();

        classes.forEach(e -> {
            className.append(printClass(e));
            relations.append(printBase(e));
            relations.append(printPart(e));
            relations.append(printElement(e));
            relations.append(printAssociates(e));
            relations.append(printUsed(e));
        });
        return className.toString() + relations.toString();
    }

    private String printClass(Unit unit){
        if(unit.getAttributes().size() > 0){
            return printClassNameWithAttribute(unit);
        }
        else{
            return printClassName(unit);
        }
    }

    private String printClassName(Unit unit) {
        return CLASS_AND_SPACE + printName(unit) + LINE_BREAK;
    }

    private String printClassNameWithAttribute(Unit unit){
        return CLASS_AND_SPACE + printName(unit)
                + "{" + LINE_BREAK + printAttributes(unit.getAttributes()) + "}";
    }

    private String printAttributes(Set<Attribute> attributes){
        StringBuilder attributesString = new StringBuilder();
        attributes.forEach( a -> attributesString.append(a.visibility.getCharacter() + " " + a.name + "\n"));
        return attributesString.toString();
    }

    private String printName(Unit entity) {
        return !StringUtils.isEmpty(entity.getMyPackage()) ? entity.getMyPackage() + DOT + entity.name : entity.name;
    }

    public String printPart(Unit entity) {
        StringBuilder chain = new StringBuilder();
        if (!entity.getPartList().isEmpty()) {
            entity.getPartList().forEach(p -> chain.append(printName(entity) + PART_RELATIONSHIP + printName(p) + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printBase(Unit entity) {
        StringBuilder chain = new StringBuilder();
        if (!entity.getBase().isEmpty()) {
            entity.getBase().forEach(b -> chain.append(printName(b) + BASE_RELATIONSHIP + printName(entity) + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printElement(Unit entity) {
        StringBuilder chain = new StringBuilder();
        if (!entity.getElements().isEmpty()) {
            entity.getElements().forEach(e -> chain.append(printName(entity) + ELEMENT_RELATIONSHIP + printName(e) + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printAssociates(Unit entity) {
        StringBuilder chain = new StringBuilder();
        if (!entity.getAssociates().isEmpty()) {
            entity.getAssociates().forEach(a -> chain.append(printName(entity) + ASSOCIATION_RELATIONSHIP + printName(a) + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printUsed(Unit entity) {
        StringBuilder chain = new StringBuilder();
        if (!entity.getUsed().isEmpty()) {
            entity.getUsed().forEach(u -> chain.append(printName(entity) + USE_RELATIONSHIP + printName(u) + LINE_BREAK));
        }
        return chain.toString();
    }
}
