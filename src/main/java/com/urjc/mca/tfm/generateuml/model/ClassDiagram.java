package com.urjc.mca.tfm.generateuml.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ClassDiagram {

    private static final String LINE_BREAK = "\n";
    private static final String PART_RELATIONSHIP = " *--> ";
    private static final String BASE_RELATIONSHIP = " <|-- ";
    private static final String ELEMENT_RELATIONSHIP = " o--> ";
    private static final String ASSOCIATION_RELATIONSHIP = " --> ";
    private static final String USE_RELATIONSHIP = " ..> ";

    List<Unit> units = new ArrayList<>();

    public ClassDiagram addUnit(Unit unit) {
        units.add(unit);
        return this;
    }

    public ClassDiagram addUnits(List<Unit> entities) {
        entities.forEach(this::addUnit);
        return this;
    }

    public ClassDiagram addDomain(Domain domain) {
        this.addUnits(domain.getUnitList());
        return this;
    }

    public String print() {
        StringBuilder className = new StringBuilder();
        StringBuilder relations = new StringBuilder();

        units.forEach(e -> {
            className.append(printClass(e));
            relations.append(printBase(e));
            relations.append(printPart(e));
            relations.append(printElement(e));
            relations.append(printAssociates(e));
            relations.append(printUsed(e));
        });
        return className.toString() + relations.toString();
    }

    public String printPackage(){
        StringBuilder sb = new StringBuilder();
        Set<String> packageDescription = new HashSet<>();
        units.forEach( e -> {
            sb.append(printPackage1(e, packageDescription));
            sb.append(printRelationships(e, packageDescription));
        });
        return sb.toString();
    }

    private String printRelationships(Unit unit, Set<String> packageDescription) {
        StringBuilder sb = new StringBuilder();
        Predicate<Unit> ff = p -> !unit.getMyPackage().equals(p.getMyPackage());
        Predicate<Unit> f =  p -> !packageDescription.contains(unit.getMyPackage() + USE_RELATIONSHIP + p.getMyPackage());
        Consumer<Unit> c = p ->{
            sb.append(unit.getMyPackage() + USE_RELATIONSHIP + p.getMyPackage() + LINE_BREAK);
            packageDescription.add(unit.getMyPackage() + USE_RELATIONSHIP + p.getMyPackage());
        };

        if(!unit.getPartList().isEmpty())
            unit.getPartList().stream().filter(ff).filter(f).forEach(c);

        if(!unit.getUsed().isEmpty())
            unit.getUsed().stream().filter(ff).filter(f).forEach(c);

        if(!unit.getAssociates().isEmpty())
            unit.getAssociates().stream().filter(ff).filter(f).forEach(c);

        if(!unit.getElements().isEmpty())
            unit.getElements().stream().filter(ff).filter(f).forEach(c);

        if(!unit.getBase().isEmpty())
            unit.getBase().stream().filter(ff).filter(f).forEach(c);

        return sb.toString();
    }

    private String printPackage1(Unit e, Set<String> packageDescription) {
        StringBuilder sb = new StringBuilder();
        if(!packageDescription.contains(e.getMyPackage())){
            sb.append("package " + e.getMyPackage() + " {} " + LINE_BREAK);
            packageDescription.add(e.getMyPackage());
        }
        return sb.toString();
    }

    private String printClass(Unit unit) {
        return unit.toStringClassFormat();
    }

    public String printPart(Unit unit) {
        StringBuilder chain = new StringBuilder();
        if (!unit.getPartList().isEmpty()) {
            unit.getPartList().forEach(p -> chain.append(unit.printName() + PART_RELATIONSHIP + p.printName() + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printBase(Unit unit) {
        StringBuilder chain = new StringBuilder();
        if (!unit.getBase().isEmpty()) {
            unit.getBase().forEach(b -> chain.append(b.printName() + BASE_RELATIONSHIP + unit.printName() + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printElement(Unit unit) {
        StringBuilder chain = new StringBuilder();
        if (!unit.getElements().isEmpty()) {
            unit.getElements().forEach(e -> chain.append(unit.printName() + ELEMENT_RELATIONSHIP + e.printName() + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printAssociates(Unit unit) {
        StringBuilder chain = new StringBuilder();
        if (!unit.getAssociates().isEmpty()) {
            unit.getAssociates().forEach(a -> chain.append(unit.printName() + ASSOCIATION_RELATIONSHIP + a.printName() + LINE_BREAK));
        }
        return chain.toString();
    }

    public String printUsed(Unit unit) {
        StringBuilder chain = new StringBuilder();
        if (!unit.getUsed().isEmpty()) {
            unit.getUsed().forEach(u -> chain.append(unit.printName() + USE_RELATIONSHIP + u.printName() + LINE_BREAK));
        }
        return chain.toString();
    }
}
