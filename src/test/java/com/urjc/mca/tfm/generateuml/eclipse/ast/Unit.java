package com.urjc.mca.tfm.generateuml.eclipse.ast;


import com.urjc.mca.tfm.generateuml.eclipse.ast.base.BaseClase;
import com.urjc.mca.tfm.generateuml.eclipse.ast.base.BaseInterfaz;

import java.util.List;

public class Unit extends BaseClase implements BaseInterfaz {
    Composition composition;
    Association association;
    List<Aggregation> aggregationList;

    public Unit(Association associationSet){
        composition = new Composition();
        this.association = associationSet;
    }

    public List<Aggregation> getAggregationList() {
        return aggregationList;
    }

    public void addAggregationList(Aggregation aggregationList) {
        this.aggregationList.add(aggregationList);
    }

    public void test1(DependencyParameter dependencyParameter){
        DependecyCreate dependecyCreate = new DependecyCreate();

    }
}
