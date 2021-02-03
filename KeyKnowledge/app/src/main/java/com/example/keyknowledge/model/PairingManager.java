package com.example.keyknowledge.model;


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

    public PairingManager(PairingControl c){
        control=c;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void createMatch(User user, String mode) {
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){



            @Override
            public void onDataChange( DataSnapshot snapshot) {
                int status =snapshot.child(mode).child("status").getValue(int.class);
                if(status==0){
                    mDatabase.child(TABLE).child(mode).child("status").setValue("wait");
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
                }else{
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

    public void resetMatch() {
        mDatabase.child(TABLE).child("RESTART_MODE").child("status").setValue("void");
        mDatabase.child(TABLE).child("RESTART_MODE").child("arrived").setValue("void");
        mDatabase.child(TABLE).child("RESTART_MODE").child("waiter").setValue("void");
    }
}
