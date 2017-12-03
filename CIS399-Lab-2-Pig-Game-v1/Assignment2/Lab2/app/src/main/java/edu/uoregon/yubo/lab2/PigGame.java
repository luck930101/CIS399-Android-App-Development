package edu.uoregon.yubo.lab2;

import java.util.Random;

/**
 * Created by zhangyu on 6/26/16.
 */
public class PigGame {

    Player player1 = Player.player1;
    Player player2 = Player.player2;
    Player current_player = player1;
    int player1_score = 0;
    int player2_score = 0;
    int turn_point = 0;
    int number;
    String player1_name;
    String player2_name;
    private Random rand = new Random();

    public void rollonce(){
        //roll dice once
        number = rand.nextInt(6)+1;
        turn_point+=number;
    }

    public void resetpoint(){
        //reset points
        turn_point=0;
    }

    public void check(){
        //caculate total score at the end of the turn
        if(current_player==player1){
            player1_score+=turn_point;

        }
        else{
            player2_score+=turn_point;
        }
    }

    public void resetgame(){
        //reset data
        current_player = player1;
        player1_name = "";
        player2_name = "";
        player1_score = 0;
        player2_score = 0;
        turn_point = 0;
        number = 0;
    }

    public Winner whoWon()
    {
        //check who won the game
        Winner win;
        win =Winner.unknow;
        if (player1_score <100 &&player2_score >= 100){
            win = Winner.player2;
        }

        if (player1_score >=100 &&current_player == player1){
            if(player1_score>player2_score){
                win = Winner.player1;
            }
            else if (player1_score<player2_score){
                win =Winner.player2;
            }
            else if (player1_score==player2_score){
                win =Winner.tie;
            }
        }

        return win;
    }





}
