package com.example.keyknowledge.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.keyknowledge.control.MatchControl;
import com.example.keyknowledge.control.QuestionControl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionManager {

    private static String TABLE="questions";
    private DatabaseReference mDatabase;
    private QuestionControl control;

    public QuestionManager(MatchControl c){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        control=new QuestionControl(c);
    }
    public QuestionManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        control=new QuestionControl();
    }

    public void getQuestion(String categoria, String livello, String id){
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Question q=snapshot.child(categoria).child(livello).child(id).getValue(Question.class);
                    control.setQuestion(q);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
