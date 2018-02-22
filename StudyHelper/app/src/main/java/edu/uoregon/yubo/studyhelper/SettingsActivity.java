package edu.uoregon.yubo.studyhelper;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by zhangyu on 7/16/16.
 */
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
