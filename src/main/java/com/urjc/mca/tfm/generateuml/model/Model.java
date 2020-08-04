package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public final String name;
    private List<Unit> unitList = new ArrayList<>();
    private Unit activeEntity;
    private String activePackage;

    public Model(String name){
        this.name = name;
    }

    private Model addUnit(Unit unit){
        this.activeEntity = this.getUnit(unit);
        return this;
    }

    public Model addUnit(String entity){
        return addUnit(new Unit(entity));
    }

    public Model addPackage(String myPackage){
        this.activePackage = myPackage;
        return this;
    }

    public Model nonPackage(){
        this.activePackage = "";
        return this;
    }

    private Unit getUnit(Unit unit) {
        Unit aux = getUnit(unit.name);
        if(aux == null){
            if(StringUtils.isEmpty(unit.getMyPackage()))
                unit.setMyPackage(this.activePackage);
            unitList.add(unit);
            aux = unit;
        }
        return aux;
    }

    public Unit getUnit(String name){
        return unitList.stream().filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    private Model addBase(Unit unit){
        this.activeEntity.addBase(getUnit(unit));
        return this;
    }

    public Model addBase(String unit){
        return addBase(new Unit(unit));
    }

    private Model addPart(Unit unit){
        this.activeEntity.addPart(getUnit(unit));
        return this;
    }

    public Model addPart(String unit){
        return addPart(new Unit(unit));
    }

    private Model addElement(Unit unit){
        this.activeEntity.addElement(getUnit(unit));
        return this;
    }

    public Model addElement(String unit){
        return addElement(new Unit(unit));
    }

    private Model addAssociate(Unit unit){
        this.activeEntity.addAssociate(getUnit(unit));
        return this;
    }

    public Model addAssociate(String unit){
        return addAssociate(new Unit(unit));
    }

    private Model addUsed(Unit unit){
        this.activeEntity.addUsed(getUnit(unit));
        return this;
    }

    public Model addUsed(String unit){
        return addUsed(new Unit(unit));
    }


    public List<Unit> getUnitList(){
        return this.unitList;
    }

    public Model addAttribute(String name){
        return addAttribute(name, null);
    }

    public Model addAttribute(String name, Visibility visibility){
        this.activeEntity.addAttribute(new Attribute(name, visibility));
        return this;
    }
}
