package com.example.keyknowledge.control;


import androidx.annotation.NonNull;

import com.example.keyknowledge.model.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserControl {

    private static final String TABLE="users",OFFLINE="offline",ONLINE="offline";

    private DatabaseReference mDatabase;

    public UserControl(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addUser(User a){
        mDatabase.child(TABLE).child(a.getNickname()).setValue(a);
    }

    public Boolean setUserOnline(String nick,String pass){
        final Boolean[] result = {false};
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    String current=ds.child(nick).child("password").getValue(String.class);
                if(current.equals(pass)){
                    mDatabase.child(nick).child("state").setValue(ONLINE);
                    result[0] =true;
                }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return result[0];
    }
}
