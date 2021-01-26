package com.example.keyknowledge.model;


import androidx.annotation.NonNull;

import com.example.keyknowledge.control.UserControl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserManager {

    private static final String TABLE="users",OFFLINE="offline",ONLINE="online";

    private DatabaseReference mDatabase;


    public UserManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void addUser(User a){
        mDatabase.child(TABLE).child(a.getNickname()).setValue(a);
    }

    public void setUserOnline(String nick, String pass,UserControl control){
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange( DataSnapshot snapshot) {

                String current=snapshot.child(nick).child("password").getValue(String.class);

                if(current.equals(pass)){
                    mDatabase.child(TABLE).child(nick).child("state").setValue(ONLINE);
                    control.setMessage("utente aggiornato");
                }else{
                    control.setMessage("utente non aggiornato");
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
