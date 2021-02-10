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

import static com.example.keyknowledge.model.Quiz.RESTART_MODE;

public class EndMatchManager {

    private EndMatchControl control;
    private DatabaseReference mDatabase;
    private static final String TABLE="matches";
    public EndMatchManager(EndMatchControl endMatchControl) {
        this.control=endMatchControl;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void updateMatch(Quiz quiz,int player) {
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if(currentData.getValue()!=null){
                    Quiz current=currentData.getValue(Quiz.class);
                    System.out.println(current);
                    System.out.println(quiz.getPunteggioG1());
                    System.out.println(player);
                    System.out.println(quiz.getPunteggioG2());
                    if(player==1){
                        currentData.child("punteggioG1").setValue(quiz.getPunteggioG1());
                    }else if(player==2){
                        currentData.child("punteggioG2").setValue(quiz.getPunteggioG2());
                    }
                     if(current.getStatus().equals("full")){
                         currentData.child("status").setValue("finishing");
                         control.waitOpponent();
                         System.out.println(quiz.getMode());
                         System.out.println(quiz.getId());
                         mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 Quiz q=snapshot.getValue(Quiz.class);
                                     System.out.println("MAMMA MIA");
                                     if (q.getStatus().equals("finished")) {
                                         control.finish(q);
                                         mDatabase.child(TABLE).child(quiz.getMode()).child("" + quiz.getId() + "").removeEventListener(this);
                                         if(quiz.getId()==0){
                                             Quiz quiz=new Quiz();
                                             quiz.setId(0);
                                             quiz.setStatus("wait");
                                             quiz.setUser2("void");
                                             quiz.setUser1("void");
                                             quiz.setMode(RESTART_MODE);
                                             quiz.setNumQuesiti(10);
                                             mDatabase.child(TABLE).child(RESTART_MODE).child(""+quiz.getId()+"").setValue(quiz);
                                         }else {
                                             mDatabase.child(TABLE).child(quiz.getMode()).child("" + quiz.getId() + "").removeValue();
                                         }
                                     }

                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {

                             }
                         });
                     }else if(current.getStatus().equals("finishing")){
                         mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").addListenerForSingleValueEvent(new ValueEventListener(){

                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 Quiz q=snapshot.getValue(Quiz.class);
                                 control.finish(q);
                                 mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").child("status").setValue("finished");
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {

                             }
                         });

                    }else if(current.getStatus().equals("quit")){
                         mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").addListenerForSingleValueEvent(new ValueEventListener(){

                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 Quiz q=snapshot.getValue(Quiz.class);
                                 control.finish(q);
                                 Quiz current=new Quiz();
                                 if(q.getId()==0){
                                     current.setId(0);
                                     current.setStatus("wait");
                                     current.setUser2("void");
                                     current.setUser1("void");
                                     current.setMode(RESTART_MODE);
                                     current.setNumQuesiti(10);
                                     mDatabase.child(TABLE).child(RESTART_MODE).child(""+current.getId()+"").setValue(current);
                                 }else {
                                     mDatabase.child(TABLE).child(RESTART_MODE).child("" +q.getId()+ "").removeValue();
                                 }
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
