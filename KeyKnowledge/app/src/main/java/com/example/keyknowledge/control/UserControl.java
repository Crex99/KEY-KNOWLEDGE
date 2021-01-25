package com.example.keyknowledge.control;


import com.example.keyknowledge.model.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserControl {

    private DatabaseReference mDatabase;

    public UserControl(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addUser(User a){
        mDatabase.child("utenti").setValue(a);
    }
}
