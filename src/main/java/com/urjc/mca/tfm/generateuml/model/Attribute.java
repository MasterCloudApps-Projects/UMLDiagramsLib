package com.urjc.mca.tfm.generateuml.model;

import java.util.Objects;

public class Attribute {

    public final String name;
    public final Visibility visibility;

    public Attribute(String name){
        this(name, Visibility.EMPTY_VISIBILITY);
    }

    public Attribute(String name, Visibility visibility){
        this.name = name;
        this.visibility = visibility;
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
