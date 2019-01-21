package com.example.breathe.util;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Calendar;

public class Prefs {
    private SharedPreferences preferences;
    private static int dayOfMonth;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setDate(long milliseconds) {
        preferences.edit().putLong("seconds", milliseconds).apply();
    }
    public void setSessions(int session) {
        preferences.edit().putInt("sessions", session).apply();
    }

    public void setBreaths(int breaths) {
        preferences.edit().putInt("breaths", breaths).apply(); // saving our breaths into system file
    }

    public void setDay() {
        dayOfMonth = Calendar.DAY_OF_MONTH;
    }

   public String getDate() {
        long milliDate = preferences.getLong("seconds", 0);
        String amOrPm;

       Calendar calender = Calendar.getInstance();
       calender.setTimeInMillis(milliDate);

       int a = calender.get(Calendar.AM_PM);
       if (a == Calendar.AM)
           amOrPm = "AM";
       else
           amOrPm = "PM";

       String time = "last session " + calender.get(Calendar.HOUR_OF_DAY) + ":" + calender.get(Calendar.MINUTE)
               + " " + amOrPm;

       return time;
   }

    public int getSessions() {
        return preferences.getInt("sessions", 0);
    }

    public int getBreaths() {
        return preferences.getInt("breaths", 0);
    }

    public int getDay() {return dayOfMonth;}
}
