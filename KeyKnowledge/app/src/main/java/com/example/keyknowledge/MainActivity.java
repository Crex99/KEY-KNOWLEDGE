package com.example.keyknowledge;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

public class MainActivity extends AppCompatActivity {

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
<<<<<<< HEAD
        Log.d("INFO", "BOHHHHHH");
=======
        Log.d("TAG","CIAO");
>>>>>>> 83596cd0ad384544c13a07ecd097fcd31e5a62f0
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
}