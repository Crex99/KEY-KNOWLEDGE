package com.example.keyknowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

public class Match extends Activity {

    private TextView text;
    private int currentQuestion=0;
    private Quiz quiz;
    private Question question;
    private MatchControl control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        quiz=(Quiz)i.getSerializableExtra("quiz");
        control=new MatchControl(quiz,this);
        control.getQuestion();
        currentQuestion++;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match);
        text=findViewById(R.id.domanda);
    }

    public void setQuestion(Question q){
        question=q;
    }
}
