package edu.uoregon.yubo.lab2;

import java.util.Random;

public class PigGame {

    private Player player1 = Player.player1;
    private Player player2 = Player.player2;
    private Player current_player = player1;
    private int player1_score = 0;
    private int player2_score = 0;
    private int turn_point = 0;
    private int number;
    private String player1_name;
    private String player2_name;
    private Random rand = new Random();
    private int win_score = 100;
    private int sides_of_die = 6;


    public String getPlayer1_name() {
        return player1_name;
    }

    public String getPlayer2_name() {
        return player2_name;
    }

    public int getNumber() {
        return number;
    }

    public int getTurn_point() {
        return turn_point;
    }

    public int getPlayer1_score() {
        return player1_score;
    }

    public int getPlayer2_score() {
        return player2_score;
    }

    public Player getCurrent_player() {
        return current_player;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer1() {
        return player1;
    }



    public void setPlayer1_name(String player1_name) {
        this.player1_name = player1_name;
    }

    public void setPlayer2_name(String player2_name) {
        this.player2_name = player2_name;
    }

    public void setPlayer1_score(int player1_score) { this.player1_score = player1_score; }

    public void setPlayer2_score(int player2_score) {
        this.player2_score = player2_score;
    }

    public void setCurrent_player(Player current_player) {
        this.current_player = current_player;
    }

    public void setTurn_point(int turn_point) {
        this.turn_point = turn_point;
    }

    public void setWin_score(int win_score) { this.win_score = win_score; }

    public void setSides_of_die(int sides_of_die) { this.sides_of_die = sides_of_die; }

    public void rollonce(){
        //roll dice once
        number = rand.nextInt(sides_of_die)+1;
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
        if (player1_score <win_score &&player2_score >= win_score){
            win = Winner.player2;
        }

        if (player1_score >= win_score &&current_player == player1){
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
