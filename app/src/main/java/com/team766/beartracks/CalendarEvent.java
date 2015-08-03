package com.team766.beartracks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by tommypacker on 8/1/15.
 */
public class CalendarEvent {

    private String description;
    private String creator;
    private String end;
    private String start;
    private String location;
    private List<Person> people;
    private String title;
    private List<subEvent> subevents;

    public CalendarEvent(){}

    public String getDescription(){
        return description;
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public List<Person> getPeople(){
        return people;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public List<subEvent> getSubevents(){
        return subevents;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public String getCreator(){
        return creator;
    }

}
