package com.urjc.mca.tfm.generateuml.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Domain {

    private static final Logger LOG = LoggerFactory.getLogger(Domain.class);

    public final String name;
    private final List<Unit> unitList = new ArrayList<>();
    private Unit activeUnit;
    private String activePackage;
    private final List<Actor> actorList = new ArrayList<>();
    private Actor activeActor;
    private Function activeFunction;
    private Attribute activeAttribute;

    public Domain(String name) {
        this.name = name;
    }

    private Domain addUnit(Unit unit) {
        this.activeUnit = this.getUnit(unit);
        this.activeFunction = null;
        this.activeAttribute = null;
        return this;
    }

    public Domain addUnit(String unit) {
        LOG.debug("add unit: {}", unit);
        return addUnit(new Unit(unit));
    }

    public Domain setAbstractUnit() {
        LOG.debug("set abstract {}", this.activeUnit.name);
        this.activeUnit.setAbstractUnit();
        return this;
    }

    public Domain addPackage(String myPackage) {
        LOG.debug("add package: {}", myPackage);
        this.activePackage = myPackage;
        return this;
    }

    public Domain nonPackage() {
        LOG.debug("remove package");
        this.activePackage = "";
        return this;
    }

    private Unit getUnit(Unit unit) {
        Unit aux = getUnit(unit.name);
        if (aux == null) {
            if (StringUtils.isEmpty(unit.getMyPackage()))
                unit.setMyPackage(this.activePackage);
            unitList.add(unit);
            aux = unit;
        }
        return aux;
    }

    public Unit getUnit(String name) {
        LOG.debug("get unit:{}", name);
        return unitList.stream().filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    private Domain addBase(Unit unit) {
        this.activeUnit.addBase(getUnit(unit));
        return this;
    }

    public Domain addBase(String unit) {
        LOG.debug("add base:{}", unit);
        return addBase(new Unit(unit));
    }

    private Domain addPart(Unit unit) {
        this.activeUnit.addPart(getUnit(unit));
        return this;
    }

    public Domain addPart(String unit) {
        LOG.debug("add part:{}", unit);
        return addPart(new Unit(unit));
    }

    private Domain addElement(Unit unit) {
        this.activeUnit.addElement(getUnit(unit));
        return this;
    }

    public Domain addElement(String unit) {
        LOG.debug("add element:{}", unit);
        return addElement(new Unit(unit));
    }

    private Domain addAssociate(Unit unit) {
        this.activeUnit.addAssociate(getUnit(unit));
        return this;
    }

    public Domain addAssociate(String unit) {
        LOG.debug("add associate:{}", unit);
        return addAssociate(new Unit(unit));
    }

    private Domain addUsed(Unit unit) {
        this.activeUnit.addUsed(getUnit(unit));
        return this;
    }

    public Domain addUsed(String unit) {
        LOG.debug("add used:{}", unit);
        return addUsed(new Unit(unit));
    }


    public List<Unit> getUnitList() {
        return this.unitList;
    }

    public Domain setType(String type) {
        this.activeAttribute.setType(type);
        return this;
    }

    public Domain addAttribute(String name) {
        LOG.debug("add attribute:{}", name);
        this.activeAttribute = new Attribute(name);
        this.activeFunction = null;
        this.activeUnit.addAttribute(this.activeAttribute);
        return this;
    }

    public Domain addFunction(String name) {
        LOG.debug("add function:{}", name);
        this.activeAttribute = null;
        this.activeFunction = new Function(name);
        this.activeUnit.addFunction(activeFunction);
        return this;
    }

    public Domain addVisibility(Visibility visibility) {
        LOG.debug("add visibility {} {}", visibility.name(), activeFunction != null ? "into function" : "into attribute");
        if (activeFunction != null)
            this.activeFunction.setVisibility(visibility);
        else
            this.activeAttribute.setVisibility(visibility);
        return this;
    }

    public Domain addReturnType(String returnType) {
        LOG.debug("add return type:{}", returnType);
        this.activeFunction.setReturnTypeName(returnType);
        return this;
    }

    public Domain addParameters(String[] parameters) {
        LOG.debug("add parameters:{}", parameters);
        this.activeFunction.setParameters(parameters);
        return this;
    }

    public Domain setStatic(boolean staticValue) {
        if (activeFunction != null)
            this.activeFunction.setStaticFunction(staticValue);
        else
            this.activeAttribute.setStaticAttribute(staticValue);
        return this;
    }

    public List<Unit> getEfferent(String unit) {
        LOG.debug("get efferent:{}", unit);
        return Collections.singletonList(this.getUnit(unit));
    }

    public List<Unit> getAllEfferents() {
        LOG.debug("get all efferents");
        List<Unit> list = new ArrayList<>();
        unitList.forEach(unit -> {
            try {
                list.add(cloneForEfferent(unit, "efferent " + unit.name));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        return list;
    }

    private Unit cloneForEfferent(Unit unit, String packageDescription) throws IOException, ClassNotFoundException {
        Unit newUnit = unit.makeClone();
        newUnit.getBase().forEach(b -> b.setMyPackage(packageDescription));
        newUnit.getPartList().forEach(p -> p.setMyPackage(packageDescription));
        newUnit.getElements().forEach(e -> e.setMyPackage(packageDescription));
        newUnit.getAssociates().forEach(a -> a.setMyPackage(packageDescription));
        newUnit.getUsed().forEach(u -> u.setMyPackage(packageDescription));
        newUnit.setMyPackage(packageDescription);
        return newUnit;
    }

    public List<Unit> getAllAfferent() {
        List<Unit> allAfferent = new ArrayList<>();
        unitList.forEach(u -> {
            allAfferent.addAll(getAfferent(u.name, "afferent " + u.name));
        });
        return allAfferent;
    }

    private List<Unit> getAfferent(String unitName, String packageDescription) {
        LOG.debug("get afferent with package:{}-{}", unitName, packageDescription);
        return findAfferent(new Unit(unitName, packageDescription), true);
    }

    private List<Unit> findAfferent(Unit unit, boolean clone) {
        List<Unit> afferent = new ArrayList<>();
        unitList.forEach(u -> {
            Unit afferentUnit = createUnit(unit, clone, u);
            boolean find = false;
            if (u.getBase().contains(unit)) {
                afferentUnit.addBase(unit);
                find = true;
            }
            if (u.getPartList().contains(unit)) {
                afferentUnit.addPart(unit);
                find = true;
            }
            if (u.getAssociates().contains(unit)) {
                afferentUnit.addAssociate(unit);
                find = true;
            }
            if (u.getElements().contains(unit)) {
                afferentUnit.addElement(unit);
                find = true;
            }
            if (u.getUsed().contains(unit)) {
                afferentUnit.addUsed(unit);
                find = true;
            }
            if (find)
                afferent.add(afferentUnit);
        });
        return afferent;
    }

    private Unit createUnit(Unit unit, boolean clone, Unit u) {
        return clone ? new Unit(u.name, unit.getMyPackage()):new Unit(u.name);
    }

    public List<Unit> getAfferent(String unit) {
        LOG.debug("get afferent:{}", unit);
        return findAfferent(new Unit(unit), false);
    }


    private Domain addActor(Actor actor) {
        this.activeActor = this.getActor(actor);
        return this;
    }

    public Domain addActor(String actor) {
        LOG.debug("add actor:{}", actor);
        return addActor(new Actor(actor));
    }

    private Actor getActor(Actor actor) {
        Actor aux = getActor(actor.name);
        if (aux == null) {
            actorList.add(actor);
            aux = actor;
        }
        return aux;
    }

    public Actor getActor(String name) {
        LOG.debug("get actor:{}", name);
        return actorList.stream().filter(a -> a.name.equals(name)).findFirst().orElse(null);
    }

    public List<Actor> getActorList() {
        return this.actorList;
    }

    public Domain addUseCase(String useCase) {
        return addUseCase(new UseCaseLeaf(useCase));
    }

    private Domain addUseCase(UseCase useCase) {
        this.activeActor.addUseCase(useCase);
        return this;
    }

    public Domain addUseCaseComposite(String useCase) {
        UseCaseComposite usc = new UseCaseComposite(useCase);
        this.activeActor.addUseCaseComposite(usc);
        return this;
    }

    public Domain addUseCaseToComposite(String useCase) {
        this.activeActor.addUseCaseToComposite(new UseCaseLeaf(useCase));
        return this;
    }

    public Domain addUseCaseCompositeToComposite(String useCase) {
        this.activeActor.addUseCaseCompositeToComposite(new UseCaseComposite(useCase));
        return this;
    }

}
