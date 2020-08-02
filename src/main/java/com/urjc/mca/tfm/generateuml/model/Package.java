package com.urjc.mca.tfm.generateuml.model;

import java.util.ArrayList;
import java.util.List;

public class Package {

    public final String name;
    private List<Entity> entityList = new ArrayList<>();
    private Entity activeEntity;

    public Package(String name){
        this.name = name;
    }

    public Package addEntity(Entity entity){
        this.activeEntity = this.getEntity(entity);
        return this;
    }

    public Package addEntity(String entity){
        return addEntity(new Entity(entity));
    }

    private Entity getEntity(Entity entity) {
        Entity aux = getEntity(entity.name);
        if(aux == null){
            if(entity.getModel() == null)
                entity.setModel(this);
            entityList.add(entity);
            aux = entity;
        }
        return aux;
    }

    public Entity getEntity(String name){
        return entityList.stream().filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    public Package addBase(Entity entity){
        this.activeEntity.addBase(getEntity(entity));
        return this;
    }

    public Package addBase(String entity){
        return addBase(new Entity(entity));
    }

    public Package addPart(Entity entity){
        this.activeEntity.addPart(getEntity(entity));
        return this;
    }

    public Package addPart(String entity){
        return addPart(new Entity(entity));
    }

    public Package addElement(Entity entity){
        this.activeEntity.addElement(getEntity(entity));
        return this;
    }

    public Package addElement(String entity){
        return addElement(new Entity(entity));
    }

    public Package addAssociate(Entity entity){
        this.activeEntity.addAssociate(getEntity(entity));
        return this;
    }

    public Package addAssociate(String entity){
        return addAssociate(new Entity(entity));
    }

    public Package addUsed(Entity entity){
        this.activeEntity.addUsed(getEntity(entity));
        return this;
    }

    public Package addUsed(String entity){
        return addUsed(new Entity(entity));
    }


    public List<Entity> getEntityList(){
        return this.entityList;
    }
}
