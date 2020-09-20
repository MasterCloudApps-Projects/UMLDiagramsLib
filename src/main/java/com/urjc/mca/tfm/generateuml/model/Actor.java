package com.urjc.mca.tfm.generateuml.model;

import java.util.HashSet;
import java.util.Set;

public class Actor {

    public final String name;
    private Set<UseCase> useCases = new HashSet<>();

    public Actor(String name){
        this.name = name;
    }

    public void addUseCase(UseCase useCase){
        if(!exitsUseCase(useCase))
            useCases.add(useCase);
    }

    private boolean exitsUseCase(UseCase useCase){
        return this.useCases.stream().anyMatch(us -> us.getName().equals(useCase.getName()));
    }
    public Set<UseCase> getUseCases(){
        return this.useCases;
    }

    public String printName(){
        return ":" + this.name + ":\n" ;
    }
}
