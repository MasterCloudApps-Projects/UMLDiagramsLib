package com.urjc.mca.tfm.generateuml.arqUnit.annotation.spring;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class EntityClass {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
