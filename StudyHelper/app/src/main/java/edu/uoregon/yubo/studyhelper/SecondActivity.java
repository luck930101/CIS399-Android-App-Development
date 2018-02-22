package edu.uoregon.yubo.studyhelper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zhangyu on 7/16/16.
 */
public class SecondActivity extends AppCompatActivity implements OnClickListener {
    private Button studyNowButton;
    private Button checkTreeButton;
    private EditText timeEditText;
    private String currentTime;
    private ImageView bgImage;
    private SharedPreferences prefs;
    private Boolean isStudying= false;
    private int mins;
    private int seconds;
    private int exp;
    private int totalExp;
    private TextView counter;
    private Handler handler = new Handler();
    private Runnable counter_thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.second_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        studyNowButton = (Button)findViewById(R.id.studynow);
        checkTreeButton = (Button)findViewById(R.id.secondchecktree);
        studyNowButton.setOnClickListener(this);
        checkTreeButton.setOnClickListener(this);
        timeEditText = (EditText)findViewById(R.id.timeeditview);
        counter = (TextView)findViewById(R.id.countertextview);
        bgImage = (ImageView)findViewById(R.id.background);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter_thread = new Runnable() {
            @Override
            public void run() {
                displayTime();
                seconds-=1;
                if (seconds>-1) {
                    //after 1 second, put thread back into thread pool
                    handler.postDelayed(counter_thread, 1000);
                }
                else{
                    //remove thread
                    handler.removeCallbacks(counter_thread);
                    //display checktree button
                    checkTreeButton.setVisibility(View.VISIBLE);
                    SharedPreferences.Editor editor = prefs.edit();
                    //stop studying
                    isStudying = false;
                    displayInformation();
                    //update SharedPreferences
                    editor.putBoolean("isstudying",isStudying);
                    editor.putString("total_exp",String.valueOf(totalExp+exp));
                    editor.commit();

                }


            }
        };
        counter.setText("00:00:00");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isstudying",false);
        editor.commit();





    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.studynow){
            try{
                mins=Integer.valueOf(timeEditText.getText().toString());
                seconds = mins*60;
                caculateExp();
                isStudying = true;
                handler.post(counter_thread);
                studyNowButton.setVisibility(View.INVISIBLE);
            }
            catch (Exception e){
                Toast.makeText(this,"Please input an integer!", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        else if(v.getId() == R.id.secondchecktree){
            // goto check tree state activity
            Intent intent = new Intent(this,ThirdActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void onPause(){

        super.onPause();
        //Using SharedPreferences to store data;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isstudying",isStudying);
        editor.putString("currenttime",currentTime);
        editor.putInt("exp",exp);
        editor.commit();
        if (prefs.getBoolean("isstudying",false)){
            //if student is studying
            Toast.makeText(this, "You are studying! you just lost "+exp+"Exp!",Toast.LENGTH_LONG).show();
            //set exp for this time to negative
            totalExp -= exp;
            editor.putString("total_exp",String.valueOf(totalExp));
            editor.commit();
            studyNowButton.setVisibility(View.INVISIBLE);
            checkTreeButton.setVisibility(View.VISIBLE);
            //remove thread
            handler.removeCallbacks(counter_thread);
        }

    }
    @Override
    public void onResume(){
        //get data from SharedPreferences;
        super.onResume();
        totalExp = (Integer.parseInt(prefs.getString("total_exp","0")));
        System.out.println(prefs.getBoolean("isstudying",false));
        currentTime =prefs.getString("currenttime","00:00:00");
        counter.setText(currentTime);
        exp = prefs.getInt("exp",0);
        changeBackground();


        if (prefs.getBoolean("isstudying",false)){
            //if student is studying
            Toast.makeText(this, "You are studying! you just lost "+exp+"Exp!",Toast.LENGTH_LONG).show();
            isStudying = false;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isstudying",isStudying);
            editor.commit();
            //hide studynow Button
            studyNowButton.setVisibility(View.INVISIBLE);
            //display check tree statr Button
            checkTreeButton.setVisibility(View.VISIBLE);
            //stop counting
            handler.removeCallbacks(counter_thread);

        }
        else {
            //if student is not studying
            //display studynowButton
            studyNowButton.setVisibility(View.VISIBLE);
        }

    }

    public void displayTime(){
        // display time format
        String hour;
        String minute;
        String second;

        hour = String.valueOf(seconds/60/60);
        if(seconds/60/60<10){
            hour= "0"+hour;
        }
        minute =String.valueOf(seconds/60%60);
        if(seconds/60%60<10){
            minute= "0"+minute;
        }
        second =String.valueOf(seconds%60);
        if(seconds%60<10){
            second= "0"+second;
        }
        currentTime = hour+":"+minute+":"+second;
        counter.setText(currentTime);
    }

    public void caculateExp(){
        //calculate exp
        exp = seconds;
        System.out.println("Exp for this time is "+ exp);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeBackground(){
        //set background
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

    public void displayInformation(){
        Toast.makeText(this, "You earn "+exp+"Exp!",Toast.LENGTH_LONG).show();
    }

}


