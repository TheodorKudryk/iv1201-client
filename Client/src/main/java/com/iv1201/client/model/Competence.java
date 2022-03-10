package com.iv1201.client.model;

/**
 * Class for saving competences with id and a name
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
