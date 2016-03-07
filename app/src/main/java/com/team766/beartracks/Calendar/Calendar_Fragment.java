package com.team766.beartracks.Calendar;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.team766.beartracks.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by tommypacker on 7/28/15.
 */
public class Calendar_Fragment extends Fragment implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener {

    //private FloatingActionButton fab;
    private WeekView mWeekView;
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private List<WeekViewEvent> preEvents = new ArrayList<WeekViewEvent>();
    private List<Calendar_Event> calEvents = new ArrayList<>();
    private Calendar_Event calEvent;
    private Firebase calRef;
    private int counter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.calendar_fragment_layout, container, false);

        calRef = new Firebase("https://beartracks.firebaseio.com/calendarEvents/");

        setHasOptionsMenu(true);

        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setNumberOfVisibleDays(3);
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));

        counter = 0; //Using counter to assign an index to each weekview event
        downloadCalendarEvents();

        return view;
    }

    private void downloadCalendarEvents(){
        calRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                calEvent = dataSnapshot.getValue(Calendar_Event.class);
                calEvent.setId(dataSnapshot.getKey());
                calEvents.add(calEvent);
                preEvents.add(getEvent(calEvent.getStart(), calEvent.getEnd(), calEvent.getTitle()));
                //Toast.makeText(getContext(), Integer.toString(counter), Toast.LENGTH_SHORT).show();
                counter++; //increment index
                mWeekView.notifyDatasetChanged();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.calendar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                setupDateTimeInterpreter(false);
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    //Readjust size
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    return true;
                }
            case R.id.action_three_day_view:
                setupDateTimeInterpreter(false);
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    //Readjust size
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                setupDateTimeInterpreter(true);
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    //Readjust size
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                //Get first letter of day name
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        for(WeekViewEvent event: preEvents){
            if (eventMatches(event, newYear, newMonth)) {
                events.add(event);
            }
        }
        return events;
    }

    private WeekViewEvent getEvent(long start, long end, String title){
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.setTimeInMillis(start);
        endTime.setTimeInMillis(end);

        WeekViewEvent event = new WeekViewEvent(counter, getEventTitle(startTime, title), startTime, endTime);
        event.setColor(getResources().getColor(R.color.primary));
        mWeekView.notifyDatasetChanged();
        //Toast.makeText(getContext(), Integer.toString((int)event.getId()), Toast.LENGTH_SHORT).show();
        return event;
    }

    private String getEventTitle(Calendar time, String title) {
        return String.format(title + " at %02d:%02d" + "\n\n", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE));
    }


    @Override
    public void onEventClick(WeekViewEvent event, RectF rectF) {
        Calendar_Event eventToView = calEvents.get((int)event.getId());
        Toast.makeText(getContext(), eventToView.getId(), Toast.LENGTH_SHORT).show();
        Bundle eventDetails = new Bundle();
        eventDetails.putString("eventId", eventToView.getId());
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF rectF) {
        Toast.makeText(this.getActivity(),event.getName(), Toast.LENGTH_LONG).show();
    }


}
