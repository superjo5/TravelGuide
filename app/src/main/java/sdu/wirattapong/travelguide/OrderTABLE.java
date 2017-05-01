package sdu.wirattapong.travelguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by SuPerJoWTF on 22/4/2560.
 */

public class OrderTABLE {

    //Explicit
    private  MySQLiteOpenHelper objMySQLiteOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase,readSqLiteDatabase;

    public static final String ORDER_TABLE = "orderTABLE" ;
    public static final String COLUMN_ID_ORDER = "_id" ;
    public static final String COLUMN_OFFICER = "Officer" ;
    public static final String COLUMN_DESK = "Desk";
    public static final String COLUMN_TRAVEL = "Travel";
    public static final String COLUMN_ITEM = "Item";

    public  OrderTABLE(Context context){
        objMySQLiteOpenHelper = new MySQLiteOpenHelper(context);
        writeSqLiteDatabase = objMySQLiteOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMySQLiteOpenHelper.getReadableDatabase();

    } // Constructor

    public  long addOrder(String strOfficer, String strDesk, String strTravel, String strItem) {
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_OFFICER, strOfficer);
        objContentValues.put(COLUMN_DESK, strDesk);
        objContentValues.put(COLUMN_TRAVEL, strTravel);
        objContentValues.put(COLUMN_ITEM, strItem);

        return readSqLiteDatabase.insert(ORDER_TABLE, null, objContentValues);
    }

}
