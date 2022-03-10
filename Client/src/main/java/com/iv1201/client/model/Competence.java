package com.iv1201.client.model;

/**
 *
 * @author theok
 */
public class Competence {
    private final int id;
    private final String name;
    
    public Competence(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}
