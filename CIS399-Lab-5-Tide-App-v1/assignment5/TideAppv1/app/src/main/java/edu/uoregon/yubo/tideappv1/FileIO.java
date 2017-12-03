package edu.uoregon.yubo.tideappv1;

import android.content.Context;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by zhangyu on 7/7/16.
 */
public class FileIO {
    private final String FILENAME = "tide.xml";
    private Context context =null;

    public FileIO (Context context){this.context = context;}

    public TideItems readFile(){

        try{

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader xmlreader = parser.getXMLReader();

            ParseHandler handler = new ParseHandler();
            xmlreader.setContentHandler(handler);

            InputStream in = context.getAssets().open(FILENAME);

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
