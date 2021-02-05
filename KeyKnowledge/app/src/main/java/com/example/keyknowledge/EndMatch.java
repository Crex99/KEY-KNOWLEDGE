package com.example.keyknowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.keyknowledge.control.EndMatchControl;
import com.example.keyknowledge.model.Quiz;

public class EndMatch extends Activity {

    private Quiz quiz;
    private int player;
    private EndMatchControl control;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        quiz=(Quiz)i.getSerializableExtra("quiz");
        player=i.getIntExtra("player",-1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_match);
        text=findViewById(R.id.text);
        control=new EndMatchControl(this);
        control.updateMatch(quiz,player);

    }

    public void waitOpponent() {
        if(player==1){
            setText("aspettando il giocatore "+quiz.getUser2());
        }else{
            setText("aspettando il giocatore "+quiz.getUser1());
        }

    }

    public void end(Quiz q) {
        if(player==1){
            if(quiz.getPunteggioG1()>quiz.getPunteggioG2()){
                setText("VITTORIA!!!");
            }else if(quiz.getPunteggioG1()<quiz.getPunteggioG2()){
                setText("HAI PERSO!!!");
            }else{
                setText("PAREGGIO!!!");
            }
        }else{
            if(quiz.getPunteggioG2()>quiz.getPunteggioG1()){
                setText("VITTORIA!!!");
            }else if(quiz.getPunteggioG2()<quiz.getPunteggioG1()){
                setText("HAI PERSO!!!");
            }else{
                setText("PAREGGIO!!!");
            }
        }

    }

    public void setText(String x){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(x);
            }
        });
    }

}