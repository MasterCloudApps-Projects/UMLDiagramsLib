package com.urjc.mca.tfm.generateuml.model;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public final String name;
    private List<Entity> entityList = new ArrayList<>();
    private Entity activeEntity;

    public Model(String name){
        this.name = name;
    }

    public Model addEntity(Entity entity){
        this.activeEntity = this.getEntity(entity);
        return this;
    }

    public Model addEntity(String entity){
        return addEntity(new Entity(entity));
    }

    private Entity getEntity(Entity entity) {
//        Entity aux = entityList.stream().filter(e -> e.name.equals(entity.name)).findFirst().orElse(null);
        Entity aux = getEntity(entity.name);
        if(aux == null){
            entity.setModel(this);
            entityList.add(entity);
            //this.activeEntity = entity;
            aux = entity;
        }
        return aux;
    }

    public Entity getEntity(String name){
        return entityList.stream().filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    public Model addBase(Entity entity){
        this.activeEntity.addBase(getEntity(entity));
        return this;
    }

    public Model addBase(String entity){
        return addBase(new Entity(entity));
    }

    public Model addPart(Entity entity){
        this.activeEntity.addPart(getEntity(entity));
        return this;
    }

    public Model addPart(String entity){
        return addPart(new Entity(entity));
    }

    public Model addElement(Entity entity){
        this.activeEntity.addElement(getEntity(entity));
        return this;
    }

    public Model addElement(String entity){
        return addElement(new Entity(entity));
    }

    public Model addAssociate(Entity entity){
        this.activeEntity.addAssociate(getEntity(entity));
        return this;
    }

    public Model addAssociate(String entity){
        return addAssociate(new Entity(entity));
    }

    public Model addUsed(Entity entity){
        this.activeEntity.addUsed(getEntity(entity));
        return this;
    }

    public Model addUsed(String entity){
        return addUsed(new Entity(entity));
    }


    public List<Entity> getEntityList(){
        return this.entityList;
    }
}
