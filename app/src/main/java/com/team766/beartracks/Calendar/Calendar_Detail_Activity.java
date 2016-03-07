package com.team766.beartracks.Calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.team766.beartracks.R;

import java.util.Calendar;

/**
 * Created by tommypacker for HackIllinois' 2016 Clue Hunt
 */
public class Calendar_Detail_Activity extends AppCompatActivity {

    private String eventUrlId = "";
    private TextView title, location, start, end;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_event_details);
        title = (TextView) findViewById(R.id.eventTitle);
        location = (TextView) findViewById(R.id.eventLocation);
        start = (TextView) findViewById(R.id.eventStart);
        end = (TextView) findViewById(R.id.eventEnd);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            eventUrlId = extras.getString("eventId");
        }

        if(eventUrlId != null && !eventUrlId.equals("")){
            setupEventDetails();
        }

    }

    private void setupEventDetails(){
        Firebase eventRef = new Firebase("https://beartracks.firebaseio.com/calendarEvents").child(eventUrlId);
        eventRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                switch (key){
                    case "title":
                        title.setText(dataSnapshot.getValue(String.class));
                        break;
                    case "location":
                        location.setText(dataSnapshot.getValue(String.class));
                        break;
                    case "start":
                        String startTime = getFormattedDate(dataSnapshot.getValue(Long.class));
                        start.setText(startTime);
                        break;
                    case "end":
                        String endTime = getFormattedDate(dataSnapshot.getValue(Long.class));
                        end.setText(endTime);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    private String getFormattedDate(long unixTime){
        String returnString;
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(unixTime);
        returnString = String.format("%02d:%02d" + "\n\n", time.get(Calendar.HOUR) , time.get(Calendar.MINUTE));
        returnString = returnString.trim();
        return returnString;
    }
}
