package com.example.keyknowledge;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

public class Login extends Activity {
    SharedPreferences pref;
    EditText us,pass;
    LoginControl control=new LoginControl(this);

    public Login()  {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        pref=getSharedPreferences("profile",MODE_PRIVATE);
        us=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
    }
    public void message(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

    public void access(View view) {
        control.access(us.getText().toString(),pass.getText().toString());
    }


    public void register(View view) {
        Toast.makeText(this,"funzionalità ancora non disponibile", Toast.LENGTH_LONG).show();
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("id",user.getNickname());
        editor.commit();
    }
}
