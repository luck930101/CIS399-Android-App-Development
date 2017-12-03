package edu.uoregon.yubo.tideappv1;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements OnItemClickListener{

    private FileIO io;
    private TideItems tideItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        io = new FileIO(getApplicationContext());
        tideItems=io.readFile();


        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();

        for(TideItem item : tideItems){
            HashMap<String,String> map = new HashMap<String, String>();
            //map.put("date", item.getForecastDateFormatted());
            map.put("info", item.getDate()+ " "+ item.getDay() + "\r\n"+ item.getHighlow()+": "+item.getTime());


            data.add(map);
        }


        SimpleAdapter adapter = new SimpleAdapter(this,
                data,
                R.layout.listview_items,
                new String[]{"info"},
                new int[]{R.id.dateTextView,}
        );
        ListView itemsListView =(ListView)findViewById(R.id.listView1);
        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent , View view, int position, long id){
        TideItem item = tideItems.get(position);
        Toast.makeText(this, item.getPredictionInFt() + " ft., "
                + item.getPredictionInCm()+ " cm"
                ,Toast.LENGTH_LONG).show();
    }
}
