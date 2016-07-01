package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class HabitDataSource {

    // Database fields
    private SQLiteDatabase database;
    private HabitDbHelper dbHelper;
    private HabitContract.HabitEntry entry;
    private String[] allColumns = {entry.COLUMN_ENTRY_KEY,
            entry.COLUMN_NAME,
            entry.COLUMN_DATE};

    public HabitDataSource(Context context) {
        dbHelper = new HabitDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addHabit(Habit habit) {
        open();
        ContentValues values = new ContentValues();
        values.put(entry.COLUMN_ENTRY_KEY, habit.entryId);
        values.put(entry.COLUMN_NAME, habit.name);
        values.put(entry.COLUMN_DATE, habit.date);
        database.insert(entry.TABLE_NAME, null, values);
    }

    public Cursor readData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                entry._ID,
                entry.COLUMN_ENTRY_KEY,
                entry.COLUMN_NAME,
                entry.COLUMN_DATE};

        String sortOrder =
                entry.COLUMN_NAME + " DESC";

        Cursor c = db.query(
                entry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return c;
    }

    public void deleteAll(){
        open();
        database.delete(entry.TABLE_NAME,null,null);
    }

    public void updateNameById(String name, long id){

        ContentValues values = new ContentValues();
        values.put(entry.COLUMN_NAME, name);

        String selection = entry.COLUMN_ENTRY_KEY + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id)};

        dbHelper.getReadableDatabase().update(
                entry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }




}
