package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Domain {

    public final String name;
    private List<Unit> unitList = new ArrayList<>();
    private Unit activeEntity;
    private String activePackage;

    public Domain(String name){
        this.name = name;
    }

    private Domain addUnit(Unit unit){
        this.activeEntity = this.getUnit(unit);
        return this;
    }

    public Domain addUnit(String entity){
        return addUnit(new Unit(entity));
    }

    public Domain addPackage(String myPackage){
        this.activePackage = myPackage;
        return this;
    }

    public Domain nonPackage(){
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

    private Domain addBase(Unit unit){
        this.activeEntity.addBase(getUnit(unit));
        return this;
    }

    public Domain addBase(String unit){
        return addBase(new Unit(unit));
    }

    private Domain addPart(Unit unit){
        this.activeEntity.addPart(getUnit(unit));
        return this;
    }

    public Domain addPart(String unit){
        return addPart(new Unit(unit));
    }

    private Domain addElement(Unit unit){
        this.activeEntity.addElement(getUnit(unit));
        return this;
    }

    public Domain addElement(String unit){
        return addElement(new Unit(unit));
    }

    private Domain addAssociate(Unit unit){
        this.activeEntity.addAssociate(getUnit(unit));
        return this;
    }

    public Domain addAssociate(String unit){
        return addAssociate(new Unit(unit));
    }

    private Domain addUsed(Unit unit){
        this.activeEntity.addUsed(getUnit(unit));
        return this;
    }

    public Domain addUsed(String unit){
        return addUsed(new Unit(unit));
    }


    public List<Unit> getUnitList(){
        return this.unitList;
    }

    public Domain addAttribute(String name){
        return addAttribute(name, Visibility.EMPTY_VISIBILITY);
    }

    public Domain addAttribute(String name, Visibility visibility){
        return addAttribute(name, visibility, "");
    }

    public Domain addAttribute(String name, Visibility visibility, String type){
        return addAttribute(name, visibility, type, false);
    }

    public Domain addAttribute(String name, Visibility visibility, String type, boolean staticAttribute){
        this.activeEntity.addAttribute(new Attribute(name, visibility, type, staticAttribute));
        return this;
    }

    public List<Unit> getAfferent(String unit){
        List<Unit> afferent = new ArrayList<>();

        unitList.forEach( u -> {
            if (u.getBase().contains(new Unit(unit)))
                afferent.add(u);
            if (u.getPartList().contains(new Unit(unit)))
                afferent.add(u);
            if (u.getAssociates().contains(new Unit(unit)))
                afferent.add(u);
            if (u.getElements().contains(new Unit(unit)))
                afferent.add(u);
            if (u.getUsed().contains(new Unit(unit)))
                afferent.add(u);
        });

        return afferent;
    }
}
