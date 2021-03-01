package com.example.keyknowledge.model;


import com.example.keyknowledge.control.*;


public class MainManager {


    private UserManager manager;

    public MainManager(){
        manager=new UserManager();
    }

    public void accessUser(String nick, MainControl control) {
        manager.access(nick,control);
    }

    public void logout(User user) {
        manager.logout(user);
    }
}
