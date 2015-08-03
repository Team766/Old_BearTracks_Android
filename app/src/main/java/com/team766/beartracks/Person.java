package com.team766.beartracks;

/**
 * Created by tommypacker on 7/30/15.
 */
public class Person {
    private String email;
    private String name;
    private String status;
    private String id;

    @SuppressWarnings("unused")
    public Person(){
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getStatus(){
        return status;
    }

    public String getId(){
        return id;
    }

}
