package com.ead.fuelpass.cons;

/**
 * Constants
 */
public class Constants {

    public static final String DATABASE_NAME = "FuelApp.db";
    public static final String USER_TABLE_NAME = "User";
    public static final String STATION_TABLE_NAME = "Station";
    public static final String TANK_TABLE_NAME = "Tank";
    public static final String QUEUE_TABLE_NAME = "Queue";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_TYPE = "type";
    public static final String USER_CUSTOMER = "customer";
    public static final String USER_OWNER = "owner";
    public static final String STATION_ID = "station_id";
    public static final String TANK_ID = "tank_id";
    public static final String QUEUE_ID = "queue_id";

    public static final String URL = "https://fuelbackendead.herokuapp.com/api/v1/";
    public static final String URL2 = "http://192.168.1.6:8080/api/v1/";

    public static final String USER_TABLE_CREATE = " CREATE TABLE " + USER_TABLE_NAME + "(" +
            USER_COLUMN_ID + " TEXT PRIMARY KEY," +
            USER_COLUMN_TYPE + " TEXT NOT NULL" + ")";
    public static final String STATION_TABLE_CREATE = " CREATE TABLE " + STATION_TABLE_NAME + "(" +
            STATION_ID + " TEXT PRIMARY KEY" + ")";
    public static final String TANK_TABLE_CREATE = " CREATE TABLE " + TANK_TABLE_NAME + "(" +
            TANK_ID + " TEXT PRIMARY KEY" + ")";
    public static final String QUEUE_TABLE_CREATE = " CREATE TABLE " + QUEUE_TABLE_NAME + "(" +
            QUEUE_ID  + " TEXT, " +
            TANK_ID + " TEXT," +
            STATION_ID + " TEXT," +
            "status" + " TEXT," +
            "type" + " TEXT," +
            "time" + " TEXT ," +
            "count" + " INT ," +
            " PRIMARY KEY"+" (tank_id, station_id) " + ")";


    public static final String USER_DROP_TABLE = "DROP TABLE IF EXISTS User";
    public static final String SELECT_ID_FROM_USER = "select id from User";
    public static final String GET_USER_TYPE = "select type from User";
    public static final String LOGOUT_WHERE = "id = ? ";


    public static final String STATION_DROP_TABLE = "DROP TABLE IF EXISTS Station";
    public static final String SELECT_ID_FROM_STATION = "select station_id from Station";
    public static final String STATION_WHERE = "station_id = ? ";


    public static final String TANK_DROP_TABLE = "DROP TABLE IF EXISTS Tank";
    public static final String SELECT_ID_FROM_TANK = "select tank_id from Tank";
    public static final String TANK_WHERE = "tank_id = ? ";


    public static final String QUEUE_DROP_TABLE = "DROP TABLE IF EXISTS Queue";
    public static final String SELECT_ALL_FROM_QUEUE = "select * from Queue";
    public static final String QUEUE_WHERE = "station_id = ? ";
}
