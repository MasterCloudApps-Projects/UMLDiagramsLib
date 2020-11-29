package com.urjc.mca.tfm.generateuml;

import com.urjc.mca.tfm.generateuml.model.Domain;
import com.urjc.mca.tfm.generateuml.model.Unit;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ClassDiagramGenerator {

    private static final String LINE_BREAK = "\n";
    private static final String PART_RELATIONSHIP = " *--> ";
    private static final String BASE_RELATIONSHIP = " <|-- ";
    private static final String ELEMENT_RELATIONSHIP = " o--> ";
    private static final String ASSOCIATION_RELATIONSHIP = " --> ";
    private static final String USE_RELATIONSHIP = " ..> ";

    List<Unit> units = new ArrayList<>();

    public ClassDiagramGenerator addUnit(Unit unit) {
        units.add(unit);
        return this;
    }

    public ClassDiagramGenerator addUnits(List<Unit> entities) {
        entities.forEach(this::addUnit);
        return this;
    }

    public ClassDiagramGenerator addDomain(Domain domain) {
        this.addUnits(domain.getUnitList());
        return this;
    }

    public String print() {
        return print(null);
    }

    public String print(String packageDescription) {
        StringBuilder className = new StringBuilder();
        StringBuilder relations = new StringBuilder();

        units.forEach(unit -> {
            if (packageDescription == null ||
                    StringUtils.equals(unit.getMyPackage(), packageDescription)) {
                className.append(printClass(unit));
                relations.append(printBase(unit));
                relations.append(printPart(unit));
                relations.append(printElement(unit));
                relations.append(printAssociates(unit));
                relations.append(printUsed(unit));
            }
        });
        String relationsString = relations.toString();
        if (packageDescription != null) {
            units.stream()
                    .filter(unit -> !StringUtils.equals(unit.getMyPackage(), (packageDescription)))
                    .filter(unit -> relationsString.contains(unit.printName()))
                    .forEach(unit -> className.append(printClass(unit)));
        }
        return className.toString() + relationsString;
    }

    public String printPackage() {
        StringBuilder sb = new StringBuilder();
        Set<String> packageDescription = new HashSet<>();
        units.forEach(e -> {
            sb.append(printPackage(e, packageDescription));
            sb.append(printRelationships(e, packageDescription));
        });
        return sb.toString();
    }

    private String printRelationships(Unit unit, Set<String> packageDescription) {
        StringBuilder sb = new StringBuilder();
        Predicate<Unit> filterNoEqualsPackage = p -> !unit.getMyPackage().equals(p.getMyPackage());
        Predicate<Unit> filterNoContainsPackage = p -> !packageDescription.contains(unit.getMyPackage() + USE_RELATIONSHIP + p.getMyPackage());
        Consumer<Unit> addRelationship = p -> {
            sb.append(unit.getMyPackage() + USE_RELATIONSHIP + p.getMyPackage() + LINE_BREAK);
            packageDescription.add(unit.getMyPackage() + USE_RELATIONSHIP + p.getMyPackage());
        };

        unit.getPartList().stream().filter(filterNoEqualsPackage).filter(filterNoContainsPackage).forEach(addRelationship);
        unit.getUsed().stream().filter(filterNoEqualsPackage).filter(filterNoContainsPackage).forEach(addRelationship);
        unit.getAssociates().stream().filter(filterNoEqualsPackage).filter(filterNoContainsPackage).forEach(addRelationship);
        unit.getElements().stream().filter(filterNoEqualsPackage).filter(filterNoContainsPackage).forEach(addRelationship);
        unit.getBase().stream().filter(filterNoEqualsPackage).filter(filterNoContainsPackage).forEach(addRelationship);

        return sb.toString();
    }

    private String printPackage(Unit e, Set<String> packageDescription) {
        StringBuilder sb = new StringBuilder();
        if (!packageDescription.contains(e.getMyPackage())) {
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
        unit.getPartList().forEach(p -> chain.append(unit.printName() + PART_RELATIONSHIP + p.printName() + LINE_BREAK));
        return chain.toString();
    }

    public String printBase(Unit unit) {
        StringBuilder chain = new StringBuilder();
        unit.getBase().forEach(b -> chain.append(b.printName() + BASE_RELATIONSHIP + unit.printName() + LINE_BREAK));
        return chain.toString();
    }

    public String printElement(Unit unit) {
        StringBuilder chain = new StringBuilder();
        unit.getElements().forEach(e -> chain.append(unit.printName() + ELEMENT_RELATIONSHIP + e.printName() + LINE_BREAK));
        return chain.toString();
    }

    public String printAssociates(Unit unit) {
        StringBuilder chain = new StringBuilder();
        unit.getAssociates().forEach(a -> chain.append(unit.printName() + ASSOCIATION_RELATIONSHIP + a.printName() + LINE_BREAK));
        return chain.toString();
    }

    public String printUsed(Unit unit) {
        StringBuilder chain = new StringBuilder();
        unit.getUsed().forEach(u -> chain.append(unit.printName() + USE_RELATIONSHIP + u.printName() + LINE_BREAK));
        return chain.toString();
    }
}
