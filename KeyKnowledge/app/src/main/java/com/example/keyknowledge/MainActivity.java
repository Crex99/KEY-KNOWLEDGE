package com.example.keyknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.keyknowledge.control.UserControl;


public class MainActivity extends AppCompatActivity {
    UserControl control=new UserControl();
    Intent i;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs=getSharedPreferences("profile",MODE_PRIVATE);
        controlAccess();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void controlAccess() {
        String nick=prefs.getString("id","null");
        System.out.println(nick);
        if(!nick.equals("null")){
            control.backHome(nick);
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
}