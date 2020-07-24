package com.urjc.mca.tfm.generateuml.model;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private List<Entity> entityList = new ArrayList<>();
    private Entity activeEntity;


    public Model addEntity(Entity entity){
        this.activeEntity = this.getEntity(entity);
        return this;
    }

    private Entity getEntity(Entity entity) {
//        Entity aux = entityList.stream().filter(e -> e.name.equals(entity.name)).findFirst().orElse(null);
        Entity aux = getEntity(entity.name);
        if(aux == null){
            entityList.add(entity);
            //this.activeEntity = entity;
            aux = entity;
        }
        return aux;
    }

    public Model addBase(Entity entity){
        this.activeEntity.addBase(getEntity(entity));
        return this;
    }

    public Model addPart(Entity entity){
        this.activeEntity.addPart(getEntity(entity));
        return this;
    }
    public Model addElelement(Entity entity){
        this.activeEntity.addElement(getEntity(entity));
        return this;
    }

    public Entity getEntity(String name){
        return entityList.stream().filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    public List<Entity> getEntityList(){
        return this.entityList;
    }
}
