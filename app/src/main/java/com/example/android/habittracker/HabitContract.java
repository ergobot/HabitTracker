package com.example.android.habittracker;


import android.provider.BaseColumns;
import android.text.format.Time;

public class HabitContract {

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    /*
    Inner class that defines the contents of the location table
    */
    public static final class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habit";
        public static final String COLUMN_ENTRY_KEY = "entry_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date";

    }

}
