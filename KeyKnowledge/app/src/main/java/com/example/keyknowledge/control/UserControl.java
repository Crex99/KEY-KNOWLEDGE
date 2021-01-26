package com.example.keyknowledge.control;


import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.keyknowledge.MainActivity;
import com.example.keyknowledge.model.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserControl {

    private static final String TABLE="users",OFFLINE="offline",ONLINE="online";

    private DatabaseReference mDatabase;
    private MainActivity activity;

    private boolean result=false;

    public UserControl(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public UserControl(MainActivity a){
        activity=a;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addUser(User a){
        mDatabase.child(TABLE).child(a.getNickname()).setValue(a);
    }

    public void setUserOnline(String nick,String pass){
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange( DataSnapshot snapshot) {

                String current=snapshot.child(nick).child("password").getValue(String.class);

                if(current.equals(pass)){
                    mDatabase.child(TABLE).child(nick).child("state").setValue(ONLINE);
                    activity.message("utente aggiornato");
                }else{
                    activity.message("utente non aggiornato");
                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
