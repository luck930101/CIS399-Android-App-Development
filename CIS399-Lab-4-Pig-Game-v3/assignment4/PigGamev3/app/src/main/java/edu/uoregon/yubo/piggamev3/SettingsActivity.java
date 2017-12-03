package edu.uoregon.yubo.piggamev3;

import android.app.Activity;
import android.os.Bundle;


public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }


}
