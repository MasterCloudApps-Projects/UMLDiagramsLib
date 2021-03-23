package com.urjc.mca.tfm.generateuml.arqUnit.composition;

import com.urjc.mca.tfm.generateuml.arqUnit.dependency.D;

public class A {
    public B b;
    private D d;
    public A(){
        b = new B();
        init();
    }

    private void init() {
        d = new D();
    }
}
