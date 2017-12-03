package edu.uoregon.yubo.tideappv2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhangyu on 7/10/16.
 */
public class TideSQLiteHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME ="tide.sqlite";
    private static final int DATABASE_VERSION =1;
    private Context context = null;

    public TideSQLiteHelper(Context c){
        super(c,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //create a new database
        db.execSQL("CREATE TABLE Item"
                + "( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Date TEXT,"
                + "City TEXT,"
                + "Day TEXT,"
                + "Time TEXT,"
                + "InFt TEXT,"
                + "InCm TEXT,"
                + "HighLow TEXT"
                + ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db ,int oldVersion, int newVersion){

    }
}
