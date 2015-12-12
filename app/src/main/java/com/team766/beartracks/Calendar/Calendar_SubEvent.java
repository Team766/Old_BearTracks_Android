package com.team766.beartracks.Calendar;

import com.team766.beartracks.Roster.Member;

import java.util.HashMap;

/**
 * Created by tommypacker on 8/3/15.
 */
public class Calendar_SubEvent {

    private String end;
    private String start;
    private HashMap<String, Member> people;
    private String title;
    private String type;
    private String description;
    private String time;

    public Calendar_SubEvent(){}

    public String getDescription(){
        return description;
    }

    public String getTime(){
        return time;
    }

    public String getEnd(){
        return end;
    }

    public String getStart(){
        return start;
    }

    public String getTitle(){
        return title;
    }

    public HashMap<String, Member> getPeople(){
        return people;
    }

    public String getType(){
        return type;
    }

}
