package com.urjc.mca.tfm.generateuml.model;

import java.util.Objects;

public class Attribute {

    public final String name;
    public final Visibility visibility;
//    public final Type type;
    public final String type;
    public final boolean staticAtribute;

    public Attribute(String name){
        this(name, Visibility.EMPTY_VISIBILITY);
    }

    public Attribute(String name, Visibility visibility){
//        this(name, visibility, Type.EMPTY_TYPE);
        this(name, visibility, "");
    }

    public Attribute(String name, Visibility visibility, String type){
        this(name, visibility, type, false);
    }

    public Attribute(String name, Visibility visibility, String type, boolean staticAtribute){
        this.name = name;
        this.visibility = visibility;
        this.type = type;
        this.staticAtribute = staticAtribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return Objects.equals(name, attribute.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
