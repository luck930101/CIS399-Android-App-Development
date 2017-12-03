package edu.uoregon.yubo.tideappv3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by zhangyu on 7/10/16.
 */

//Data Access Layer
public class Dal {
    private Context context = null;
    public Dal(Context context){this.context = context;}

    public void loadDb(String xmlData,String stationId){
        TideItems items;
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        //Put  tide items in the database
        ContentValues cv = new ContentValues();
        items = parseXml(xmlData);
        for (TideItem item : items)
        {
            cv.put("City",stationId);
            cv.put("Date",item.getDate());
            cv.put("Time",item.getTime());
            cv.put("Pred",item.getPred());
            cv.put("Type",item.getType());
            db.insert("Item",null,cv);
        }

        db.close();
    }

    public Cursor getItemFromDb(String location,String date)
    {
        //Initialize the database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        //Get Tide Item for one location
        //For each query ,we only got record for one day
//        String query = "SELECT * FROM Item WHERE City = ? AND Date = ? ORDER BY Date ASC";
//        String[] variables = new String[]{location, date};
        String query = "SELECT * FROM Item WHERE City = ? AND Date = ? ORDER BY Date ASC";
        String[] variables = new String[]{location,date};
        return db.rawQuery(query,variables);
    }


    public TideItems parseXml(String xmlData){

        try{
            // get the XML reader
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader xmlreader = parser.getXMLReader();

            //set content handler
            ParseHandler handler = new ParseHandler();
            xmlreader.setContentHandler(handler);

            //read xml String
            xmlreader.parse(new InputSource(new StringReader(xmlData)));

            TideItems items = handler.getItems();
            return items;


        }
        catch (Exception e){
            Log.e("New reader", e.toString());
            return  null;
        }
    }





}
