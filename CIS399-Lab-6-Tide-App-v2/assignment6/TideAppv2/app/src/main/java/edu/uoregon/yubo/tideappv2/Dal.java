package edu.uoregon.yubo.tideappv2;

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

    public void loadDb(){
        TideItems items;


        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        //Put brighton tide items in the database
        ContentValues cv = new ContentValues();
        items = parseXml("brighton_9437815_annual.xml");
        for (TideItem item : items)
        {
            cv.put("Date", item.getDate());
            cv.put("Day",item.getDay());
            cv.put("City","Brighton");
            cv.put("Time",item.getTime());
            cv.put("InFt",item.getPredictionInFt());
            cv.put("InCm",item.getPredictionInCm());
            cv.put("HighLow",item.getHighlow());
            db.insert("Item",null,cv);
        }
        //Put florence tide items in the database
        items = parseXml("florence_9434032_annual.xml");
        for (TideItem item : items)
        {
            cv.put("Date", item.getDate());
            cv.put("Day",item.getDay());
            cv.put("City","Florence");
            cv.put("Time",item.getTime());
            cv.put("InFt",item.getPredictionInFt());
            cv.put("InCm",item.getPredictionInCm());
            cv.put("HighLow",item.getHighlow());
            db.insert("Item",null,cv);
        }

        items = parseXml("wauna_9439099_annual.xml");
        for (TideItem item : items)
        {
            cv.put("Date", item.getDate());
            cv.put("Day",item.getDay());
            cv.put("City","Wauna");
            cv.put("Time",item.getTime());
            cv.put("InFt",item.getPredictionInFt());
            cv.put("InCm",item.getPredictionInCm());
            cv.put("HighLow",item.getHighlow());
            db.insert("Item",null,cv);
        }

        db.close();
    }

    public Cursor getItemFromDb(String location,String date)
    {
        //Initialize the database
        System.out.println("I tried here!!!!!!!!!!!!!!!!!!");
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        //Get Tide Item for one location
        String query = "SELECT * FROM Item WHERE City = ? AND Date = ? ORDER BY Date ASC";
        String[] variables = new String[]{location, date};
        System.out.println("I dinished try ");
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


            InputStream in = context.getAssets().open(xmlData);
            InputSource is = new InputSource(in);
            xmlreader.parse(is);

            TideItems items = handler.getItems();
            return items;


        }
        catch (Exception e){
            Log.e("New reader", e.toString());
            return  null;
        }
    }





}
