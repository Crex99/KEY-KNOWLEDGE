package com.example.keyknowledge.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.keyknowledge.control.EndMatchControl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class EndMatchManager {

    private EndMatchControl control;
    private DatabaseReference mDatabase;
    public EndMatchManager(EndMatchControl endMatchControl) {
        this.control=endMatchControl;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void updateMatch(Quiz quiz,int player) {
        mDatabase.child("matches").child(quiz.getMode()).child(""+quiz.getId()+"").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if(currentData!=null){
                    Quiz current=currentData.getValue(Quiz.class);
                    if(player==1){
                        currentData.child("punteggioG1").setValue(quiz.getPunteggioG1());
                    }else if(player==2){
                        currentData.child("punteggioG2").setValue(quiz.getPunteggioG2());
                    }
                     if(current.getStatus().equals("full")){
                         currentData.child("status").setValue("finishing");
                         control.waitOpponent();
                         mDatabase.child("mathes").child(quiz.getMode()).child(""+quiz.getId()+"").addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 Quiz q=snapshot.getValue(Quiz.class);
                                 if(q.getStatus().equals("finished")){
                                     control.finish(q);
                                     mDatabase.child("mathes").child(quiz.getMode()).child(""+quiz.getId()+"").removeEventListener(this);
                                     mDatabase.child("mathes").child(quiz.getMode()).child(""+quiz.getId()+"").removeValue();
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {

                             }
                         });
                     }else if(current.getStatus().equals("finishing")){
                         mDatabase.child("mathes").child(quiz.getMode()).child(""+quiz.getId()+"").addListenerForSingleValueEvent(new ValueEventListener(){

                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 Quiz q=snapshot.getValue(Quiz.class);
                                 control.finish(q);
                                 mDatabase.child("matches").child(quiz.getMode()).child(""+quiz.getId()+"").child("status").setValue("finished");
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {

                             }
                         });

                    }
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }
}
