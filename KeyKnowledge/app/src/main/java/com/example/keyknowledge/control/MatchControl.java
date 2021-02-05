package com.example.keyknowledge.control;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.keyknowledge.EndMatch;
import com.example.keyknowledge.Match;
import com.example.keyknowledge.model.*;

public class MatchControl {

    private Quiz quiz;
    private MatchManager manager;
    private Match match;

    public MatchControl(Quiz q, Match m){
        match=m;
        quiz=q;
        manager=new MatchManager(quiz,this);
    }

    public void getQuestion(){
        manager.getQuestion();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setQuestion(Question question){
        match.setQuestion(question);
    }


    public void endMatch(Quiz quiz, int player) {
        Intent i=new Intent(match.getApplicationContext(), EndMatch.class);
        i.putExtra("quiz",quiz);
        i.putExtra("player",player);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        match.startActivity(i);
    }
}
