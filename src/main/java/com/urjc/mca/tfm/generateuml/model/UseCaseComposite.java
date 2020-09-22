package com.urjc.mca.tfm.generateuml.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UseCaseComposite implements UseCase{

    private String name;
    private Set<UseCase> useCases = new HashSet<>();

    public UseCaseComposite(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String printName(){
        return "(" + this.name + ")";
    }

    public String printUseCases(){
        StringBuilder sb = new StringBuilder();
        useCases.forEach(useCase -> sb.append(useCase.printName()));
        return sb.toString();
    }

    public Set<UseCase> getUseCases() {
        return this.useCases;
    }

    public void addUseCase(UseCase useCase) {
        this.useCases.add(useCase);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UseCase useCase = (UseCase) o;
        return Objects.equals(name, useCase.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
