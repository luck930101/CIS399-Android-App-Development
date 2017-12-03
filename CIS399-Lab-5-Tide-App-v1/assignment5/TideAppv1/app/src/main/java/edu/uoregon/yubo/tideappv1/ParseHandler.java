package edu.uoregon.yubo.tideappv1;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by zhangyu on 7/7/16.
 */
public class ParseHandler extends DefaultHandler {
    private TideItems tideItems;
    private TideItem item;

    private boolean isDate = false;
    private boolean isDay = false;
    private boolean isTime = false;
    private boolean isHighLow = false;
    private boolean isPredictionInFt = false;
    private boolean isPredictionInCm = false;


    public TideItems getItems(){return tideItems;}

    @Override
    public void startDocument() throws SAXException{
        tideItems = new TideItems();
        item = new TideItem();
    }

    @Override
    public void startElement (String namespaceURI, String localName,
                              String qName, Attributes atts) throws SAXException{
        if(qName.equals("item")){
            item = new TideItem();
            return;
        }
        else if (qName.equals("date")){
            isDate = true;
            return;
        }

        else if (qName.equals("day")){
            isDay = true;
            return;
        }
        else if (qName.equals("time")){
            isTime = true;
            return;
        }
        else if (qName.equals("predictions_in_ft")){
            isPredictionInFt = true;
            return;
        }
        else if (qName.equals("predictions_in_cm")){
            isPredictionInCm = true;
            return;
        }
        else if (qName.equals("highlow")){
            isHighLow = true;
            return;
        }

    }

    @Override
    public void endElement (String namespaceURI,String localName, String qName) throws SAXException{

        if(qName.equals("item")){
            tideItems.add(item);
        }
        return;
    }

    @Override
    public void characters(char ch[], int start , int length){
        String s = new String(ch, start,length);
        if (isDate){
            item.setDate(s);
            isDate = false;
        }
        else if (isDay){
            item.setDay(s);
            System.out.println(s);
            isDay = false;
        }
        else if (isTime){
            item.setTime(s);
            System.out.println(s);
            isTime = false;
        }
        else if (isPredictionInFt){
            item.setPredictionInFt(s);
            System.out.println(s);
            isPredictionInFt = false;
        }
        else if (isPredictionInCm){
            item.setPredictionInCm(s);
            System.out.println(s);
            isPredictionInCm = false;
        }
        else if (isHighLow){
            if (s.equals("L")){item.setHighlow("Low");}
            else if (s.equals("H")){item.setHighlow("High");}

            System.out.println(s);
            isHighLow = false;
        }
    }
}
