package com.urjc.mca.tfm.generateuml.model;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram {

    private static final String LINE_BREAK = "\n";
    private static final String PART_RELATIONSHIP = " *--> ";
    private static final String BASE_RELATIONSHIP = " <|-- ";
    private static final String ELEMENT_RELATIONSHIP = " o--> ";
    private static final String ASSOCIATION_RELATIONSHIP = " --> ";
    private static final String USE_RELATIONSHIP = " ..> ";

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

    private String printClass(Unit unit) {
        return unit.toStringClassFormat();
    }

    public String printPart(Unit entity) {
        StringBuilder chain = new StringBuilder();
        if (!entity.getPartList().isEmpty()) {
            entity.getPartList().forEach(p -> chain.append(entity.printName() + PART_RELATIONSHIP + p.printName() + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printBase(Unit entity) {
        StringBuilder chain = new StringBuilder();
        if (!entity.getBase().isEmpty()) {
            entity.getBase().forEach(b -> chain.append(b.printName() + BASE_RELATIONSHIP + entity.printName() + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printElement(Unit entity) {
        StringBuilder chain = new StringBuilder();
        if (!entity.getElements().isEmpty()) {
            entity.getElements().forEach(e -> chain.append(entity.printName() + ELEMENT_RELATIONSHIP + e.printName() + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printAssociates(Unit entity) {
        StringBuilder chain = new StringBuilder();
        if (!entity.getAssociates().isEmpty()) {
            entity.getAssociates().forEach(a -> chain.append(entity.printName() + ASSOCIATION_RELATIONSHIP + a.printName() + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printUsed(Unit entity) {
        StringBuilder chain = new StringBuilder();
        if (!entity.getUsed().isEmpty()) {
            entity.getUsed().forEach(u -> chain.append(entity.printName() + USE_RELATIONSHIP + u.printName() + LINE_BREAK));
        }
        return chain.toString();
    }
}
