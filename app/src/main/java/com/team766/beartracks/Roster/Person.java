package com.team766.beartracks.Roster;

import java.util.HashMap;
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
    private String graduation;
    private String phone;
    private String photo;
    private HashMap<String, String> associated;
    private String key;
    private int notificationPeriod;

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

    public HashMap<String,String> getAssociated(){
        return associated;
    }

    public int getSupervisor(){
        return supervisor;
    }

    public String getGraduation(){
        return graduation;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public int getNotificationPeriod(){
        return notificationPeriod;
    }
}
