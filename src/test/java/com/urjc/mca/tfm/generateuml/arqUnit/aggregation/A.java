package com.urjc.mca.tfm.generateuml.arqUnit.aggregation;

import java.util.ArrayList;
import java.util.List;

public class A {
    //normalmente es de tipo collections
    public List<B> bs;
    public List<C> cs;

    public A(){
        load();
    }

    private void load() {
        cs = new ArrayList<C>();
    }

    public void setB(B b) {
        this.bs.add(b);
    }

    public List<B> getBs() {
        return bs;
    }
}
