package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class Attribute {

    public final String name;
    public final Visibility visibility;
    public final String type;
    public final boolean staticAttribute;

    public Attribute(String name){
        this(name, Visibility.EMPTY_VISIBILITY);
    }

    public Attribute(String name, Visibility visibility){
        this(name, visibility, "");
    }

    public Attribute(String name, Visibility visibility, String type){
        this(name, visibility, type, false);
    }

    public Attribute(String name, Visibility visibility, String type, boolean staticAttribute){
        this.name = name;
        this.visibility = visibility;
        this.type = type;
        this.staticAttribute = staticAttribute;
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

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        if(this.visibility != Visibility.EMPTY_VISIBILITY){
            stringBuilder.append(this.visibility.getCharacter());
            stringBuilder.append( " ");
        }
        if(this.staticAttribute)
            stringBuilder.append("{static} ");
        stringBuilder.append(this.name);
        if(!StringUtils.isEmpty(this.type)){
            stringBuilder.append(": ");
            stringBuilder.append(this.type);
        }
        return stringBuilder.toString();
    }
}
