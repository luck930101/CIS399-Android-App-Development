package edu.uoregon.yubo.piggamev3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    private PigGame game;

    public void setGame(PigGame game) {
        this.game = game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
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
                Toast.makeText(this, "About",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void sendGame(PigGame g){
        //send game object to the second fragment
        game = g;
        SecondFragment secondFragment = (SecondFragment)getFragmentManager().findFragmentById(R.id.second_fragment);
        secondFragment.setGame(game);
        //        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("player1_name",game.getPlayer1_name());
//        editor.putString("player2_name",game.getPlayer2_name());
//        editor.putInt("player1_score",game.getPlayer1_score());
//        editor.putInt("player2_score",game.getPlayer2_score());
//        editor.putInt("turn_point",game.getTurn_point());
//        editor.commit();
    }
}
