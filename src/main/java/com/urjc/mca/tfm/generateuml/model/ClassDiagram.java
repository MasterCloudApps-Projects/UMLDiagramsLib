package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram {

    List<Entity> classes = new ArrayList<>();

    public ClassDiagram addClass(Entity entity){
        classes.add(entity);
        return this;
    }

    public ClassDiagram addClasses(List<Entity> entities){
        entities.forEach(this::addClass);
        return this;
    }

    public String print(){
        StringBuilder className = new StringBuilder();
        StringBuilder relations = new StringBuilder();

        classes.forEach(e -> {
            className.append(printClassName(e));
            relations.append(printBase(e));
            relations.append(printPart(e));
            relations.append(printElement(e));
            relations.append(printAssociates(e));
            relations.append(printUsed(e));
        });
        return className.toString() + relations.toString();
    }

    public String printClassName(Entity entity){
        return "class " + printName(entity) + "\n";
    }

    private String printName(Entity entity){
        return !StringUtils.isEmpty(entity.getModel().name) ? entity.getModel().name + "." + entity.name: entity.name;
    }

    public String printPart(Entity entity){
        StringBuilder cadena = new StringBuilder();
        if(!entity.getPartList().isEmpty()){
            entity.getPartList().forEach( p -> cadena.append(printName(entity) + " *--> " + printName(p) + "\n"));
        }
        return cadena.toString();
    }

    public String printBase(Entity entity){
        StringBuilder cadena = new StringBuilder();
        if(!entity.getBase().isEmpty()){
            entity.getBase().forEach(b -> cadena.append( printName(b) + " <|-- " + printName(entity) + "\n"));
        }
        return cadena.toString();
    }

    public String printElement(Entity entity){
        StringBuilder cadena = new StringBuilder();
        if(!entity.getElements().isEmpty()){
            entity.getElements().forEach(e -> cadena.append( printName(entity) +" o--> " + printName(e) + "\n"));
        }
        return cadena.toString();
    }

    public String printAssociates(Entity entity){
        StringBuilder cadena = new StringBuilder();
        if(!entity.getAssociates().isEmpty()){
            entity.getAssociates().forEach(a -> cadena.append( printName(entity) +" --> " + printName(a) + "\n"));
        }
        return cadena.toString();
    }

    public String printUsed(Entity entity){
        StringBuilder cadena = new StringBuilder();
        if(!entity.getUsed().isEmpty()){
            entity.getUsed().forEach(u -> cadena.append( printName(entity) +" ..> " + printName(u) + "\n"));
        }
        return cadena.toString();
    }
}
