package edu.uoregon.yubo.lab2;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private PigGame game = new PigGame();
    private ImageView rpsImage;
    private ImageView bgImage;

    private EditText player1_Name;
    private EditText player2_Name;
    private TextView player1_Score_Display;
    private TextView player2_Score_Display;
    private TextView name_Label1;
    private TextView name_Label2;
    private TextView whose_Turn_Display;
    private TextView turn_point_Display;
    private SharedPreferences prefs;
    private boolean large_font = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1_Name = (EditText) findViewById(R.id.editText);
        player2_Name = (EditText) findViewById(R.id.editText2);
        name_Label1 = (TextView) findViewById(R.id.textView2);
        name_Label2 = (TextView) findViewById(R.id.textView);
        player1_Score_Display = (TextView) findViewById(R.id.textView7);
        player2_Score_Display = (TextView) findViewById(R.id.textView8);
        whose_Turn_Display = (TextView) findViewById(R.id.textView5);
        turn_point_Display = (TextView) findViewById(R.id.textView10);
        rpsImage = (ImageView)findViewById(R.id.imageView);
        bgImage = (ImageView)findViewById(R.id.background);

        PreferenceManager.setDefaultValues(this, R.xml.preferences,false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        player1_Name.addTextChangedListener(new TextWatcher() {
            //get player1's name
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                game.setPlayer1_name(player1_Name.getText().toString());
                displayName ();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }


        });


        player2_Name.addTextChangedListener(new TextWatcher() {
            //get player2's name
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                game.setPlayer2_name(player2_Name.getText().toString());
                displayName ();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    @Override

    public void onPause(){

        super.onPause();
        //Using SharedPreferences to store data;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("player1_name",game.getPlayer1_name());
        editor.putString("player2_name",game.getPlayer2_name());
        editor.putInt("player1_score",game.getPlayer1_score());
        editor.putInt("player2_score",game.getPlayer2_score());
        editor.putInt("turn_point",game.getTurn_point());
        if(game.getCurrent_player()==Player.player1)
        {
            editor.putInt("current_player",1);
        }
        else
        {
            editor.putInt("current_player",2);
        }
        editor.commit();



    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override

    public void onResume(){

        super.onResume();
        //Get data from SharedPreferences ;
        game.setPlayer1_name(prefs.getString("player1_name",""));
        game.setPlayer2_name(prefs.getString("player2_name",""));
        game.setPlayer1_score(prefs.getInt("player1_score",0));
        player1_Score_Display.setText(String.valueOf(game.getPlayer1_score()));
        game.setPlayer2_score(prefs.getInt("player2_score",0));
        player2_Score_Display.setText(String.valueOf(game.getPlayer2_score()));
        game.setTurn_point(prefs.getInt("turn_point",0));
        turn_point_Display.setText(String.valueOf(game.getTurn_point()));
        if(prefs.getInt("current_player",1)==1)
        {
            game.setCurrent_player(Player.player1);
            player1_Name.setText(game.getPlayer1_name());
            whose_Turn_Display.setText(game.getPlayer1_name());
        }
        if(prefs.getInt("current_player",1)==2)
        {
            game.setCurrent_player(Player.player2);
            player2_Name.setText(game.getPlayer2_name());
            whose_Turn_Display.setText(game.getPlayer2_name());
        }
        //set how many points to win
        game.setWin_score(Integer.parseInt(prefs.getString("win_score","100")));
        //set how many sides of a die
        game.setSides_of_die(Integer.parseInt(prefs.getString("sides","6")));

        //set background image
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

        large_font = prefs.getBoolean("set_large_font",false);

        //set font size

        if(large_font){
            name_Label1.setTextSize(25);
            name_Label2.setTextSize(25);
            player1_Score_Display.setTextSize(30);
            player2_Score_Display.setTextSize(30);
            whose_Turn_Display.setTextSize(30);
            turn_point_Display.setTextSize(30);
        }
        else{
            name_Label1.setTextSize(20);
            name_Label2.setTextSize(20);
            player1_Score_Display.setTextSize(20);
            player2_Score_Display.setTextSize(20);
            whose_Turn_Display.setTextSize(20);
            turn_point_Display.setTextSize(20);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_pig_game,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:

                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                 Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void rollDie(View source) {
        //roll dice once
        if(game.whoWon()!=Winner.unknow){
            displayWinner();
            return;
        }
        game.setPlayer1_name(player1_Name.getText().toString());
        game.setPlayer2_name(player2_Name.getText().toString());
        displayName ();
        game.rollonce();
        displayImage(game.getNumber());
        if(game.getNumber() == 1){
            game.setTurn_point(0);
            endTurn(source);
        }
        turn_point_Display.setText(String.valueOf(game.getTurn_point()));




    }
    public void endTurn(View source) {
        //end and check for each turn
        if(game.whoWon()!=Winner.unknow){
            displayWinner();
            return;
        }

        if(game.getCurrent_player()==game.getPlayer1()){
            game.check();
            player1_Score_Display.setText(String.valueOf(game.getPlayer1_score()));
            System.out.println("change to player2");
            game.setCurrent_player(game.getPlayer2());
            whose_Turn_Display.setText(game.getPlayer2_name());
        }
        else{
            game.check();
            player2_Score_Display.setText(String.valueOf(game.getPlayer2_score()));
            System.out.println("change to player1");
            game.setCurrent_player(game.getPlayer1());
            whose_Turn_Display.setText(game.getPlayer1_name());
        }
        game.resetpoint();
        turn_point_Display.setText(String.valueOf(game.getTurn_point()));

        displayWinner();

    }
    public void newGame(View source) {
        //start a new game
        game.resetgame();
        whose_Turn_Display.setText(game.getPlayer1_name());
        player1_Score_Display.setText(String.valueOf(game.getPlayer1_score()));
        player2_Score_Display.setText(String.valueOf(game.getPlayer2_score()));
        player1_Name.setText(game.getPlayer1_name());
        player2_Name.setText(game.getPlayer2_name());
        turn_point_Display.setText(String.valueOf(game.getTurn_point()));



    }

    private void displayImage(int random_num) {
        //display dice image
        int id = 0;

        switch(random_num)
        {
            case 1:
                id = R.drawable.die1;
                break;
            case 2:
                id = R.drawable.die2;
                break;
            case 3:
                id = R.drawable.die3;
                break;
            case 4:
                id = R.drawable.die4;
                break;
            case 5:
                id = R.drawable.die5;
                break;
            case 6:
                id = R.drawable.die6;
                break;
        }
        rpsImage.setImageResource(id);
    }

    public void displayName () {
        //display who is throwing the dice
        if(game.getCurrent_player()==game.getPlayer1()){
            whose_Turn_Display.setText(game.getPlayer1_name());
        }
        else{
            whose_Turn_Display.setText(game.getPlayer2_name());
        }



    }

    public void displayWinner(){
        //display who won the game
        if(game.whoWon()!=Winner.unknow) {
            if (game.whoWon()==Winner.player1) {
                Toast.makeText(this, game.getPlayer1_name() + " win", Toast.LENGTH_SHORT).show();
                return;
            }
            if (game.whoWon()==Winner.player2) {
                Toast.makeText(this, game.getPlayer2_name() + " win", Toast.LENGTH_SHORT).show();
                return;
            }
            if (game.whoWon()==Winner.tie) {
                Toast.makeText(this, "tie", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
