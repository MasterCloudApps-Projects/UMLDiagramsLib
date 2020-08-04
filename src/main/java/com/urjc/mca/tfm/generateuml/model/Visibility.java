package com.urjc.mca.tfm.generateuml.model;

public enum Visibility {

    PUBLIC("public", "+"), PRIVATE("private", "-"), PROTECTED("protected", "#"),
    PACKAGE("package","~"), EMPTY_VISIBILITY("empty", "");

    private String name;
    private String character;

    Visibility(String name, String character){
        this.name = name;
        this.character = character;
    }

    public String getCharacter(){
        return this.character;
    }
}
