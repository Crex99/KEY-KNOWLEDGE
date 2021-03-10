package com.example.keyknowledge.control;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.keyknowledge.Match;
import com.example.keyknowledge.model.Question;
import com.example.keyknowledge.model.Quiz;

public class QuestionControl {

    public QuestionControl(MatchControl c){
        control=c;
    }

    public QuestionControl(){
        //control = new MatchControl(new Quiz(), new Match());
    }

    private MatchControl control;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setQuestion(Question question) {
        control.setQuestion(question);
    }

}
