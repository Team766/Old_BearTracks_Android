package com.team766.beartracks.Role;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tommypacker on 8/6/15.
 */
public class Role {

    private String creator;
    private String grade;
    private String mentor;
    private String name; //purpose
    private String owner;
    private String status;
    private String supervisor;
    private String container;
    private String key;
    private String results;
    private String event;
    private HashMap<String, Accountability> accountabilities;
    private HashMap<String, Authority> authorities;
    private HashMap<String, Attachment> attachments;

    public Role(){}

    public String getCreator(){
        return creator;
    }

    public String getGrade(){
        return grade;
    }

    public String getMentor(){
        return mentor;
    }

    public String getName(){
        return name;
    }

    public String getOwner(){
        return owner;
    }

    public String getStatus(){
        return status;
    }

    public String getSupervisor(){
        return supervisor;
    }

    public String getContainer(){
        return container;
    }

    public String getResults(){
        return results;
    }

    public String getEvent(){
        return event;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public HashMap<String, Accountability> getAccountabilities(){
        return accountabilities;
    }

    public HashMap<String, Authority> getAuthorities(){
        return authorities;
    }

    public HashMap<String, Attachment> getAttachments(){
        return attachments;
    }




}
