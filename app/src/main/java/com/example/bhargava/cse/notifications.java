package com.example.bhargava.cse;

import android.content.ContentResolver;
import android.content.ContentValues;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.TimeZone;

public class notifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
//        addReminder(2017,7,9,8,30,2017,7,9,8,31);
//        addReminderInCalendar();

    }

//    public void addReminder(int statrYear, int startMonth, int startDay, int startHour, int startMinut, int endYear, int endMonth, int endDay, int endHour, int endMinuts){
//        Calendar beginTime = Calendar.getInstance();
//        beginTime.set(statrYear, startMonth, startDay, startHour, startMinut);
//        long startMillis = beginTime.getTimeInMillis();
//
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(endYear, endMonth, endDay, endHour, endMinuts);
//        long endMillis = endTime.getTimeInMillis();
//
//        String eventUriString = "content://com.android.calendar/events";
//        ContentValues eventValues = new ContentValues();
//
//        eventValues.put(Events.CALENDAR_ID, 1);
//        eventValues.put(Events.TITLE, "OCS");
//        eventValues.put(Events.DESCRIPTION, "Clinic App");
//        eventValues.put(Events.EVENT_TIMEZONE, "Nasik");
//        eventValues.put(Events.DTSTART, startMillis);
//        eventValues.put(Events.DTEND, endMillis);
//
//        //eventValues.put(Events.RRULE, "FREQ=DAILY;COUNT=2;UNTIL="+endMillis);
//        eventValues.put(Events.HAS_ALARM, 1);
//
//        Uri eventUri = getContentResolver().insert(Uri.parse(eventUriString), eventValues);
//        long eventID = Long.parseLong(eventUri.getLastPathSegment());
//
//        /***************** Event: Reminder(with alert) Adding reminder to event *******************/
//
//        String reminderUriString = "content://com.android.calendar/reminders";
//
//        ContentValues reminderValues = new ContentValues();
//
//        reminderValues.put("event_id", eventID);
//        reminderValues.put("minutes", 1);
//        reminderValues.put("method", 1);
//
//        Uri reminderUri = getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
//    }
private void addReminderInCalendar() {
    Calendar cal = Calendar.getInstance();
    Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
    ContentResolver cr = getApplicationContext().getContentResolver();
    TimeZone timeZone = TimeZone.getDefault();

    /** Inserting an event in calendar. */
    ContentValues values = new ContentValues();
    values.put(CalendarContract.Events.CALENDAR_ID, 1);
    values.put(CalendarContract.Events.TITLE, "Sanjeev Reminder 01");
    values.put(CalendarContract.Events.DESCRIPTION, "A test Reminder.");
    values.put(CalendarContract.Events.ALL_DAY, 0);
    // event starts at 11 minutes from now
    values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis() + 1 * 60 * 1000);
    // ends 60 minutes from now
    values.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 2 * 60 * 1000);
    values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
    values.put(CalendarContract.Events.HAS_ALARM, 1);
    Uri event = cr.insert(EVENTS_URI, values);

    // Display event id.
    Toast.makeText(getApplicationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();

    /** Adding reminder for event added. */
    Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
    values = new ContentValues();
    values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
    values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
    values.put(CalendarContract.Reminders.MINUTES, 10);
    cr.insert(REMINDERS_URI, values);
}

    /** Returns Calendar Base URI, supports both new and old OS. */
    private String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (android.os.Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                        .parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }

}