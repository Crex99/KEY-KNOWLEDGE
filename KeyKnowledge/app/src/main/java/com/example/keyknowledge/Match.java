package com.example.keyknowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.keyknowledge.model.User;

public class Match extends Activity {

    private User user,opponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        user=(User)i.getSerializableExtra("user");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
}
