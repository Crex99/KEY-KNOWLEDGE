package com.example.keyknowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

public class Pairing extends Activity {

    PairingControl control=new PairingControl(this);
    User user;
    String mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        user=(User)i.getSerializableExtra("user");
        mode=i.getStringExtra("mode");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_player);
        control.createMatch(user,mode);
    }

    public void message(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        //control.resetMatch();
        super.onBackPressed();
    }
}
