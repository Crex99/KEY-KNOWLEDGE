package com.example.keyknowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.keyknowledge.model.*;

public class Knowledge extends Activity {
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
         user=(User)i.getSerializableExtra("user");
        Log.d("INFO", "USER: " + user);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knowledge);
    }
}
