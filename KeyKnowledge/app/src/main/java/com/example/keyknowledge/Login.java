package com.example.keyknowledge;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.User;

public class Login extends Activity {
    Intent i;
    SharedPreferences pref;
    EditText us,pass;

    UserControl userControl=new UserControl(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref=getSharedPreferences("profile",MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        us=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
    }
    public void message(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

    public void access(View view) {
        userControl.access(us.getText().toString(),pass.getText().toString());
    }

    public void saveUser(User user) {
        System.out.println(user.getNickname());
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("id",user.getNickname());
        editor.commit();
        goHome(user);
    }

    public void goHome(User user){
        i=new Intent(this,HomeScreen.class);
        startActivity(i);
    }
}
