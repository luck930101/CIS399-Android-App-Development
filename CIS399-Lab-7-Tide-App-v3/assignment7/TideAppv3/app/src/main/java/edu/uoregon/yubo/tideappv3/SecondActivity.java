package edu.uoregon.yubo.tideappv3;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import java.io.IOException;
import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangyu on 7/10/16.
 */
public class SecondActivity extends Activity implements OnItemClickListener {

    String stationId;
    private Dal dal = new Dal(this);
    Cursor cursor = null;
    SimpleCursorAdapter adapter = null;
    int gap;
    String stDate;
    String edDate;
    String location;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setContentView(R.layout.second_activity);


        Intent intent = getIntent();
        stDate = intent.getExtras().getString("startdate");
        edDate = intent.getExtras().getString("enddate");
        location = intent.getExtras().getString("location");

        //assign stationId for different city
        if (location.equals("Southbeach"))
        {
            stationId = "9435380";
        }
        else if (location.equals("Florence"))
        {
            stationId = "9434098";
        }
        else if (location.equals("Garibaldi"))
        {
            stationId = "9437540";
        }

        // convert String date to Date date
        String[] stDateArray = stDate.split("/");
        String[] edDateArray = edDate.split("/");

        Calendar stcalendar = Calendar.getInstance();
        stcalendar.set(Integer.parseInt(stDateArray[0]),Integer.parseInt(stDateArray[1])-1,Integer.parseInt(stDateArray[2]));
        Date startDate = stcalendar.getTime();

        Calendar edcalendar = Calendar.getInstance();
        edcalendar.set(Integer.parseInt(edDateArray[0]),Integer.parseInt(edDateArray[1])-1,Integer.parseInt(edDateArray[2]));
        Date endDate = edcalendar.getTime();

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(startDate);



        // format for database
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
        // format for qurey
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

        //find how many days between
        gap = (int)(endDate.getTime()-startDate.getTime())/(1000*60*60*24);


        Cursor[] mergeCursor = new Cursor[gap+1];
        //check missing data
        //if all data available, display in the listview
        //else if there is missing data, request web page
        for (int i = 0;i<=gap;i++){
            //query for each day
            mergeCursor[i] = dal.getItemFromDb(stationId,sdf1.format(currentCalendar.getTime()));
            if(mergeCursor[i].getCount()==0)
                //data missing!
            {
                SoapTask soapTask = new SoapTask();
                soapTask.execute(stationId,sdf2.format(currentCalendar.getTime()),sdf2.format(endDate));
                return;
            }

            currentCalendar.add(Calendar.DATE,1);
        }

        //display in view list
        cursor = new MergeCursor(mergeCursor);
        adapter = new SimpleCursorAdapter(this,
                R.layout.listview_items,
                cursor,
                new String[]{"Date","Time","Pred","Type"},
                new int[]{
                        R.id.dateTextView,
                        R.id.timeTextView,
                        R.id.hightTextView,
                        R.id.hlTextView},
        0 );

        ListView itemsListView =(ListView)findViewById(R.id.listView1);
        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(this);

    }


    private class SoapTask  extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

        /* Query a web service using kSoap */
            // 1. Create a SOAP request object, constructor wants namespace and operation
            final String TARGET_NAMESPACE =
                    "http://opendap.co-ops.nos.noaa.gov/axis/webservices/highlowtidepred/wsdl";
            final String OPERATION_NAME = "getHighLowTidePredictions";
            SoapObject request = new SoapObject(TARGET_NAMESPACE, OPERATION_NAME);
            request.addProperty("stationId", params[0]);         // type="xsd:string"
            request.addProperty("beginDate", params[1]+" 00:00");  // type="xsd:string"
            request.addProperty("endDate", params[2]+" 23:59");    // type="xsd:string"
            request.addProperty("datum", "MLLW");    // type="xsd:string"
            // the following parameter is optional: (default value is "Celsius")
            // request.addProperty("unit", "Fahrenheit");        // nillable="true" type="xsd:string"
            request.addProperty("timeZone", 1);                  // type="xsd:int"
