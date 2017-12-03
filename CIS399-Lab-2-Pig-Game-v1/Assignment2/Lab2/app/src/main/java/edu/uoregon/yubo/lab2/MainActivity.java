package edu.uoregon.yubo.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    PigGame game = new PigGame();
    ImageView rpsImage;

    EditText player1_Name;
    EditText player2_Name;
    TextView player1_Score_Display;
    TextView player2_Score_Display;
    TextView whose_Turn_Display;
    TextView turn_point_Display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1_Name = (EditText) findViewById(R.id.editText);
        player2_Name = (EditText) findViewById(R.id.editText2);
        player1_Score_Display = (TextView) findViewById(R.id.textView7);
        player2_Score_Display = (TextView) findViewById(R.id.textView8);
        whose_Turn_Display = (TextView) findViewById(R.id.textView5);
        turn_point_Display = (TextView) findViewById(R.id.textView10);
        rpsImage = (ImageView)findViewById(R.id.imageView);

        player1_Name.addTextChangedListener(new TextWatcher() {
            //get player1's name
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                game.player1_name = player1_Name.getText().toString();
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
                game.player2_name = player2_Name.getText().toString();
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



    public void rollDie(View source) {
        //roll dice once
        if(game.whoWon()!=Winner.unknow){
            displayWinner();
            return;
        }
        game.player1_name = player1_Name.getText().toString();
        game.player2_name = player2_Name.getText().toString();
        displayName ();
        game.rollonce();
        displayImage(game.number);
        if(game.number == 1){
            game.turn_point = 0;
            endTurn(source);
        }
        turn_point_Display.setText(String.valueOf(game.turn_point));


    }
    public void endTurn(View source) {
        //end and check for each turn
        if(game.whoWon()!=Winner.unknow){
            displayWinner();
            return;
        }

        if(game.current_player==game.player1){
            game.check();
            player1_Score_Display.setText(String.valueOf(game.player1_score));
            System.out.println("change to player2");
            game.current_player=game.player2;
            whose_Turn_Display.setText(game.player2_name);
        }
        else{
            game.check();
            player2_Score_Display.setText(String.valueOf(game.player2_score));
            System.out.println("change to player1");
            game.current_player=game.player1;
            whose_Turn_Display.setText(game.player1_name);
        }
        game.resetpoint();
        turn_point_Display.setText(String.valueOf(game.turn_point));

        displayWinner();

    }
    public void newGame(View source) {
        //start a new game
        game.resetgame();
        whose_Turn_Display.setText(game.player1_name);
        player1_Score_Display.setText(String.valueOf(game.player1_score));
        player2_Score_Display.setText(String.valueOf(game.player2_score));
        player1_Name.setText(game.player1_name);
        player2_Name.setText(game.player2_name);
        turn_point_Display.setText(String.valueOf(game.turn_point));



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
        if(game.current_player==game.player1){
            whose_Turn_Display.setText(game.player1_name);
        }
        else{
            whose_Turn_Display.setText(game.player2_name);
        }



    }

    public void displayWinner(){
        //display who won the game
        if(game.whoWon()!=Winner.unknow) {
            if (game.whoWon()==Winner.player1) {
                Toast.makeText(this, game.player1_name + " win", Toast.LENGTH_SHORT).show();
                return;
            }
            if (game.whoWon()==Winner.player2) {
                Toast.makeText(this, game.player2_name + " win", Toast.LENGTH_SHORT).show();
                return;
            }
            if (game.whoWon()==Winner.tie) {
                Toast.makeText(this, "tie", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

}
