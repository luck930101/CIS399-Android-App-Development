package edu.uoregon.yubo.piggamev3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by zhangyu on 7/4/16.
 */
public class SecondActivity extends AppCompatActivity{

    private PigGame game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onNewIntent(Intent intent){
        setIntent(intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent =getIntent();

        String player1Name = intent.getExtras().getString("name1");
        String player2Name = intent.getExtras().getString("name2");

        if(game == null){
            game = new PigGame();
        }

        game.setPlayer1_name(player1Name);
        game.setPlayer2_name(player2Name);
        SecondFragment secondFragment = (SecondFragment)getFragmentManager().findFragmentById(R.id.second_fragment);
        secondFragment.setGame(game);
    }


}
