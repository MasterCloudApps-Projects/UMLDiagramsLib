package com.urjc.mca.tfm.generateuml.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Domain {

    private static final Logger LOG = LoggerFactory.getLogger(Domain.class);

    public final String name;
    private List<Unit> unitList = new ArrayList<>();
    private Unit activeUnit;
    private String activePackage;
    private List<Actor> actorList = new ArrayList<>();
    private Actor activeActor;
    private Function activeFunction;

    public Domain(String name){
        this.name = name;
    }

    private Domain addUnit(Unit unit){
        this.activeUnit = this.getUnit(unit);
        return this;
    }

    public Domain addUnit(String unit){
        LOG.debug("add unit:" + unit);
        return addUnit(new Unit(unit));
    }

    public Domain addPackage(String myPackage){
        LOG.debug("add package:" + myPackage);
        this.activePackage = myPackage;
        return this;
    }

    public Domain nonPackage(){
        LOG.debug("remove package");
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
        this.activeUnit.addBase(getUnit(unit));
        return this;
    }

    public Domain addBase(String unit){
        LOG.debug("add base:" + unit);
        return addBase(new Unit(unit));
    }

    private Domain addPart(Unit unit){
        this.activeUnit.addPart(getUnit(unit));
        return this;
    }

    public Domain addPart(String unit){
        LOG.debug("add part:" + unit);
        return addPart(new Unit(unit));
    }

    private Domain addElement(Unit unit){
        this.activeUnit.addElement(getUnit(unit));
        return this;
    }

    public Domain addElement(String unit){
        LOG.debug("add element:" + unit);
        return addElement(new Unit(unit));
    }

    private Domain addAssociate(Unit unit){
        this.activeUnit.addAssociate(getUnit(unit));
        return this;
    }

    public Domain addAssociate(String unit){
        LOG.debug("add associate:" + unit);
        return addAssociate(new Unit(unit));
    }

    private Domain addUsed(Unit unit){
        this.activeUnit.addUsed(getUnit(unit));
        return this;
    }

    public Domain addUsed(String unit){
        LOG.debug("add used:" + unit);
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
        this.activeUnit.addAttribute(new Attribute(name, visibility, type, staticAttribute));
        return this;
    }

    public Domain addFunction(String name){
        activeFunction = new Function(name);
        this.activeUnit.addFunction(activeFunction);
        return this;
    }

    public Domain addVisibility(Visibility visibility){
        this.activeFunction.setVisibility(visibility);
        return this;
    }

    public Domain addReturnType(String returnType){
        this.activeFunction.setReturnTypeName(returnType);
        return this;
    }

    public Domain addParameters(String[] parameters){
        this.activeFunction.setParameters(parameters);
        return this;
    }

    public Domain setStaticFunction(boolean staticValue){
        this.activeFunction.setStaticFunction(staticValue);
        return this;
    }

    public Domain addFunction(String name, Visibility visibility){
        return addFunction(name, visibility, "");
    }

    public Domain addFunction(String name, Visibility visibility, String returnType){
        return addFunction(name, visibility, returnType, null);
    }

    public Domain addFunction(String name, Visibility visibility, String returnType, String[] parameters){
        return addFunction(name, visibility, returnType, parameters, false);
    }

    public Domain addFunction(String name, Visibility visibility, String returnType, String[] parameters, boolean staticFunction){
        this.activeUnit.addFunction(new Function(name, visibility, returnType, parameters, staticFunction));
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

    private Domain addActor(Actor actor){
        this.activeActor = this.getActor(actor);
        return this;
    }

    public Domain addActor(String actor){
        LOG.debug("add actor:" + actor);
        return addActor(new Actor(actor));
    }

    private Actor getActor(Actor actor) {
        Actor aux = getActor(actor.name);
        if(aux == null){
            actorList.add(actor);
            aux = actor;
        }
        return aux;
    }

    public Actor getActor(String name){
        return actorList.stream().filter(a -> a.name.equals(name)).findFirst().orElse(null);
    }

    public List<Actor> getActorList(){
        return this.actorList;
    }

    public Domain addUseCase(String useCase){
        return addUseCase(new UseCaseLeaf(useCase));
    }

    private Domain addUseCase(UseCase useCase){
        this.activeActor.addUseCase(useCase);
        return this;
    }

    public Domain addUseCaseComposite(String useCase){
        UseCaseComposite usc = new UseCaseComposite(useCase);
        this.activeActor.addUseCaseComposite(usc);
        return this;
    }

    public Domain addUseCaseToComposite(String useCase){
        this.activeActor.addUseCaseToComposite(new UseCaseLeaf(useCase));
        return this;
    }

    public Domain addUseCaseCompositeToComposite(String useCase){
        this.activeActor.addUseCaseCompositeToComposite(new UseCaseComposite(useCase));
        return this;
    }

}
