package com.example.keyknowledge.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.keyknowledge.control.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PairingManager {
    private static final int MAX_ROOMS=100;
    private static final String TABLE="matches";

    private DatabaseReference mDatabase;
    private PairingControl control;
    private MatchControl control2;

    public PairingManager(PairingControl c){
        control2=new MatchControl();
        control=c;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createMatch(User user,String mode) {
        mDatabase.child(TABLE).child(mode).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue()!=null) {
                    for (int i = 0; i < MAX_ROOMS; i++) {
                        String id = "" + i + "";
                        if (currentData.hasChild(id)) {
                            String status = currentData.child(id).child("status").getValue(String.class);
                            //System.out.println(status);
                            if (status.equals("void")) {
                                mDatabase.child(TABLE).child(mode).child(id).child("status").setValue("wait");
                                mDatabase.child(TABLE).child(mode).child(id).child("user1").setValue(user.getNickname());
                                mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        String opponent = snapshot.child(id).child("user2").getValue(String.class);
                                        control.startMatch(mode, user, opponent);
                                        mDatabase.child(TABLE).child(mode).child(id).removeEventListener(this);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });
                                break;
                            } else if (status.equals("wait")) {
                                mDatabase.child(TABLE).child(mode).child(id).child("status").setValue("full");
                                mDatabase.child(TABLE).child(mode).child(id).child("user2").setValue(user.getNickname());
                                String opponent = currentData.child(id).child("user2").getValue(String.class);
                                control.startMatch(mode, user, opponent);
                                //mDatabase.child(TABLE).child(mode).child(id).removeEventListener(this);
                                break;
                            }
                        }else{
                            String us=user.getNickname();
                            Quiz quiz=new Quiz();
                            quiz.setId(i);
                            quiz.setStatus("wait");
                            quiz.setUser2("void");
                            quiz.setUser1(us);
                            quiz.setMode(mode);
                            quiz.setNumQuesiti(30);
                            //mDatabase.child(TABLE).child(mode).child("2").setValue(quiz);
                            mDatabase.child(TABLE).child(mode).child(id).removeValue();
                            break;
                        }
                    }
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(DatabaseError error, boolean committed, DataSnapshot currentData) {

            }

        });
    }

/*
    public void create(User user, String mode) {


        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                 String status=snapshot.child(mode).child("0").child("status").getValue(String.class);
                if (status.equals("void")) {
                    mDatabase.child(TABLE).child(mode).child("0").child("status").setValue("wait");
                    mDatabase.child(TABLE).child(mode).child("0").child("user1").setValue(user.getNickname());
                    mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener(){

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String opponent=snapshot.child("0").child("user2").getValue(String.class);
                            if(!opponent.equals("void")){
                                control.startMatch(mode, user, opponent);
                                mDatabase.child(TABLE).child(mode).removeEventListener(this);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    if (mode.equals("RESTART_MODE")) {
                        String us=user.getNickname();
                        Quiz quiz = new Quiz();
                        quiz.setId(1);
                        quiz.setUser1(us);
                        quiz.setMode(mode);
                        quiz.setNumQuesiti(30);
                        mDatabase.child(TABLE).child(mode).push().setValue(quiz);
                    }

                }else if(status.equals("wait")){
                    mDatabase.child(TABLE).child(mode).child("0").child("status").setValue("full");
                    mDatabase.child(TABLE).child(mode).child("0").child("user2").setValue(user.getNickname());
                    String opponent=snapshot.child("0").child("user2").getValue(String.class);
                    control.startMatch(mode, user, opponent);
                    mDatabase.child(TABLE).child(mode).child("0").removeEventListener(this);


                    DataSnapshot ref=snapshot.child(mode);
                    for(DataSnapshot snap:ref.getChildren()){
                        String key=snap.getKey();
                    }

                }else if(status.equals("full")){
                    long n=snapshot.getChildrenCount();
                    long c=0;
                    System.out.println(n);
                    System.out.println(c);
                    for(DataSnapshot snap:snapshot.getChildren()){
                        c++;
                        System.out.println(c);
                        if(c>=n){
                            String us=user.getNickname();
                            Quiz quiz = new Quiz();
                            quiz.setId(1);
                            quiz.setUser1(us);
                            quiz.setMode(mode);
                            quiz.setNumQuesiti(30);
                            quiz.setStatus("wait");
                            mDatabase.child(TABLE).child(mode).push().setValue(quiz);
                        }
                        Quiz curr=snap.getValue(Quiz.class);
                        String current_status=curr.getStatus();
                        int id=curr.getId();
                        System.out.println(id);
                        switch(current_status){
                            case "void":
                                mDatabase.child(TABLE).child(mode).child(key).child("status").setValue("wait");
                                mDatabase.child(TABLE).child(mode).child(key).child("user1").setValue(user.getNickname());
                                mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener(){

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String opponent=snapshot.child(key).child("user2").getValue(String.class);
                                        control.startMatch(mode, user, opponent);
                                        mDatabase.child(TABLE).child(mode).child(key).removeEventListener(this);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                break;
                            case "wait":
                                mDatabase.child(TABLE).child(mode).child(key).child("status").setValue("full");
                                mDatabase.child(TABLE).child(mode).child(key).child("user2").setValue(user.getNickname());
                                String opponent=snapshot.child(key).child("user2").getValue(String.class);
                                control.startMatch(mode, user, opponent);
                                mDatabase.child(TABLE).child(mode).child(key).removeEventListener(this);
                                break;
                        }
                    }

                }
            }

                    control2.createMatch(1,user,mode);
                    mDatabase.child(TABLE).child(mode).child("waiter").setValue(user.getNickname());
                    mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener(){

                        @Override
                        public void onDataChange( DataSnapshot snapshot) {
                            String opponent=snapshot.child("arrived").getValue(String.class);
                            if(opponent!=null) {
                                if (!opponent.equals("void")) {
                                    control.startMatch(mode, user, opponent);
                                    mDatabase.child(TABLE).child(mode).removeEventListener(this);
                                    //mDatabase.removeEventListener(this);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }

                else{
                    mDatabase.child(TABLE).child(mode).child("arrived").setValue(user.getNickname());
                    mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener(){

                        @Override
                        public void onDataChange( DataSnapshot snapshot) {
                            String opponent=snapshot.child("waiter").getValue(String.class);
                            if(opponent!=null) {
                                if (!opponent.equals("void")) {
                                    control.startMatch(mode, user, opponent);
                                    mDatabase.child(TABLE).child(mode).removeEventListener(this);
                                    //mDatabase.child(TABLE).removeEventListener(this);
                                    //mDatabase.removeEventListener(this);
                                }
                            }
                        }


                        @Override
                        public void onCancelled( DatabaseError error) {

                        }
                    });
                }
            }




            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
        }
*/
    public void resetMatch() {
        mDatabase.child(TABLE).child("RESTART_MODE").child("status").setValue("void");
        mDatabase.child(TABLE).child("RESTART_MODE").child("arrived").setValue("void");
        mDatabase.child(TABLE).child("RESTART_MODE").child("waiter").setValue("void");
    }
}
