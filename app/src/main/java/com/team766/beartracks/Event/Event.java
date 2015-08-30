package com.team766.beartracks.Event;

import com.team766.beartracks.Role.Attachment;

import java.util.HashMap;

/**
 * Created by tommypacker on 8/29/15.
 */
public class Event {
    private String creator;
    private String end;
    private String start;
    private String location;
    private String title;
    private HashMap<String, Attachment> attachments;

    public String getCreator(){
        return creator;
    }

    public String getEnd(){
        return end;
    }

    public String getStart(){
        return start;
    }

    public String getLocation(){
        return location;
    }

    public String getTitle(){
        return title;
    }

    public HashMap<String, Attachment> getAttachments(){
        return attachments;
    }
}
