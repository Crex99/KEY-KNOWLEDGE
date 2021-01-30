package com.example.keyknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

public class MainActivity extends AppCompatActivity {
    Intent i=new Intent();

//ciao
    EditText us,pw;
    UserControl userControl=new UserControl(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        us=findViewById(R.id.user);
        pw=findViewById(R.id.pass);
    }

    public void write(View view) {
        User user =new User(us.getText().toString(),pw.getText().toString(),"email a caso","offline");
        userControl.addUser(user);

    }

    //pigliat sta modific

    public void ciao(){
        Log.d("INFO", "BOHHHHHH");
    }

    public void aggiorna(View view){
        userControl.setUserOnline(us.getText().toString(),pw.getText().toString());
    }

    public void message(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }


    public void read(View view){
        /*
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    */}

    public void login(View view) {

    }

    public void register(View view) {

        Toast.makeText(this,"funzionalità ancora non disponibile", Toast.LENGTH_LONG).show();
    }
}