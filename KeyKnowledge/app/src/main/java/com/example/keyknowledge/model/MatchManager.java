package com.example.keyknowledge.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

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

    private static final String TABLE="matches";
    private static final int CATEGORIES=5, LEVELS=4, QUESTIONS=4;
    private String[] categories={"arte","cultura generale","geografia","scienze","storia"};
    private String[] questions={"arte","generale","geo","scienze","storia"};
    private String[] levels={"livello1","livello2","livello3","livello4"};
    private Quiz quiz;
    private DatabaseReference mDatabase;
    private MatchControl control;
    private IaModule module;
    private QuestionManager managerQuestion;
    public MatchManager(Quiz q,MatchControl c){
        control=c;
        quiz=q;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        module=new IaModule(quiz);
        managerQuestion=new QuestionManager(c);
    }

    public void getQuestion(){
        Random r=new Random();
        int categoria=r.nextInt(CATEGORIES-1);
        int level=r.nextInt(LEVELS-1);
        int max=(level+1)*4;
        int min=(level+1)*4-(QUESTIONS-1);
        int domanda=r.nextInt((max-min)+1)+min;
        String question=questions[categoria]+domanda;
        managerQuestion.getQuestion(categories[categoria],levels[level],question);
    }

    public void getQuestion(int current,Boolean resp){
        switch(quiz.getMode()){
            case RESTART_MODE:
                module.nextQuestion(current,resp);
                break;
            case MISC_MODE:
            case CLASSIC_MODE:
                //do nothing
                break;
        }

    }

    public void setQuitListener(Quiz quiz,int player) {
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                String status = snapshot.child("status").getValue(String.class);
                System.out.println(status+"quitted");
                if (status.equals("quit")) {
                    System.out.println(status+"quitted");
                    //mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").child("status").setValue("finished");
                    control.endMatch(quiz,player*-1);
                    mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").removeEventListener(this);
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }

        });
        }



    public void quit(Quiz quiz) {
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").child("status").setValue("quit");
    }
}
