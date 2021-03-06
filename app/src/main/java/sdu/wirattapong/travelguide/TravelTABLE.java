package sdu.wirattapong.travelguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by SuPerJoWTF on 22/4/2560.
 */

public class TravelTABLE {

    //Explicit
    private  MySQLiteOpenHelper objMySQLiteOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase,readSqLiteDatabase;

    public static final String TRAVEL_TABLE = "travelTABLE" ;
    public static final String COLUMN_ID_TRAVEL = "_id" ;
    public static final String COLUMN_TRAVEL = "Travel" ;
    public static final String COLUMN_SOURCE = "Source";
    public static final String COLUMN_PRICE = "Price";

    public  TravelTABLE(Context context){
        objMySQLiteOpenHelper = new MySQLiteOpenHelper(context);
        writeSqLiteDatabase = objMySQLiteOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMySQLiteOpenHelper.getReadableDatabase();

    } // Constructor

    public  long addNewTravel(String strTravel, String strSource, String strPrice) {
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_TRAVEL, strTravel);
        objContentValues.put(COLUMN_SOURCE, strSource);
        objContentValues.put(COLUMN_PRICE, strPrice);

        return readSqLiteDatabase.insert(TRAVEL_TABLE, null, objContentValues);
    }

    public String[] readAllTravel(int intColumn) {
        String[] strReadAll = null;
        Cursor objCursor = readSqLiteDatabase.query(TRAVEL_TABLE,
                new String[]{COLUMN_ID_TRAVEL, COLUMN_TRAVEL, COLUMN_SOURCE, COLUMN_PRICE},
                null, null, null, null, null);
        if (objCursor != null) {
            objCursor.moveToFirst();
            strReadAll = new String[objCursor.getCount()];
            for (int i = 0; i<= objCursor.getCount(); i++) {
                switch (intColumn) {
                    case 1:
                        strReadAll[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_TRAVEL));
                        break;
                    case 2:
                        strReadAll[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_SOURCE));
                        break;
                    default:
                        strReadAll[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_PRICE));
                        break;
                }
                objCursor.moveToNext();
            }
        }
        return strReadAll;
    }

}
