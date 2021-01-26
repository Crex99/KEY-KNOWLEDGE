package com.example.keyknowledge.control;


import com.example.keyknowledge.MainActivity;
import com.example.keyknowledge.model.*;


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
