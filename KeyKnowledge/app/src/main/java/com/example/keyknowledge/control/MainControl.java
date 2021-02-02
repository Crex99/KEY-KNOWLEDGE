package com.example.keyknowledge.control;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.keyknowledge.Knowledge;
import com.example.keyknowledge.Login;
import com.example.keyknowledge.MainActivity;
import com.example.keyknowledge.Pairing;
import com.example.keyknowledge.R;
import com.example.keyknowledge.model.MainManager;
import com.example.keyknowledge.model.User;

import static android.content.Context.MODE_PRIVATE;
import static com.example.keyknowledge.MainActivity.CLASSIC_MODE;
import static com.example.keyknowledge.MainActivity.MISC_MODE;
import static com.example.keyknowledge.MainActivity.RESTART_MODE;

public class MainControl {
    private Intent i;
    private MainActivity main;
    private MainManager manager;
    SharedPreferences pref=main.getSharedPreferences("profile",MODE_PRIVATE);
    public MainControl(MainActivity a){
        main=a;
        manager=new MainManager();
    }

    public void setMessage(String x){
        main.message(x);
    }

    public void backHome(String nick){
        manager.accessUser(nick,this);
    }



    public void controlAccess() {
        String nick=pref.getString("id",null);
        System.out.println(nick);
        if(nick!=null){
            backHome(nick);
        }else{
            main.setContent(R.layout.activity_main,null);
        }
    }

    public void setView(int x, User user){
        main.setContent(x,user);
    }

    public void goKnowledge(User user) {
        i=new Intent(main.getApplicationContext(), Knowledge.class);
        i.putExtra("user",user);
        main.startActivity(i);
    }

    public void goLogin(){
        i=new Intent(main.getApplicationContext(), Login.class);
        main.startActivity(i);
    }

    public void logout(User user){
        manager.logout(user);
        SharedPreferences.Editor editor=pref.edit();
        editor.remove("id");
        editor.commit();
        i = new Intent(main.getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        main.startActivity(i);
    }


    public void searchOpponent(String mode,User user) {
        switch(mode){
            case RESTART_MODE:
                i=new Intent(main.getApplicationContext(), Pairing.class);
                i.putExtra("user",user);
                i.putExtra("mode",mode);
                main.startActivity(i);
                break;
            case MISC_MODE:
            case CLASSIC_MODE:
                main.message("funzione in fase di sviluppo");
                break;

        }
    }
}
