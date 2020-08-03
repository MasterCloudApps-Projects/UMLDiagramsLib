package com.urjc.mca.tfm.generateuml.model;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public final String name;
    private List<Unit> entityList = new ArrayList<>();
    private Unit activeEntity;

    public Model(String name){
        this.name = name;
    }

    private Model addEntity(Unit entity){
        this.activeEntity = this.getEntity(entity);
        return this;
    }

    public Model addEntity(String entity){
        return addEntity(new Unit(entity));
    }

    private Unit getEntity(Unit entity) {
        Unit aux = getEntity(entity.name);
        if(aux == null){
            if(entity.getModel() == null)
                entity.setModel(this);
            entityList.add(entity);
            aux = entity;
        }
        return aux;
    }

    public Unit getEntity(String name){
        return entityList.stream().filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    private Model addBase(Unit entity){
        this.activeEntity.addBase(getEntity(entity));
        return this;
    }

    public Model addBase(String entity){
        return addBase(new Unit(entity));
    }

    private Model addPart(Unit entity){
        this.activeEntity.addPart(getEntity(entity));
        return this;
    }

    public Model addPart(String entity){
        return addPart(new Unit(entity));
    }

    private Model addElement(Unit entity){
        this.activeEntity.addElement(getEntity(entity));
        return this;
    }

    public Model addElement(String entity){
        return addElement(new Unit(entity));
    }

    private Model addAssociate(Unit entity){
        this.activeEntity.addAssociate(getEntity(entity));
        return this;
    }

    public Model addAssociate(String entity){
        return addAssociate(new Unit(entity));
    }

    private Model addUsed(Unit entity){
        this.activeEntity.addUsed(getEntity(entity));
        return this;
    }

    public Model addUsed(String entity){
        return addUsed(new Unit(entity));
    }


    public List<Unit> getEntityList(){
        return this.entityList;
    }
}
