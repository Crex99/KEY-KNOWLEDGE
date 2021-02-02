package com.example.keyknowledge.model;

import com.example.keyknowledge.R;
import com.example.keyknowledge.control.MainControl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainManager {

    private static final String TABLE="users",OFFLINE="offline",ONLINE="online";

    private DatabaseReference mDatabase;

    public MainManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void accessUser(String nick, MainControl control) {
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange( DataSnapshot snapshot) {

                User user=snapshot.child(nick).getValue(User.class);
                if(user==null){
                    control.setMessage("L'utente "+nick+" non esiste");
                }else{
                    control.setView(R.layout.home,user);
                    //control.setHome(user);
                }
            }


            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }

    public void logout(User user) {
        mDatabase.child(TABLE).child(user.getNickname()).child("stato").setValue(OFFLINE);
    }
}
