package com.ead.fuelpass.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ead.fuelpass.cons.Constants;
import com.ead.fuelpass.model.QueueData;

import java.util.ArrayList;
import java.util.UUID;


/**
 * sqlite database controller
 */
public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constants.USER_TABLE_CREATE);
        sqLiteDatabase.execSQL(Constants.STATION_TABLE_CREATE);
        sqLiteDatabase.execSQL(Constants.TANK_TABLE_CREATE);
        sqLiteDatabase.execSQL(Constants.QUEUE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(Constants.USER_DROP_TABLE);
        sqLiteDatabase.execSQL(Constants.STATION_DROP_TABLE);
        sqLiteDatabase.execSQL(Constants.TANK_DROP_TABLE);
        sqLiteDatabase.execSQL(Constants.QUEUE_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }


    // add user to database
    public void insertUser(String id, String type,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.USER_COLUMN_ID, id);
        contentValues.put(Constants.USER_COLUMN_TYPE, type);
        db.insert(Constants.USER_TABLE_NAME, null, contentValues);
    }


    //get user id
    @SuppressLint("Range")
    public String getId() {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery(Constants.SELECT_ID_FROM_USER, null);

        String state = "";
        if (res.moveToFirst()) {
            do {
                state = res.getString(res.getColumnIndex(Constants.USER_COLUMN_ID));
            } while (res.moveToNext());
        }

        return state;
    }

    //get user type
    @SuppressLint("Range")
    public String getType() {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery(Constants.GET_USER_TYPE, null);

        String state = "";
        if (res.moveToFirst()) {
            do {
                state = res.getString(res.getColumnIndex(Constants.USER_COLUMN_TYPE));
            } while (res.moveToNext());
        }

        return state;
    }


    //logout
    public Integer deleteUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Constants.USER_TABLE_NAME, Constants.LOGOUT_WHERE, new String[]{id});
    }


    //add station id
    public void insertStation(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.STATION_ID, id);
        db.insert(Constants.STATION_TABLE_NAME, null, contentValues);
    }

    //get station id
    @SuppressLint("Range")
    public String getStationId() {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery(Constants.SELECT_ID_FROM_STATION, null);

        String state = "";
        if (res.moveToFirst()) {
            do {
                state = res.getString(res.getColumnIndex(Constants.STATION_ID));
            } while (res.moveToNext());
        }

        return state;
    }


    //delete station id
    public Integer deleteStation(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Constants.STATION_TABLE_NAME, Constants.STATION_WHERE, new String[]{id});
    }



    // add selected fuel tank
    public void insertTank(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TANK_ID, id);
        db.insert(Constants.TANK_TABLE_NAME, null, contentValues);
    }


    //get tank id
    @SuppressLint("Range")
    public String getTankId() {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery(Constants.SELECT_ID_FROM_TANK, null);

        String state = "";
        if (res.moveToFirst()) {
            do {
                state = res.getString(res.getColumnIndex(Constants.TANK_ID));
            } while (res.moveToNext());
        }

        return state;
    }


    //delete tank id
    public Integer deleteTank(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Constants.TANK_TABLE_NAME, Constants.TANK_WHERE, new String[]{id});
    }


    //save queue data
            public void insertQueue(QueueData d) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.QUEUE_ID, d.getQ_id());
            contentValues.put(Constants.TANK_ID, d.getTank_id());
            contentValues.put(Constants.STATION_ID,d.getStaion_id());
            contentValues.put("type", d.getType());
            contentValues.put("status", d.getStatus());
            contentValues.put("time", d.getTime());
            contentValues.put("count", d.getCount());
            db.insert(Constants.QUEUE_TABLE_NAME, null, contentValues);
    }


    //get queue data

    @SuppressLint("Range")
    public  ArrayList<QueueData> getQueue(String nm) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery("SELECT * FROM Queue WHERE TRIM(station_id) = '"+nm.trim()+"'", null);

        ArrayList<QueueData> data = new ArrayList<>();
        if (res.moveToFirst()) {
            do {
                String q_id = res.getString(res.getColumnIndex("queue_id"));
                String tank_id = res.getString(res.getColumnIndex("tank_id"));
                String station_id = res.getString(res.getColumnIndex("station_id"));
                String status = res.getString(res.getColumnIndex("status"));
                String status2 = res.getString(res.getColumnIndex("status2"));
                String type = res.getString(res.getColumnIndex("type"));
                int count = res.getInt(res.getColumnIndex("count"));
                String  time = res.getString(res.getColumnIndex("time"));
                QueueData q = new QueueData(q_id,tank_id,station_id,status,status2,type,time,count);
                data.add(q);
            } while (res.moveToNext());
        }

        return data;
    }



    //delete queue data
    public void  deleteQueue() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  from "+ Constants.QUEUE_TABLE_NAME);
    }

}
