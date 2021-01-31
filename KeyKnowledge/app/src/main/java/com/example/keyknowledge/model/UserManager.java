package com.example.keyknowledge.model;



import android.content.SharedPreferences;
import com.example.keyknowledge.control.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class UserManager {

    private static final String TABLE="users",OFFLINE="offline",ONLINE="online";

    private DatabaseReference mDatabase;
    public UserManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
   /* public void addUser(User a){
        mDatabase.child(TABLE).child(a.getNickname()).setValue(a);
    }
*/

/*
    public void read(View view){

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }*/

    public void accessUser(String nick, String pass, UserControl control) {
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange( DataSnapshot snapshot) {

                User user=snapshot.child(nick).getValue(User.class);
                if(user==null){
                    control.setMessage("L'utente "+nick+" non esiste");
                }else{
                    if(user.getPassword().equals(pass)){
                        control.saveUser(user);
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

    public void accessUser(String nick, UserControl control) {
        mDatabase.child(TABLE).addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange( DataSnapshot snapshot) {

                User user=snapshot.child(nick).getValue(User.class);
                if(user==null){
                    control.setMessage("L'utente "+nick+" non esiste");
                }else{
                   control.goHome(user);
                }
            }


            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }
}
