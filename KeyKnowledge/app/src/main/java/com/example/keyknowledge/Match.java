package com.example.keyknowledge;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

import java.util.ArrayList;

public class Match extends Activity {

    private LinearLayout grid;
    private TextView text,numero,categoria,livello;
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
        player=i.getIntExtra("player",0);
        control=new MatchControl(quiz,this);
        control.setQuitListener(quiz,player);
        control.getQuestion(currentQuestion,false);
        //control.getQuestion();
        currentQuestion++;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match);
        grid=findViewById(R.id.answers);
        text=findViewById(R.id.domanda);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);
        numero=findViewById(R.id.numDomanda);
        categoria=findViewById(R.id.catDomanda);
        livello=findViewById(R.id.livDomanda);
        list=grid.getTouchables();
        for(View v:list){
            Button b=(Button)v;
            int id=Integer.parseInt(b.getTag().toString());
            b.setOnClickListener(new View.OnClickListener(){

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    risposta_corrente=id;
                    for(View c:list){
                        Button b2=(Button)c;
                        if(risposta_corrente==Integer.parseInt(b2.getTag().toString())){
                            b2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                        }else{
                            b2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

                        }
                    }
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setQuestion(Question q){
        question=q;
        b1.setText(question.getRisposta1());
        b2.setText(question.getRisposta2());
        b3.setText(question.getRisposta3());
        b4.setText(question.getRisposta4());
        text.setText(question.getTesto());
        categoria.setText(question.getCategoria());
        livello.setText("LIVELLO "+question.getLivello());
        numero.setText("DOMANDA n."+currentQuestion);
        risposta_corrente=0;
        for(View c:list){
            Button b2=(Button)c;
            b2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        }
    }

    public void message(String x){
        Toast.makeText(this,x,Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void next(View view) {
        if (quiz.getPunteggioG1() < 0) {
                        quiz.setPunteggioG1(0);
                    }
        if (quiz.getPunteggioG2() < 0) {
                        quiz.setPunteggioG2(0);
                    }
        if (risposta_corrente == 0) {
            message("SELEZIONARE UNA RISPOSTA PRIMA DI ANDARE AVANTI");
        } else {

            if (risposta_corrente == question.getRisposta_esatta()) {
                if (player == 1) {
                    quiz.setPunteggioG1(quiz.getPunteggioG1() + 1);
                } else if (player == 2) {
                    quiz.setPunteggioG2(quiz.getPunteggioG2() + 1);
                }
                int color=getResources().getColor(R.color.green);
                switch (risposta_corrente) {
                    case 1:
                        b1.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 2:
                        b2.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 3:
                        b3.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 4:
                        b4.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                }
                control.getQuestion(currentQuestion,true);
                //fare animazione di risposta giusta
            } else {
                int color = getResources().getColor(R.color.red);
                switch (risposta_corrente) {
                    case 1:
                        b1.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 2:
                        b2.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 3:
                        b3.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                    case 4:
                        b4.setBackgroundTintList(ColorStateList.valueOf(color));
                        break;
                }
                control.getQuestion(currentQuestion,false);
                //fare animazione di risposta sbagliata
            }
            if (currentQuestion == quiz.getNumQuesiti()) {
                control.endMatch(quiz, player);
            }else {
                currentQuestion++;
                //control.getQuestion();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LIFECYCLE","onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LIFECYCLE","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LIFECYCLE","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LIFECYCLE","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LIFECYCLE","onDestroy()");
        if (currentQuestion != quiz.getNumQuesiti()) {
            control.quit(quiz);
        }


    }

}
