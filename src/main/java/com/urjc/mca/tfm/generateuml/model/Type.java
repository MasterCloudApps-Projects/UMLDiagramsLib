package com.urjc.mca.tfm.generateuml.model;

public enum Type {

    LIST("List"), SET("Set"), INTEGER("Integer"), STRING ("String"), DOUBLE("Double"),
    EMPTY_TYPE("");
    private String description;

    Type(String description){
        this.description = description;
    }

    public String getDescription(){
        return getDescrption("");
    }

    public String getDescrption(String type){
        return description + "<" + type + ">";
    }
}
