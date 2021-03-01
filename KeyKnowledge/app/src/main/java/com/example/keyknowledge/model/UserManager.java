package com.example.keyknowledge.model;

import com.example.keyknowledge.R;
import com.example.keyknowledge.control.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserManager {

    private static String TABLE="users",OFFLINE="offline",ONLINE="online";
    private DatabaseReference mDatabase;
    private UserControl controller;

    public UserManager(){
        controller=new UserControl();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void login(String nick, String pass, LoginControl control){
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange( DataSnapshot snapshot) {

                User user=snapshot.child(nick).getValue(User.class);
                if(user==null){
                    controller.setMessage("L'utente "+nick+" non esiste",control);
                }else{
                    if(user.getPassword().equals(pass)){
                        if(user.getStato().equals(OFFLINE)){
                            mDatabase.child(TABLE).child(nick).child("stato").setValue(ONLINE);
                            controller.saveUser(user,control);
                        }else{
                            controller.setMessage("utente connesso da un altro dispositivo",control);
                        }

                    }else{
                        controller.setMessage("password errata",control);
                    }
                }
            }


            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }

    public void access(String nick,MainControl control){
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange( DataSnapshot snapshot) {

                User user=snapshot.child(nick).getValue(User.class);
                if(user==null){
                    controller.setMessage("L'utente "+nick+" non esiste",control);
                }else{
                    mDatabase.child(TABLE).child(user.getNickname()).child("stato").setValue(ONLINE);
                    controller.setView(R.layout.home,user,control);

                }
            }
            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }

    public void logout(User user){
        mDatabase.child(TABLE).child(user.getNickname()).child("stato").setValue(OFFLINE);
    }
}
