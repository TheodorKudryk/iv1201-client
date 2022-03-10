package com.iv1201.client.model;

/**
 *
 * @author leohj
 */
public class Person {
    private final int id;
    private final String name;
    private final String token;
    private final String role;

    
    public Person(int id, String name, String token, String role) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.role = role;
    }
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getToken(){
        return this.token;
    }
    
    public String getRole(){
        return this.role;
    }
}
