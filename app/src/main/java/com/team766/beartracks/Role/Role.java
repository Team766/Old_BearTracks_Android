package com.team766.beartracks.Role;

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
    private List<Accountability> accountabilities;
    private List<Authority> authorities;
    private List<Attachment> attachments;

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

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public List<Accountability> getAccountabilities(){
        return accountabilities;
    }

    public List<Authority> getAuthorities(){
        return authorities;
    }

    public List<Attachment> getAttachments(){
        return attachments;
    }




}
