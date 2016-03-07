package com.team766.beartracks.Calendar;

import com.team766.beartracks.Role.Attachment;
import com.team766.beartracks.Roster.Member;

import java.util.HashMap;

/**
 * Created by tommypacker on 8/1/15.
 */
public class Calendar_Event {

    private String description;
    private String creator;
    private long end;
    private long start;
    private String location;
    private HashMap<String, Member> people;
    private String title;
    private HashMap<String, Calendar_SubEvent> subevents;
    private HashMap<String, Attachment> attachments;
    private String meals;
    private String supervision;
    private String id;

    public Calendar_Event(){}

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

    public HashMap<String, Member> getPeople(){
        return people;
    }

    public HashMap<String, Calendar_SubEvent> getSubevents(){
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

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
}
