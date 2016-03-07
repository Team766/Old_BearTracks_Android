package com.team766.beartracks.Calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tommypacker for HackIllinois' 2016 Clue Hunt
 */
public class Calendar_Detail_Activity extends AppCompatActivity {

    private String eventUrlId;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            eventUrlId = extras.getString("eventId");
        }else{
            eventUrlId = "";
        }


    }
}
