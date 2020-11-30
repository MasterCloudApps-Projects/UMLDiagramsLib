package com.urjc.mca.tfm.generateuml;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.urjc.mca.tfm.generateuml.model.Domain;

import java.util.HashSet;
import java.util.Set;

public class JavaAnalyzer {
    Set<String> constructor = new HashSet<>();
    String path;

    public JavaAnalyzer(String path) {
        this.path = path;
    }

    public Domain run() {

        JavaClasses importedClasses = new ClassFileImporter().importPackages(path);

        Domain domain = new Domain("domain");
        for (JavaClass imp : importedClasses) {
            domain.addUnit(imp.getName());
            findComposition(domain, imp);
            findAggregation(domain, imp);
            findAssociate(domain, imp);
            findBase(domain, imp);
            findDependency(domain, imp);
        }

        return domain;

    }

    private void findComposition(Domain domain, JavaClass javaClass) {
        javaClass.getConstructors().forEach(c ->
                c.getConstructorCallsFromSelf().stream()
                        .filter(constructorCall -> !constructorCall.getTargetOwner().getName().equals("java.lang.Object"))
                        .filter(constructorCall -> !constructorCall.getTargetOwner().getName().equals(javaClass.getSuperClass().get().getName()))
                        .forEach(
                                javaConstructorCall -> {
                                    constructor.add(javaConstructorCall.getTargetOwner().getName());
                                    domain.addPart(javaConstructorCall.getTargetOwner().getName());
                                }
                        ));
    }

    private void findAssociate(Domain domain, JavaClass javaClass) {
        javaClass.getFields().forEach(field -> {
            if (!constructor.contains(field.getRawType().getName()))
                domain.addAssociate(field.getRawType().getName());
        });
    }

    private void findAggregation(Domain domain, JavaClass javaClass) {
        Set<String> fields = new HashSet<>();
        javaClass.getFields().forEach(f -> fields.add(f.getName()));

        javaClass.getMethods().forEach(method -> method.getMethodCallsFromSelf().stream()
                .filter(methodCall -> methodCall.getTarget().getName().equals("add"))
                .forEach(methodCall -> methodCall.getOrigin().getFieldAccesses().stream()
                        .filter(fieldAccess -> fieldAccess.getAccessType().name().equals("GET"))
                        .filter(fieldAccess -> fields.contains(fieldAccess.getTarget().getName()))
                        .forEach(fieldAccess -> {
                                    String aux = fieldAccess.getOrigin().getFullName();
                                    domain.addElement(aux.substring(aux.indexOf("(") + 1, aux.indexOf(")")));
                                }
                        )));
    }

    private void findDependency(Domain domain, JavaClass javaClass) {
        javaClass.getMethods().forEach(method -> method.getConstructorCallsFromSelf()
                .forEach(constructorCall -> domain.addUsed(constructorCall.getTarget().getOwner().getName())));

    }

    private void findBase(Domain domain, JavaClass javaClass) {
        if (javaClass.getSuperClass().isPresent() && !javaClass.getSuperClass().get().getName().equals("java.lang.Object"))
            domain.addBase(javaClass.getSuperClass().get().getName());
        javaClass.getInterfaces().forEach(javaInterfaces -> domain.addBase(javaInterfaces.getName()));
    }

}


