package com.example.keyknowledge.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.keyknowledge.control.MatchControl;
import com.example.keyknowledge.control.QuestionControl;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class QuestionManager {

    private static String TABLE="questions";
    private DatabaseReference mDatabase;
    private QuestionControl control;
    private Question question;
    private FirebaseFirestore firebaseFirestore;

    public Question getQuestionInEvent(String categoria, String livello, String id){
        getQuestion(categoria, livello, id);
        System.out.println("Sto in getQuestionInEvent");
        System.out.println("In getQuestionInEvent:\n" + question);
        return question;
    }


    public QuestionManager(MatchControl c){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        control=new QuestionControl(c);
    }
    public QuestionManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        control=new QuestionControl();
    }

    public void getQuestion(String categoria, String livello, String id){
        System.out.println("Sto in getQuestion()");
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("Sto in onDataChange");
                Question q=snapshot.child(categoria).child(livello).child(id).getValue(Question.class);
                    System.out.println(q);
                    question = q;
                    control.setQuestion(q);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
