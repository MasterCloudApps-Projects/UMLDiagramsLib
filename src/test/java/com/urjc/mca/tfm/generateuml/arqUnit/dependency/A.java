package com.urjc.mca.tfm.generateuml.arqUnit.dependency;

import com.urjc.mca.tfm.generateuml.arqUnit.base.C;

import java.util.List;

public class A {

    public void prueba(C c, List<D> dList){
        B aux = new B();
        if(true)
            aux.m();
    }

    private E getE() {
        return new E();
    }
}
