package edu.uoregon.yubo.piggamev3;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by zhangyu on 7/4/16.
 */
public class SecondFragment extends Fragment implements OnClickListener{
    private PigGame game;
    private TextView namelable1;
    private TextView namelable2;
    private ImageView rpsImage;
    private ImageView bgImage;

    private TextView player1_Score_Display;
    private TextView player2_Score_Display;
    private TextView whose_Turn_Display;
    private TextView turn_point_Display;
    private SharedPreferences prefs;
    private boolean large_font = false;
    private Activity activity;



    public void setGame(PigGame game) {
        //start a new game
        this.game = game;
        newGame();
        namelable1.setText(game.getPlayer1_name());
        namelable2.setText(game.getPlayer2_name());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.second_fragment,container,false);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        //get references
        activity = getActivity();
        Button ro = (Button)activity.findViewById(R.id.button);
        Button eg = (Button)activity.findViewById(R.id.button2);
        namelable1 = (TextView)activity.findViewById(R.id.namename1);
        namelable2 = (TextView)activity.findViewById(R.id.namename2);
        player1_Score_Display = (TextView) activity.findViewById(R.id.textView7);
        player2_Score_Display = (TextView) activity.findViewById(R.id.textView8);
        whose_Turn_Display = (TextView) activity.findViewById(R.id.textView5);
        turn_point_Display = (TextView) activity.findViewById(R.id.textView10);
        rpsImage = (ImageView)activity.findViewById(R.id.imageView);
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        bgImage = (ImageView)activity.findViewById(R.id.background);

        ro.setOnClickListener(this);
        eg.setOnClickListener(this);



    }

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
    public void onResume(){
        //get data from SharedPreferences;

        super.onResume();
        if (game ==null){
            game = new PigGame();
        }
//        game.setPlayer1_name(prefs.getString("player1_name","P1"));
//        game.setPlayer2_name(prefs.getString("player2_name","P1"));
        game.setPlayer1_score(prefs.getInt("player1_score",0));
        player1_Score_Display.setText(String.valueOf(game.getPlayer1_score()));
        game.setPlayer2_score(prefs.getInt("player2_score",0));
        player2_Score_Display.setText(String.valueOf(game.getPlayer2_score()));
        game.setTurn_point(prefs.getInt("turn_point",0));
        turn_point_Display.setText(String.valueOf(game.getTurn_point()));


        namelable1.setText(game.getPlayer1_name());
        namelable2.setText(game.getPlayer2_name());



        //set how many points to win
        game.setWin_score(Integer.parseInt(prefs.getString("win_score","100")));
        //set how many sides of a die
        game.setSides_of_die(Integer.parseInt(prefs.getString("sides","6")));
        if(prefs.getInt("current_player",1)==1)
        {
            game.setCurrent_player(Player.player1);
            whose_Turn_Display.setText(game.getPlayer1_name());
        }
        if(prefs.getInt("current_player",1)==2)
        {
            game.setCurrent_player(Player.player2);
            whose_Turn_Display.setText(game.getPlayer2_name());
        }

        //set background image
        String background;
        background = prefs.getString("background","ocean");

        if(background.equals("ocean")){
            bgImage.setBackground(activity.getDrawable(R.drawable.ocean));

        }
        else if(background.equals("flower")){
            bgImage.setBackground(activity.getDrawable(R.drawable.flower));
        }
        else if(background.equals("mountain")){
            bgImage.setBackground(activity.getDrawable(R.drawable.mountain));
        }
        else if(background.equals("sky")){
            bgImage.setBackground(activity.getDrawable(R.drawable.sky));
        }

        large_font = prefs.getBoolean("set_large_font",false);

        //set font size

        if(large_font){
            namelable1.setTextSize(25);
            namelable2.setTextSize(25);
            player1_Score_Display.setTextSize(30);
            player2_Score_Display.setTextSize(30);
            whose_Turn_Display.setTextSize(30);
            turn_point_Display.setTextSize(30);
        }
        else{
            namelable1.setTextSize(20);
            namelable2.setTextSize(20);
            player1_Score_Display.setTextSize(20);
            player2_Score_Display.setTextSize(20);
            whose_Turn_Display.setTextSize(20);
            turn_point_Display.setTextSize(20);
        }

    }

    public void newGame() {
//        start a new game
        game.resetgame();
        whose_Turn_Display.setText(game.getPlayer1_name());
        player1_Score_Display.setText(String.valueOf(game.getPlayer1_score()));
        player2_Score_Display.setText(String.valueOf(game.getPlayer2_score()));
        turn_point_Display.setText(String.valueOf(game.getTurn_point()));


    }



    public void onClick(View v){
        if(v.getId() == R.id.button)
        {
            rollDie();

        }
        else if (v.getId() == R.id.button2){
            endTurn();
            System.out.println("end turn");
        }
    }






    public void rollDie() {
        //roll dice once
        if(game.whoWon()!=Winner.unknow){
            displayWinner();
            return;
        }
        displayName ();
        game.rollonce();
        displayImage(game.getNumber());
        if(game.getNumber() == 1){
            game.setTurn_point(0);
            endTurn();
        }
        turn_point_Display.setText(String.valueOf(game.getTurn_point()));




    }
    public void endTurn() {
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
//        player1_Name.setText(game.getPlayer1_name());
//        player2_Name.setText(game.getPlayer2_name());
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
                Toast.makeText(activity, game.getPlayer1_name() + " win", Toast.LENGTH_SHORT).show();
                return;
            }
            if (game.whoWon()==Winner.player2) {
                Toast.makeText(activity, game.getPlayer2_name() + " win", Toast.LENGTH_SHORT).show();
                return;
            }
            if (game.whoWon()==Winner.tie) {
                Toast.makeText(activity, "tie", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
