package com.example.keyknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keyknowledge.control.UserControl;
import com.example.keyknowledge.model.User;


public class MainActivity extends AppCompatActivity {
    User user;
    UserControl control=new UserControl(this);
    Intent i;
    SharedPreferences prefs;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs=getSharedPreferences("profile",MODE_PRIVATE);
        System.out.println("main");
        control.controlAccess(prefs.getString("id",null));
        super.onCreate(savedInstanceState);
    }

    public void setContent(int x,User y) {
        setContentView(x);
        textView=findViewById(R.id.profile);
        user=y;
        if(user!=null) {
            System.out.println(user);
            if(textView!=null) {
                textView.setText(user.getNickname());
            }
        }
    }

    public void message(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

    public void login(View view) {
        i=new Intent(this,Login.class);
        startActivity(i);
    }

    public void register(View view) {

        Toast.makeText(this,"funzionalità ancora non disponibile", Toast.LENGTH_LONG).show();
    }

    public void logout(View view){
        Editor editor=prefs.edit();
        editor.remove("id");
        editor.commit();
        i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
/*
    public void goHome(User user){
        i=new Intent(this,HomeScreen.class);
        startActivity(i);
    }

 */
}