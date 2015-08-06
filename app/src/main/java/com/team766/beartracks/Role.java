package com.team766.beartracks;

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
    private List<String> accountabilities;
    private List<String> authorities;
    private List<Attachment> attachments;

    public Role(){}

    private String getCreator(){
        return creator;
    }

    private String getGrade(){
        return grade;
    }

    private String getMentor(){
        return mentor;
    }

    private String getName(){
        return name;
    }

    private String getOwner(){
        return owner;
    }

    private String getStatus(){
        return status;
    }

    private String getSupervisor(){
        return supervisor;
    }

    private List<String> getAccountabilities(){
        return accountabilities;
    }

    private List<String> getAuthorities(){
        return authorities;
    }

    private List<Attachment> getAttachments(){
        return attachments;
    }




}
