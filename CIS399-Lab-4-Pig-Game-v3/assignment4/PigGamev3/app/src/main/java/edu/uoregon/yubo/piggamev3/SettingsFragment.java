package edu.uoregon.yubo.piggamev3;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by zhangyu on 7/3/16.
 */
public class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
        }


}
