package com.example.keyknowledge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

import java.util.ArrayList;

public class Match extends Activity {

    private GridLayout grid;
    private TextView text;
    private Button b1,b2,b3,b4,confirm;
    private int currentQuestion=0,risposta_corrente=0,player;
    private Quiz quiz;
    private Question question;
    private MatchControl control;
    private ArrayList<View> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        quiz=(Quiz)i.getSerializableExtra("quiz");
        player=i.getIntExtra("player",-1);
        control=new MatchControl(quiz,this);
        control.getQuestion();
        currentQuestion++;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match);
        grid=findViewById(R.id.answers);
        text=findViewById(R.id.domanda);
        b1=findViewById(R.id.b1);
        b1.setText(question.getRisposta1());
        b2=findViewById(R.id.b2);
        b2.setText(question.getRisposta2());
        b3=findViewById(R.id.b3);
        b3.setText(question.getRisposta3());
        b4=findViewById(R.id.b4);
        b4.setText(question.getRisposta4());
        list=grid.getTouchables();
        for(View v:list){
            Button b=(Button)v;
            int id=Integer.parseInt(b.getTag().toString());
            b.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    risposta_corrente=id;
                    for(View c:list){
                        Button b2=(Button)c;
                        b2.setBackgroundColor(Color.WHITE);
                    }
                    b.setBackgroundColor(Color.YELLOW);
                }
            });
        }
    }

    public void setQuestion(Question q){
        question=q;
    }

    public void next(View view){

        if(risposta_corrente==question.getRisposta_corretta()){
            if(player==1){
                if(quiz.getPunteggioG1()<0){
                    quiz.setPunteggioG1(0);
                }
                quiz.setPunteggioG1(quiz.getPunteggioG1()+1);
            }else if(player==2){
                if(quiz.getPunteggioG2()<0){
                    quiz.setPunteggioG2(0);
                }
                quiz.setPunteggioG2(quiz.getPunteggioG2()+1);
            }
            int color=Color.GREEN;
            switch(risposta_corrente){
                case 1:
                    b1.setBackgroundColor(color);
                    break;
                case 2:
                    b2.setBackgroundColor(color);
                    break;
                case 3:
                    b3.setBackgroundColor(color);
                    break;
                case 4:
                    b4.setBackgroundColor(color);
                    break;
            }
            //fare animazione di risposta giusta
        }else{
            int color=Color.RED;
            switch(risposta_corrente){
                case 1:
                    b1.setBackgroundColor(color);
                    break;
                case 2:
                    b2.setBackgroundColor(color);
                    break;
                case 3:
                    b3.setBackgroundColor(color);
                    break;
                case 4:
                    b4.setBackgroundColor(color);
                    break;
            }
            //fare animazione di risposta sbagliata
        }
        if(currentQuestion==quiz.getNumQuesiti()){
            control.endMatch(quiz,player);
        }
        control.getQuestion();
        currentQuestion++;

    }
}
