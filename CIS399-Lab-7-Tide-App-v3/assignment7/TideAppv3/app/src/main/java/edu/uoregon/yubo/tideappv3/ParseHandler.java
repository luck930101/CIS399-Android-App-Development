package edu.uoregon.yubo.tideappv3;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by zhangyu on 7/7/16.
 */
public class ParseHandler extends DefaultHandler {
    private TideItems tideItems;
    private TideItem item;
    private String attsDate;

    private boolean isTime = false;
    private boolean isPred = false;
    private boolean isType = false;



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
            attsDate=atts.getValue("date");
            return;
        }
        else if (qName.equals("data")){
            item =new TideItem();
            return;
        }

        else if (qName.equals("time")){
            isTime = true;
            return;
        }
        else if (qName.equals("pred")){
            isPred = true;
            return;
        }
        else if (qName.equals("type")){
            isType = true;
            return;
        }

    }

    @Override
    public void endElement (String namespaceURI,String localName, String qName) throws SAXException{

        if(qName.equals("data")){
            tideItems.add(item);
        }
        return;
    }

    @Override
    public void characters(char ch[], int start , int length){
        String s = new String(ch, start,length);
        if (isTime){
            item.setTime(s);
            item.setDate(attsDate);
            isTime = false;
        }
        else if (isPred){
            item.setPred(s);
            isPred= false;
        }
        else if (isType){
            item.setType(s);
            isType = false;
        }
    }
}
