package com.example.keyknowledge.model;

import androidx.annotation.NonNull;

import com.example.keyknowledge.control.MatchControl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.keyknowledge.model.Quiz.CLASSIC_MODE;
import static com.example.keyknowledge.model.Quiz.MISC_MODE;
import static com.example.keyknowledge.model.Quiz.RESTART_MODE;

public class MatchManager {

    private static final String TABLE="questions";
    private static final int CATEGORIES=5, LEVELS=4, QUESTIONS=4;
    private String[] categories={"arte","cultura generale","geografia","scienze","storia"};
    private String[] levels={"livello1","livello2","livello3","livello4"};
    private Quiz quiz;
    private DatabaseReference mDatabase;
    private MatchControl control;

    public MatchManager(Quiz q,MatchControl c){
        control=c;
        quiz=q;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void getQuestion(){
        switch(quiz.getMode()){
            case RESTART_MODE:
                Random r=new Random();
                int categoria=r.nextInt(CATEGORIES-1);
                int level=r.nextInt(LEVELS-1);
                int domanda=r.nextInt(QUESTIONS-1);
                mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String question=categories[categoria]+domanda;
                        Question q=snapshot.child(categories[categoria]).child(levels[level]).child(question).getValue(Question.class);
                        control.setQuestion(q);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
            case MISC_MODE:
            case CLASSIC_MODE:
                //do nothing
                break;
        }

    }
}
