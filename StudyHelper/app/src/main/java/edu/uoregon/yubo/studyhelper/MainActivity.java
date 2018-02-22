package edu.uoregon.yubo.studyhelper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private SharedPreferences prefs;
    private ImageView bgImage;
    private ImageView treeImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        Button startStudyButton = (Button)findViewById(R.id.startstudy);
        Button checkTreeButton = (Button)findViewById(R.id.firstchecktree);
        bgImage = (ImageView)findViewById(R.id.background);
        treeImage = (ImageView)findViewById(R.id.homepagetree);

        startStudyButton.setOnClickListener(this);
        checkTreeButton.setOnClickListener(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

    }
    @Override
    public void onResume(){
        //get data from SharedPreferences;
        super.onResume();
        displayImage(Integer.parseInt(prefs.getString("total_exp","0")));
        changeBackground();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_pig_game,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu
        switch (item.getItemId()) {
            case R.id.menu_settings:

                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                Toast.makeText(this, "StudyHelper! \n It helps get raid of your phone!",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override

    public void onClick(View v){
        if(v.getId() == R.id.startstudy){
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("currenttime","00:00:00");
            editor.commit();
        }
        else if(v.getId() == R.id.firstchecktree){
            Intent intent = new Intent(this,ThirdActivity.class);
            startActivity(intent);

        }


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeBackground(){
        String background;
        background = prefs.getString("background","ocean");

        if(background.equals("ocean")){
            bgImage.setBackground(getDrawable(R.drawable.ocean));

        }
        else if(background.equals("flower")){
            bgImage.setBackground(getDrawable(R.drawable.flower));
        }
        else if(background.equals("mountain")){
            bgImage.setBackground(getDrawable(R.drawable.mountain));
        }
        else if(background.equals("sky")){
            bgImage.setBackground(getDrawable(R.drawable.sky));
        }
    }

    private void displayImage(int exp) {
        //display dice image
        int level = 1;
        if (exp<60){
            level =1;
        }
        else if(exp>=60&&exp<200){
            level = 2;
        }
        else if(exp>=200&&exp<900){
            level = 3;
        }
        else if(exp>=900){
            level = 4;
        }
        int id = 0;

        switch(level)
        {
            case 1:
                id = R.drawable.level1;
                break;
            case 2:
                id = R.drawable.level2;
                break;
            case 3:
                id = R.drawable.level3;
                break;
            case 4:
                id = R.drawable.level4;
                break;

        }
        treeImage.setImageResource(id);
    }






}
