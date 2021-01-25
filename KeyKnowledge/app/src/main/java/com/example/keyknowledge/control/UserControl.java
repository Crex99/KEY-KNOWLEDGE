package com.example.keyknowledge.control;


import androidx.annotation.NonNull;

import com.example.keyknowledge.model.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserControl {

    private static final String ONLINE="online",OFFLINE="offline",TABLE="users";

    private DatabaseReference mDatabase;

    public UserControl(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addUser(User a){
        mDatabase.child(TABLE).child(a.getNickname()).setValue(a);
    }

    public void setOnline(User a){

        mDatabase.child(TABLE).child(a.getNickname()).child("state").setValue(ONLINE);

    }

    public void setOffline(User a){
        mDatabase.child(TABLE).child(a.getNickname()).child("state").setValue(OFFLINE);
    }

    public User searchUser(String nick,String pass){

        final User[] users = new User[1];

        DatabaseReference userReference=mDatabase.child(TABLE);
        System.out.println("boh");
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("boh");
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String id=ds.child("nickname").getValue(String.class);
                    System.out.println(id);
                    if(id.equals(nick)){
                        users[0] =ds.getValue(User.class);
                        return;
                    }
                }

                 System.out.println(users[0]);
                //Post post = dataSnapshot.getValue(Post.class);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        userReference.addListenerForSingleValueEvent(userListener);
        if(users[0].getPassword().equals(pass)){
            return users[0];
        }else{
            return null;
        }

        //mDatabase.child(TABLE).child(nick).child("password").getValue(String.class);
    }
}
