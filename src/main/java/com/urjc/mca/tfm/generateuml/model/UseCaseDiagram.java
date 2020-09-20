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
        actorList.forEach( actor -> {
            stringBuilder.append(actor.printName());
            stringBuilder2.append(printUseCase(actor.getUseCases()));
        });
        return stringBuilder.append(stringBuilder2).toString();
    }

    private String printUseCase(Set<UseCase> useCases) {
        StringBuilder sb = new StringBuilder();
        useCases.forEach( us ->
                sb.append(us.printName()));
        return sb.toString();
    }
}
