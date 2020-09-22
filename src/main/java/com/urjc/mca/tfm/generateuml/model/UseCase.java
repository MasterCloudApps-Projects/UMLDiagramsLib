package com.urjc.mca.tfm.generateuml.model;

import java.util.Set;

public interface UseCase {

    String getName();
    String printName();

    void addUseCase(UseCase useCase);
    Set<UseCase> getUseCases();
}