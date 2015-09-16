package com.team766.beartracks.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.team766.beartracks.Role.Attachment;
import com.team766.beartracks.Roster.Person;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tommypacker on 8/1/15.
 */
public class CalendarEvent {

    private String description;
    private String creator;
    private long end;
    private long start;
    private String location;
    private HashMap<String, Person> people;
    private String title;
    private HashMap<String, subEvent> subevents;
    private HashMap<String, Attachment> attachments;
    private String meals;
    private String supervision;

    public CalendarEvent(){}

    public String getDescription(){
        return description;
    }

    public long getEnd(){
        return end;
    }

    public long getStart(){
        return start;
    }

    public String getLocation(){
        return location;
    }

    public String getTitle(){
        return title;
    }

    public HashMap<String, Person> getPeople(){
        return people;
    }

    public HashMap<String, subEvent> getSubevents(){
        return subevents;
    }

    public String getCreator(){
        return creator;
    }

    public String getMeals(){
        return meals;
    }

    public String getSupervision(){
        return supervision;
    }

    public HashMap<String, Attachment> getAttachments(){
        return attachments;
    }

}
