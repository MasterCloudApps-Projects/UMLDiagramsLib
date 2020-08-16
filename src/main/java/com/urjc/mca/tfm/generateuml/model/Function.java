package com.urjc.mca.tfm.generateuml.model;

import java.util.Objects;

public class Function {

    public final boolean staticFunction;
    public final Visibility visibility;
    public final String name;
    public final String[] parameters;
    public final String returnTypeName;

    public Function(String name) {
        this(name, Visibility.EMPTY_VISIBILITY);
    }

    public Function(String name, Visibility visibility) {
        this(name, visibility, "");
    }

    public Function(String name, Visibility visibility, String returnType) {
        this(name, visibility, returnType, null);
    }

    public Function(String name, Visibility visibility, String returnType, String[] parameters) {
        this(name, visibility, returnType, parameters, false);
    }

    public Function(String name, Visibility visibility, String returnType, String[] parameters, boolean staticFunction) {
        this.name = name;
        this.visibility = visibility;
        this.returnTypeName = returnType;
        this.staticFunction = staticFunction;
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function = (Function) o;
        return Objects.equals(name, function.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}