//            request.addProperty("dataInterval", 60);                  //
            // 2. create SOAP envelope and add the request to it
            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            // envelope.implicitTypes = true;  // we only need this if we pass a wrong type to addProperty
            envelope.setOutputSoapObject(request);

            // 3. set up the transport object
            final String ENDPOINT =
                    "http://opendap.co-ops.nos.noaa.gov/axis/services/highlowtidepred";
            /* Note:endpoint is the <wsdlsoap:address location in the wsdl file:
            <wsdl:port binding="impl:WaterTemperatureSoapBinding" name="WaterTemperature">
                <wsdlsoap:address location="http://opendap.co-ops.nos.noaa.gov/axis/services/WaterTemperature"/>
             */

            HttpTransportSE transport = new HttpTransportSE(Proxy.NO_PROXY, ENDPOINT, 10000);
            transport.debug = true;
            final String SOAP_ACTION = ENDPOINT + "/" + OPERATION_NAME;
            try {
                transport.call(SOAP_ACTION, envelope);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            // 4. Get response
            String xmlResponse = transport.responseDump;
            System.out.println("Done with get data from severs "+params[0]);

            return xmlResponse;
        }

        @Override
        protected void onPostExecute(String xml) {
            dal.loadDb(xml,stationId);//write back into local database
            super.onPostExecute(xml);

            listViewDisplay();



        }
    }


    public void  listViewDisplay(){

        //display the entire query in list view

        if (location.equals("Southbeach"))
        {
            stationId = "9435380";
        }
        else if (location.equals("Florence"))
        {
            stationId = "9434098";
        }
        else if (location.equals("Garibaldi"))
        {
            stationId = "9437540";
        }

        String[] stDateArray = stDate.split("/");
        String[] edDateArray = edDate.split("/");

        // convert String date to Date date
        Calendar stcalendar = Calendar.getInstance();
        stcalendar.set(Integer.parseInt(stDateArray[0]),Integer.parseInt(stDateArray[1])-1,Integer.parseInt(stDateArray[2]));
        Date startDate = stcalendar.getTime();

        Calendar edcalendar = Calendar.getInstance();
        edcalendar.set(Integer.parseInt(edDateArray[0]),Integer.parseInt(edDateArray[1])-1,Integer.parseInt(edDateArray[2]));
        Date endDate = edcalendar.getTime();

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(startDate);



        //format for database
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("start is "+ sdf1.format(startDate));
        //format for request
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        System.out.println("end is "+ sdf2.format(endDate));

        //how many days between
        gap = (int)(endDate.getTime()-startDate.getTime())/(1000*60*60*24);

        Cursor[] mergeCursor = new Cursor[gap+1];
        //check missing data
        for (int i = 0;i<=gap;i++){
            //put data in to cursor
            mergeCursor[i] = dal.getItemFromDb(stationId,sdf1.format(currentCalendar.getTime()));
            currentCalendar.add(Calendar.DATE,1);
        }

        //get data
        cursor = new MergeCursor(mergeCursor);
        adapter = new SimpleCursorAdapter(this,
                R.layout.listview_items,
                cursor,
                new String[]{"Date","Time","Pred","Type"},
                new int[]{
                        R.id.dateTextView,
                        R.id.timeTextView,
                        R.id.hightTextView,
                        R.id.hlTextView},
                0 );

        ListView itemsListView =(ListView)findViewById(R.id.listView1);
        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(this);


    }

    @Override
    protected void onNewIntent(Intent intent){
        setIntent(intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    public void onItemClick(AdapterView<?> parent , View view, int position, long id){
        cursor.moveToPosition(position);
        Toast.makeText(this,  "I want put something here, but no more information..."
                ,Toast.LENGTH_LONG).show();
    }










}
