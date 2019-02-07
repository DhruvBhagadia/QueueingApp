package com.djunicode.queuingapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.djunicode.queuingapp.model.ActiveStudentQueues;

import java.util.ArrayList;
import java.util.List;

public class ActiveQueuesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "activeQueue.db";
    private static final int DATABASE_VERSION = 2;

    public ActiveQueuesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_QUEUE_TABLE = "CREATE TABLE " + ActiveQueuesContract.ActiveQueuesEntry.TABLE_NAME + " (" +
                ActiveQueuesContract.ActiveQueuesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ActiveQueuesContract.ActiveQueuesEntry.COLUMN_SUBJECT + " TEXT NOT NULL, " +
                ActiveQueuesContract.ActiveQueuesEntry.COLUMN_FROM + " TEXT NOT NULL, " +
                ActiveQueuesContract.ActiveQueuesEntry.COLUMN_TO + " TEXT NOT NULL, " +
                ActiveQueuesContract.ActiveQueuesEntry.COLUMN_LOCATION + " TEXT NOT NULL, " +
                ActiveQueuesContract.ActiveQueuesEntry.COLUMN_QUEUE_ID + " INTEGER NOT NULL " +
                "); ";
        db.execSQL(SQL_CREATE_QUEUE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ActiveQueuesContract.ActiveQueuesEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addQueue(ActiveStudentQueues activeStudentQueues){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ActiveQueuesContract.ActiveQueuesEntry.COLUMN_SUBJECT, activeStudentQueues.getSubject());
        cv.put(ActiveQueuesContract.ActiveQueuesEntry.COLUMN_FROM, activeStudentQueues.getStartTime());
        cv.put(ActiveQueuesContract.ActiveQueuesEntry.COLUMN_TO, activeStudentQueues.getEndTime());
        cv.put(ActiveQueuesContract.ActiveQueuesEntry.COLUMN_LOCATION, activeStudentQueues.getLocation());
        cv.put(ActiveQueuesContract.ActiveQueuesEntry.COLUMN_QUEUE_ID, activeStudentQueues.getId());

        db.insert(ActiveQueuesContract.ActiveQueuesEntry.TABLE_NAME, null, cv);
        Log.e("DBHelper", cv.toString());
        db.close();
    }

    public List<ActiveStudentQueues> getAllQueues() {
        List<ActiveStudentQueues> activeStudentQueuesList = new ArrayList<ActiveStudentQueues>();
        String selectQuery = "SELECT  * FROM " + ActiveQueuesContract.ActiveQueuesEntry.TABLE_NAME;
        Log.e("SQL active", ActiveQueuesContract.ActiveQueuesEntry.TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ActiveStudentQueues aQeues = new ActiveStudentQueues(null, null, null, null, null);
                aQeues.setSubject(cursor.getString(1));
                aQeues.setStartTime(cursor.getString(2));
                aQeues.setEndTime(cursor.getString(3));
                aQeues.setLocation(cursor.getInt(4));
                aQeues.setId(cursor.getInt(5));
                activeStudentQueuesList.add(aQeues);
            } while (cursor.moveToNext());
        }
        return activeStudentQueuesList;
    }

    public void deleteQueue(Integer queueId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ActiveQueuesContract.ActiveQueuesEntry.TABLE_NAME, ActiveQueuesContract.ActiveQueuesEntry.COLUMN_QUEUE_ID + " = ?",
                new String[] { String.valueOf(queueId) });
        db.close();
    }

}
