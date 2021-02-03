package com.example.keyknowledge.model;


import androidx.annotation.NonNull;

import com.example.keyknowledge.control.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PairingManager {

    private static final String TABLE="matches";

    private DatabaseReference mDatabase;
    private PairingControl control;
    private MatchControl control2;

    public PairingManager(PairingControl c){
        control2=new MatchControl();
        control=c;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void createMatch(User user, String mode) {
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
                            control.startMatch(mode, user, opponent);
                            mDatabase.child(TABLE).child(mode).child("0").removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    /*
                    if (mode.equals("RESTART_MODE")) {
                        String us=user.getNickname();
                        Quiz quiz = new Quiz();
                        quiz.setId(1);
                        quiz.setUser1(us);
                        quiz.setMode(mode);
                        quiz.setNumQuesiti(30);
                        mDatabase.child(TABLE).child(mode).push().setValue(quiz);
                    }
                     */
                }else if(status.equals("wait")){
                    mDatabase.child(TABLE).child(mode).child("0").child("status").setValue("full");
                    mDatabase.child(TABLE).child(mode).child("0").child("user2").setValue(user.getNickname());
                    String opponent=snapshot.child("0").child("user2").getValue(String.class);
                    control.startMatch(mode, user, opponent);
                    mDatabase.child(TABLE).child(mode).child("0").removeEventListener(this);

                    /*
                    DataSnapshot ref=snapshot.child(mode);
                    for(DataSnapshot snap:ref.getChildren()){
                        String key=snap.getKey();
                    }
               */
                }else if(status.equals("full")){

                    for(DataSnapshot snap:snapshot.getChildren()){
                        Quiz curr=snap.getValue(Quiz.class);
                        String current_status=curr.getStatus();
                        String key=snap.getKey();
                        switch(current_status){
                            case "void":

                                break;
                            case "wait":

                                break;
                            case "full":

                                break;
                        }
                    }

                }
            }
                    /*
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

                     */


            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
        }

    public void resetMatch() {
        mDatabase.child(TABLE).child("RESTART_MODE").child("status").setValue("void");
        mDatabase.child(TABLE).child("RESTART_MODE").child("arrived").setValue("void");
        mDatabase.child(TABLE).child("RESTART_MODE").child("waiter").setValue("void");
    }
}
