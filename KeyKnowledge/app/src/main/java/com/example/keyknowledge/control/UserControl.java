package com.example.keyknowledge.control;




import com.example.keyknowledge.*;
import com.example.keyknowledge.model.*;
public class UserControl {


    private UserManager userManager;
    private Login activity;
    public UserControl(){
        userManager =new UserManager();
    }

    public UserControl(Login a){
        activity=a;
        userManager =new UserManager();
    }

    public void setMessage(String x){
        activity.message(x);
    }

    public void access(String nick,String pass){
        userManager.accessUser(nick,pass,this);
    }

    public void saveUser(User user){
        activity.saveUser(user);
    }
    public void backHome(String nick){
        userManager.accessUser(nick,this);
    }

    public void goHome(User user) {
        activity.goHome(user);
    }
}
