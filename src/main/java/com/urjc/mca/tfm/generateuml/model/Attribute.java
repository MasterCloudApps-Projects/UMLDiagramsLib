package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class Attribute {

    public final String name;
    public Visibility visibility;
    public String type;
    public boolean staticAttribute;

    public Attribute(String name){
        this.name = name;
        this.visibility = Visibility.EMPTY_VISIBILITY;
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
