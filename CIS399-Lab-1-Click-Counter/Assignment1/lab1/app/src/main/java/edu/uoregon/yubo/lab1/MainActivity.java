package edu.uoregon.yubo.lab1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private int currentNum = 0 ;
    private TextView displayBoard;
    private SharedPreferences savedValues;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayBoard = (TextView) findViewById(R.id.textView);
        savedValues =getSharedPreferences("savedValues", MODE_PRIVATE);

    }

    @Override

    public void onPause(){
        //save the currentNum
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putInt("currentNum",currentNum);
        editor.commit();
        super.onPause();
    }

    @Override

    public void onResume(){
        //get the currentNum
        super.onResume();
        currentNum = savedValues.getInt("currentNum",currentNum);
        displayBoard.setText(String.valueOf(currentNum));




    }

    public void add (View source) {
        //increase counter by 1
        currentNum +=1;
        displayBoard.setText(String.valueOf(currentNum));


    }

    public void reset (View source) {
        //reset counter
        currentNum =0;
        displayBoard.setText(String.valueOf(currentNum));

    }

}
