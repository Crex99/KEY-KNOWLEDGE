package com.example.keyknowledge.model;

import com.example.keyknowledge.control.*;

public class LoginManager {

    private UserManager manager;

    public LoginManager(){
            manager=new UserManager();
    }

    public void accessUser(String nick, String pass, LoginControl control) {
        manager.login(nick,pass,control);
    }
}
