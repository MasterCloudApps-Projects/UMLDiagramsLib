package com.urjc.mca.tfm.generateuml.model;

import java.util.HashSet;
import java.util.Set;

public class Actor {

    public final String name;
    private Set<UseCase> useCases = new HashSet<>();
    private UseCase activeUseCase;

    public Actor(String name){
        this.name = name;
    }

    public void addUseCase(UseCase useCase){
        if(!exitsUseCase(useCase))
            useCases.add(useCase);
    }

    public void addUseCaseComposite(UseCase useCase){
        if(!exitsUseCase(useCase))
            useCases.add(useCase);
        activeUseCase = useCase;
    }

    public void addUseCaseToComposite(UseCase useCase){
        if(!exitsUseCase(useCase))
            activeUseCase.addUseCase(useCase);
    }

    public void addUseCaseCompositeToComposite(UseCase useCase){
        if(!exitsUseCase(useCase))
            activeUseCase.addUseCase(useCase);
        activeUseCase = useCase;
    }
    private boolean exitsUseCase(UseCase useCase){
        return this.useCases.stream().anyMatch(us -> us.getName().equals(useCase.getName()));
    }
    public Set<UseCase> getUseCases(){
        return this.useCases;
    }

    public String printName(){
        return printNameWithoutSpaces() + "\n";
    }

    public String printNameWithoutSpaces(){
        return ":" + this.name + ":" ;
    }
}
