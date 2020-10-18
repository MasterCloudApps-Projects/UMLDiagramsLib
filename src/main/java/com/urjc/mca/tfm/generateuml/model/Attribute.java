package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class Attribute {

    public final String name;
    private Visibility visibility;
    private String type;
    private boolean staticAttribute;

    public Attribute(String name){
        this.name = name;
        this.setVisibility(Visibility.EMPTY_VISIBILITY);
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
        if(this.getVisibility() != Visibility.EMPTY_VISIBILITY){
            stringBuilder.append(this.getVisibility().getCharacter());
            stringBuilder.append( " ");
        }
        if(this.isStaticAttribute())
            stringBuilder.append("{static} ");
        stringBuilder.append(this.name);
        if(!StringUtils.isEmpty(this.getType())){
            stringBuilder.append(": ");
            stringBuilder.append(this.getType());
        }
        return stringBuilder.toString();
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStaticAttribute() {
        return staticAttribute;
    }

    public void setStaticAttribute(boolean staticAttribute) {
        this.staticAttribute = staticAttribute;
    }
}
