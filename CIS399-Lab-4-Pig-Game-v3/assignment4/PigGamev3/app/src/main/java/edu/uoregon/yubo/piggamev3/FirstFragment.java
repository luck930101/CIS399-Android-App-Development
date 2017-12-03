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
import android.widget.EditText;
import android.widget.ImageView;



/**
 * Created by zhangyu on 7/4/16.
 */
public class FirstFragment extends Fragment implements OnClickListener {

    private FirstActivity activity;
    private PigGame game;
    private boolean twoPaneLayout;
    private EditText player1EditText;
    private EditText player2EditText;
    private ImageView bgImage;
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View view = inflater.inflate(R.layout.first_fragment,container,false);
        //get button
        Button ng = (Button) view.findViewById(R.id.newgame);
        ng.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);

        activity = (FirstActivity) getActivity();
        player1EditText = (EditText) activity.findViewById(R.id.editText);
        player2EditText = (EditText) activity.findViewById(R.id.editText2);
        bgImage = (ImageView)activity.findViewById(R.id.background);
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);

        if (saveInstanceState != null){
            game = new PigGame();
            game.setPlayer1_name(player1EditText.getText().toString());
            game.setPlayer2_name(player2EditText.getText().toString());
        }
        else {
            game = new PigGame();
        }

        activity.setGame(game);

        twoPaneLayout = activity.findViewById(R.id.second_fragment)!= null;//if two fragment in one activity?



    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onResume(){

        super.onResume();

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


    }

    @Override

    public void onClick(View v){
        if(v.getId() == R.id.newgame)
            if(twoPaneLayout){
                //two fragments in the same activity
                String playername1 =player1EditText.getText().toString();
                String playername2 =player2EditText.getText().toString();
                game.setPlayer1_name(playername1);
                game.setPlayer2_name(playername2);

                activity.sendGame(game);
                System.out.println("do some thing");
            }
        else
            {
                //two fragments in two activities
                String playername1 =player1EditText.getText().toString();
                String playername2 =player2EditText.getText().toString();
                game.setPlayer1_name(playername1);
                game.setPlayer2_name(playername2);

                Intent intent = new Intent(getActivity(),SecondActivity.class);
                intent.putExtra("name1",playername1);
                intent.putExtra("name2",playername2);
                startActivity(intent);
                SharedPreferences.Editor editor = prefs.edit();
                //when create a new game, reset preference
                editor.putString("player1_name",game.getPlayer1_name());
                editor.putString("player2_name",game.getPlayer2_name());
                editor.putInt("player1_score",game.getPlayer1_score());
                editor.putInt("player2_score",game.getPlayer2_score());
                editor.putInt("turn_point",game.getTurn_point());
                editor.commit();
            }
    }

}
