package com.example.keyknowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.keyknowledge.control.EndMatchControl;
import com.example.keyknowledge.model.Quiz;

public class EndMatch extends Activity {

    private Quiz quiz;
    private int player;
    private EndMatchControl control;
    private TextView text;
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        quiz=(Quiz)i.getSerializableExtra("quiz");
        System.out.println(quiz);
        player=i.getIntExtra("player",-1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_match);
        text=findViewById(R.id.text);
        returnButton=findViewById(R.id.Return);
        returnButton.setEnabled(false);
        control=new EndMatchControl(this);
        control.updateMatch(quiz,player);

    }

    public void waitOpponent() {
        if(player==1){
            setText("aspettando il giocatore "+quiz.getUser2());
            System.out.println(quiz.getUser2());
        }else{
            setText("aspettando il giocatore "+quiz.getUser1());
            System.out.println(quiz.getUser1());
        }

    }

    public void end(Quiz q) {
        returnButton.setEnabled(true);
        quiz=q;
        System.out.println(quiz.getPunteggioG1());
        System.out.println(quiz.getPunteggioG2());
        if(player==1){
            if(quiz.getPunteggioG1()>quiz.getPunteggioG2()){
                setText("VITTORIA!!!");
            }else if(quiz.getPunteggioG1()<quiz.getPunteggioG2()){
                setText("HAI PERSO!!!");
            }else{
                setText("PAREGGIO!!!");
            }
        }else if(player==2){
            if(quiz.getPunteggioG2()>quiz.getPunteggioG1()){
                setText("VITTORIA!!!");
            }else if(quiz.getPunteggioG2()<quiz.getPunteggioG1()){
                setText("HAI PERSO!!!");
            }else{
                setText("PAREGGIO!!!");
            }
        }else if(player==-2){
            setText("VITTORIA PER ABBANDONO!!!\n Il giocatore "+quiz.getUser1()+" ha abbandonato");
        }else if(player==-1){
            setText("VITTORIA PER ABBANDONO!!!\n Il giocatore "+quiz.getUser2()+" ha abbandonato");
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

    public void returnHome(View view){
        control.returnHome();
    }

}
