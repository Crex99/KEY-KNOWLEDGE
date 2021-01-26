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

    private UserManager userManager;
    private MainActivity activity;

    public UserControl(){
        userManager =new UserManager();
    }

    public UserControl(MainActivity a){
        activity=a;
        userManager =new UserManager();
    }

    public void addUser(User user){
        userManager.addUser(user);
    }

    public void setMessage(String x){
        activity.message(x);
    }



    public void setUserOnline(String nick,String pass){
        userManager.setUserOnline(nick,pass,this);
    }
}
