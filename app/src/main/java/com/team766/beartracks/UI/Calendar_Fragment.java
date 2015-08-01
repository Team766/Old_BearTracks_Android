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
import com.team766.beartracks.MainActivity;
import com.team766.beartracks.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tommypacker on 7/28/15.
 */
public class Calendar_Fragment extends Fragment implements WeekView.EventClickListener, WeekView.MonthChangeListener, WeekView.EventLongPressListener {

    private WeekView mWeekView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.calendar_fragment_layout, container, false);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) view.findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        mWeekView.setNumberOfVisibleDays(3);

        return view;
    }


    @Override
    public void onEventClick(WeekViewEvent event, RectF rectF) {
        Toast.makeText(this.getActivity(), "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth-1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.primary));
        events.add(event);


        return events;
    }

    private String getEventTitle(Calendar time) {
        return String.format("New Event at %02d:%02d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE));
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF rectF) {
        Toast.makeText(this.getActivity(), "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }


}
