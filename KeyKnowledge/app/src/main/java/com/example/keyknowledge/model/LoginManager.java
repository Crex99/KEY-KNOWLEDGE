package com.example.keyknowledge.model;

import com.example.keyknowledge.control.*;

import static com.example.keyknowledge.model.UserManager.OFFLINE;
import static com.example.keyknowledge.model.UserManager.ONLINE;

public class LoginManager {

    private UserManager manager;
    private LoginControl control;

    public LoginManager(LoginControl con){
            manager=new UserManager();
            control=con;
    }

    public void accessUser(String nick, String pass) {
        manager.getUser(nick,pass,this);
    }

    public void login(User user,String pass){
        if(user==null){
            control.setMessage("L'utente "+user.getNickname()+" non esiste");
        }else{
            if(user.getPassword().equals(pass)){
                if(user.getStato().equals(OFFLINE)){
                    manager.setState(ONLINE,user.getNickname());
                    control.saveUser(user);
                }else{
                    control.setMessage("utente connesso da un altro dispositivo");
                }

            }else{
                control.setMessage("password errata");
            }
        }
    }
}
