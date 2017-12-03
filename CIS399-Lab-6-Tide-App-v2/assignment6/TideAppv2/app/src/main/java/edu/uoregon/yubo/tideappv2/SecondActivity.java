package edu.uoregon.yubo.tideappv2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * Created by zhangyu on 7/10/16.
 */
public class SecondActivity extends Activity implements OnItemClickListener {

    private Dal dal = new Dal(this);
    Cursor cursor = null;
    SimpleCursorAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);


        Intent intent = getIntent();
        String date = intent.getExtras().getString("date");
        String location = intent.getExtras().getString("location");

        //get date depend on date and location
        cursor = dal.getItemFromDb(location,date);

        //display data in listview
        adapter = new SimpleCursorAdapter(this,
                R.layout.listview_items,
                cursor,
                new String[]{"Date","Day","Time","InFt","HighLow"},
                new int[]{
                        R.id.dateTextView,
                        R.id.dayTextView,
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
        String Ft = cursor.getString(cursor.getColumnIndex("InFt"));
        String Cm = cursor.getString(cursor.getColumnIndex("InCm"));
        //throw detile
        Toast.makeText(this,  Ft+ " ft., "
                        + Cm+ " cm"
                ,Toast.LENGTH_LONG).show();
    }












}
