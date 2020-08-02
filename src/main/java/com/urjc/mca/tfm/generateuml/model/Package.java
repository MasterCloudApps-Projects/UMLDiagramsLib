package com.urjc.mca.tfm.generateuml.model;

import java.util.ArrayList;
import java.util.List;

public class Package {

    public final String name;
    private List<Unit> entityList = new ArrayList<>();
    private Unit activeEntity;

    public Package(String name){
        this.name = name;
    }

    public Package addEntity(Unit entity){
        this.activeEntity = this.getEntity(entity);
        return this;
    }

    public Package addEntity(String entity){
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

    public Package addBase(Unit entity){
        this.activeEntity.addBase(getEntity(entity));
        return this;
    }

    public Package addBase(String entity){
        return addBase(new Unit(entity));
    }

    public Package addPart(Unit entity){
        this.activeEntity.addPart(getEntity(entity));
        return this;
    }

    public Package addPart(String entity){
        return addPart(new Unit(entity));
    }

    public Package addElement(Unit entity){
        this.activeEntity.addElement(getEntity(entity));
        return this;
    }

    public Package addElement(String entity){
        return addElement(new Unit(entity));
    }

    public Package addAssociate(Unit entity){
        this.activeEntity.addAssociate(getEntity(entity));
        return this;
    }

    public Package addAssociate(String entity){
        return addAssociate(new Unit(entity));
    }

    public Package addUsed(Unit entity){
        this.activeEntity.addUsed(getEntity(entity));
        return this;
    }

    public Package addUsed(String entity){
        return addUsed(new Unit(entity));
    }


    public List<Unit> getEntityList(){
        return this.entityList;
    }
}
