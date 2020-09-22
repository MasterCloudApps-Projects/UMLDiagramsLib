package com.urjc.mca.tfm.generateuml.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UseCaseLeaf implements UseCase{

    private String name;

    public UseCaseLeaf(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
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

    public String printName(){
        return "(" + this.name + ")";
    }

    @Override
    public void addUseCase(UseCase useCase) {
        //Do nothing
    }

    @Override
    public Set<UseCase> getUseCases() {
        return new HashSet<>();
    }
}
