package com.example.keyknowledge.control;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.keyknowledge.Login;
import com.example.keyknowledge.MainActivity;
import com.example.keyknowledge.model.LoginManager;
import com.example.keyknowledge.model.User;

import static android.content.Context.MODE_PRIVATE;

public class LoginControl {
    Intent i;
    private Login login;
    private LoginManager manager;
    SharedPreferences pref=login.getSharedPreferences("profile",MODE_PRIVATE);
    public LoginControl(Login x){
        login=x;
        manager=new LoginManager();
    }

    public void setMessage(String x){
        login.message(x);
    }

    public void access(String nick,String pass){
        manager.accessUser(nick,pass,this);
    }

    public void saveUser(User user){
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("id",user.getNickname());
        editor.commit();
        goHome();
    }

    public void goHome() {
        i=new Intent(login.getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        login.startActivity(i);
    }

}
