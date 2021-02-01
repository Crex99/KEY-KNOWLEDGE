package com.example.keyknowledge.model;

import androidx.annotation.NonNull;

import com.example.keyknowledge.control.PairingControl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PairingManager {

    private static final String TABLE="matches";

    private DatabaseReference mDatabase;
    private PairingControl control;

    public PairingManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void createMatch(User user, String mode) {
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange( DataSnapshot snapshot) {
                String status=snapshot.child(mode).child("status").getValue(String.class);
                if(status.equals("void")){
                    mDatabase.child(TABLE).child(mode).child("status").setValue("wait");
                    mDatabase.child(TABLE).child(mode).child("waiter").setValue(user.getNickname());
                    mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener(){

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String opponent=snapshot.child("arrived").getValue(String.class);
                            if(!opponent.equals("void")){
                                control.startMatch(mode,user,opponent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(status.equals("wait")){
                    mDatabase.child(TABLE).child(mode).child("arrived").setValue(user.getNickname());
                    String opponent=snapshot.child(mode).child("waiter").getValue(String.class);
                    if(!opponent.equals("void")){
                        control.startMatch(mode,user,opponent);
                    }
                }
            }


            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }
}
