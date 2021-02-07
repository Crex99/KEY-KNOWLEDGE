package com.example.keyknowledge.model;

import com.example.keyknowledge.control.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginManager {

    private static final String TABLE="users",OFFLINE="offline",ONLINE="online";

    private DatabaseReference mDatabase;

    public LoginManager(){
            mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void accessUser(String nick, String pass, LoginControl control) {
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange( DataSnapshot snapshot) {

                User user=snapshot.child(nick).getValue(User.class);
                if(user==null){
                    control.setMessage("L'utente "+nick+" non esiste");
                }else{
                    if(user.getPassword().equals(pass)){
                        if(user.getStato().equals(OFFLINE)){
                            mDatabase.child(TABLE).child(nick).child("stato").setValue(ONLINE);
                            control.saveUser(user);
                        }else{
                            control.setMessage("utente connesso da un altro dispositivo");
                        }

                    }else{
                        control.setMessage("password errata");
                    }
                }
            }


            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }
}
