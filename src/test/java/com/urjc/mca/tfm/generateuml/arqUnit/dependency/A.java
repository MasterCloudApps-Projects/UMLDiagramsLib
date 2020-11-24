package com.urjc.mca.tfm.generateuml.arqUnit.dependency;

import com.urjc.mca.tfm.generateuml.arqUnit.base.C;

public class A {

    public void prueba(C c){
        B aux = new B();
        if(true)
            aux.m();
    }
}
