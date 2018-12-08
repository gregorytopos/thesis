package hu.ppke.itk.android.onlab.cellinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by User on 3.27.2018.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    //Database data
    public static final String DATABASE_NAME = "Database";
    public static final int DATABASE_VERSION = 1;

    //Table data
    public static final String TABLE_NAME = "Measurements";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_MIN = "minute";
    public static final String COLUMN_SEC = "second";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LAC = "lac";
    public static final String COLUMN_CID = "cid";
    public static final String COLUMN_MCCMNC = "mccmnc";
    public static final String COLUMN_SGN = "signal";
    public static final String COLUMN_BAND = "band";

    //Table creation
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_YEAR + " integer not null, "
            + COLUMN_MONTH + " integer not null, "
            + COLUMN_DAY + " integer not null, "
            + COLUMN_HOUR + " integer not null, "
            + COLUMN_MIN + " integer not null, "
            + COLUMN_SEC + " integer not null, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_LAC + " bigint not null, "
            + COLUMN_CID + " bigint not null, "
            + COLUMN_MCCMNC + " bigint not null, "
            + COLUMN_SGN + " bigint not null, "
            + COLUMN_BAND + " bigint not null"
            + ");";

    //Table deletion
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SQLiteHandler(Context context){super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db){db.execSQL(CREATE_TABLE);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    //Add measurement
    public void addMeasurement(Measurement measurement){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_YEAR, measurement.getYear());
        values.put(COLUMN_MONTH, measurement.getMonth());
        values.put(COLUMN_DAY, measurement.getDay());
        values.put(COLUMN_HOUR, measurement.getHour());
        values.put(COLUMN_MIN, measurement.getMinute());
        values.put(COLUMN_SEC, measurement.getSecond());
        values.put(COLUMN_NAME, measurement.getName());
        values.put(COLUMN_LAC, measurement.getLac());
        values.put(COLUMN_CID, measurement.getCid());
        values.put(COLUMN_MCCMNC, measurement.getMccmnc());
        values.put(COLUMN_SGN, measurement.getSgn());
        values.put(COLUMN_BAND, measurement.getBand());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //Get ALL measurements
    public ArrayList<Measurement> getMeasurements(){
        ArrayList<Measurement> measurementList = new ArrayList<Measurement>();
        String select = "SELECT * FROM " + TABLE_NAME +
                " ORDER BY " + COLUMN_YEAR + " ASC, "
                + COLUMN_MONTH + " ASC, "
                + COLUMN_DAY + " ASC, "
                + COLUMN_HOUR + " ASC, "
                + COLUMN_MIN + " ASC, "
                + COLUMN_SEC + " ASC;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()){
            do{
                Measurement measurement = new Measurement();
                measurement.setId(Integer.parseInt(cursor.getString(0)));
                measurement.setYear(Integer.parseInt(cursor.getString(1)));
                measurement.setMonth(Integer.parseInt(cursor.getString(2)));
                measurement.setDay(Integer.parseInt(cursor.getString(3)));
                measurement.setHour(Integer.parseInt(cursor.getString(4)));
                measurement.setMinute(Integer.parseInt(cursor.getString(5)));
                measurement.setSecond(Integer.parseInt(cursor.getString(6)));
                measurement.setName(cursor.getString(7));
                measurement.setLac(Integer.parseInt(cursor.getString(8)));
                measurement.setCid(Integer.parseInt(cursor.getString(9)));
                measurement.setMccmnc(Integer.parseInt(cursor.getString(10)));
                measurement.setSgn(Integer.parseInt(cursor.getString(11)));
                measurement.setBand(Integer.parseInt(cursor.getString(12)));
                measurementList.add(measurement);
            } while(cursor.moveToNext());
        }
        return measurementList;
    }

    //Delete measurement
    public void deleteMeasurement(Measurement measurement){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] {String.valueOf(measurement.getId())});
        db.close();
    }
}
