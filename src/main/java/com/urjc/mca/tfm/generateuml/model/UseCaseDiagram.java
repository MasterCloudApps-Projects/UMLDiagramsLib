package com.urjc.mca.tfm.generateuml.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UseCaseDiagram {

    List<Actor> actorList = new ArrayList<>();

    public UseCaseDiagram addActor(Actor actor){
        this.actorList.add(actor);
        return this;
    }

    public String print(){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        actorList.forEach( actor -> {
            stringBuilder.append(actor.printName());
            stringBuilder2.append(printUseCase(actor.getUseCases()));
        });
        actorList.forEach( actor -> stringBuilder3.append(printRelationships(actor)));
        return stringBuilder.append(stringBuilder2).append(stringBuilder3).toString();
    }

    private String printUseCase(Set<UseCase> useCases) {
        StringBuilder sb = new StringBuilder();
        useCases.forEach( us -> {
            sb.append(us.printName()+"\n");
            sb.append(printUseCase(us.getUseCases()));
        });
        return sb.toString();
    }

    private String printRelationships(Actor actor){
        StringBuilder sb = new StringBuilder();
        actor.getUseCases().forEach( useCase -> {
            sb.append(actor.printNameWithoutSpaces() + " --> " + useCase.printName() + "\n");
            sb.append(printRelationships(useCase));
        });
        return sb.toString();
    }

    private String printRelationships(UseCase useCase){
        StringBuilder sb = new StringBuilder();
        useCase.getUseCases().forEach( us -> {
            sb.append(useCase.printName() + " --> " + us.printName() + "\n");
            sb.append(printRelationships(us));
        });
        return sb.toString();
    }
}
