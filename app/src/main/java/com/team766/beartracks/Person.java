package com.team766.beartracks;

import java.util.List;

/**
 * Created by tommypacker on 7/30/15.
 */
public class Person {
    private String email;
    private String name;
    private String status;
    private String id;
    private int supervisor;
    private int graduation;
    private String phone;
    private String photo;
    private List<Person> associated;
    private String key;

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

    public String getPhone(){
        return phone;
    }

    public String getPhoto(){
        return photo;
    }

    public List<Person> getAssociated(){
        return associated;
    }

    public int getSupervisor(){
        return supervisor;
    }

    public int getGraduation(){
        return graduation;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }
}
