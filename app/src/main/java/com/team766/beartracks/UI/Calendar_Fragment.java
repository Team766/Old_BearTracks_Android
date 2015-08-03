package com.team766.beartracks.UI;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team766.beartracks.CalendarEvent;
import com.team766.beartracks.MainActivity;
import com.team766.beartracks.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by tommypacker on 7/28/15.
 */
public class Calendar_Fragment extends Fragment implements WeekView.EventClickListener, WeekView.MonthChangeListener, WeekView.EventLongPressListener {

    private WeekView mWeekView;
    private List<WeekViewEvent> preEvents = new ArrayList<WeekViewEvent>();
    private CalendarEvent calEvent;
    private Firebase calRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.calendar_fragment_layout, container, false);

        calRef = new Firebase("https://beartracks.firebaseio.com/calendarEvents/");

        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setNumberOfVisibleDays(3);

        calRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot events : dataSnapshot.getChildren()) {
                    calEvent = events.getValue(CalendarEvent.class);
                    preEvents.add(getEvent(calEvent.getStart(), calEvent.getEnd(), calEvent.getTitle()));
                }
                mWeekView.notifyDatasetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        return view;
    }

    private void makeToast(String name){
        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF rectF) {
        Toast.makeText(this.getActivity(), "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        if(newMonth == 8){
            for(int i = 0; i<preEvents.size(); i++){
                events.add(preEvents.get(i));
            }
        }

        return events;
    }

    private WeekViewEvent getEvent(String start, String end, String title){
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            startTime.setTime(sdf.parse(start));
            endTime.setTime(sdf.parse(end));
        } catch (ParseException e){
            //Nothing
        }

        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime, title), startTime, endTime);
        event.setColor(getResources().getColor(R.color.primary));
        return event;
    }

    private String getEventTitle(Calendar time, String title) {
        return String.format(title + " at %02d:%02d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE));
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF rectF) {
        Toast.makeText(this.getActivity(), "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }


}
