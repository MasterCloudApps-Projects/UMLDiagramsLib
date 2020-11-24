package com.urjc.mca.tfm.generateuml.arqUnit.aggregation;

import java.util.List;

public class A {
    //normalmente es de tipo collections
    public List<B> bs;

    public void setB(B b) {
        this.bs.add(b);
    }

    public List<B> getBs() {
        return bs;
    }
}
