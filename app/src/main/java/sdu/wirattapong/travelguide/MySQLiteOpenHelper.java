package sdu.wirattapong.travelguide;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SuPerJoWTF on 22/4/2560.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //Explicit
    private static final String DATABASE_NAME = "TravelGuide.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_USER_TABLE = "create table userTABLE " +
            "(_id integer primary key, User text , Password text, Name text);";
    private static final String CREATE_TRAVEL_TABLE = "create table travelTABLE " +
            "(_id integer primary key, Travel text , Source text, Price text);";
    private static final String CREATE_ORDER_TABLE = "create table orderTABLE " +
            "(_id integer primary key, Officer text, Desk text, Travel text, Item text);";

    public MySQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    } // Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TRAVEL_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
} // Main Class
