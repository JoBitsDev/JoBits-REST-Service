/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.persistence.repository;

/**
 * FirstDream
 *
 * @author Jorge
 *
 */
public enum PersistenceProperties {

    URL("javax.persistence.jdbc.url"),
    USER("javax.persistence.jdbc.user"),
    DRIVER("javax.persistence.jdbc.driver"),
    PASSWORD("javax.persistence.jdbc.password");

    private final String name;

    private PersistenceProperties(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    
    

}
