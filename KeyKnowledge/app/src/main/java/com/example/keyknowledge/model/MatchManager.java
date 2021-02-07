package com.example.keyknowledge.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchManager {

    private final String TABLE="matches";

    private DatabaseReference mDatabase;

    public MatchManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createMatch(int id, User user,String mode) {

    }
}
