package edu.uoregon.yubo.studyhelper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * Created by zhangyu on 7/16/16.
 */
public class ThirdActivity extends AppCompatActivity implements OnClickListener{
    private ImageView treeImage;
    private Button backToHomepageButton;
    private Button displayExpButton;
    private Button resetTreeButton;
    private ImageView bgImage;
    private SharedPreferences prefs;
    private int totalExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        treeImage = (ImageView)findViewById(R.id.tree);
        backToHomepageButton = (Button)findViewById(R.id.backhome);
        displayExpButton =(Button)findViewById(R.id.displayexp);
        resetTreeButton =(Button)findViewById(R.id.reset);
        backToHomepageButton.setOnClickListener(this);
        displayExpButton.setOnClickListener(this);
        resetTreeButton.setOnClickListener(this);
        bgImage = (ImageView)findViewById(R.id.background);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);


    }

    public void onPause(){

        super.onPause();
        //Using SharedPreferences to total exp;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("total_exp",String.valueOf(totalExp));
        editor.commit();


    }
    @Override
    public void onResume(){
        //get total exp from SharedPreferences;
        super.onResume();
        totalExp = (Integer.parseInt(prefs.getString("total_exp","0")));
        displayImage(totalExp);
        changeBackground();

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backhome){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.displayexp){
            displayExp();
        }
        else if(v.getId() == R.id.reset){
            resetTree();
        }

    }

    private void displayImage(int exp) {
        //calculate level
        //display tree image
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

    private void displayExp() {
        Toast.makeText(this, "Your tree got "+String.valueOf(totalExp)+"EXP!",Toast.LENGTH_LONG).show();

    }
    private void resetTree() {
        //set total to 0 then display image
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        totalExp = 0;
        editor.putString("total_exp",String.valueOf(totalExp));
        editor.commit();
        displayImage(totalExp);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeBackground(){
        //set back ground
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

}
