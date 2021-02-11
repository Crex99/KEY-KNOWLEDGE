package com.example.keyknowledge;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

import static com.example.keyknowledge.model.Quiz.CLASSIC_MODE;
import static com.example.keyknowledge.model.Quiz.MISC_MODE;
import static com.example.keyknowledge.model.Quiz.RESTART_MODE;



public class MainActivity extends Activity {

    SharedPreferences pref;
    User user;
    MainControl control=new MainControl(this);
    TextView textView;
    ImageView logo;
    private GestureDetector detector;
    int layout=0;
    Animation zoom;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref=getSharedPreferences("profile",MODE_PRIVATE);
        control.controlAccess(pref.getString("id",null));
        detector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velx, float vely) {
                        if (e1.getX()>e2.getX()) {
                            //swipe verso sinistra
                        }else {
                            //swipe verso destra
                            if(layout==R.layout.home) {
                                control.goKnowledge(user);
                            }
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(!InternetConnection.haveInternetConnection(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle("ATTENZIONE!");
            builder.setCancelable(false);
            builder.setIcon(R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_24);
            builder.setMessage("Nessuna connessione rilevata.\nRiconnettere il dispositivo");
            builder.setPositiveButton("RIPROVA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!InternetConnection.haveInternetConnection(getApplicationContext())){
                        builder.show();
                    }
                }
            });
            builder.setNegativeButton("CHIUDI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.show();
        }
    }


    public void setContent(int x, User y) {
        setContentView(x);
        layout=x;
        textView=findViewById(R.id.profile);
        user=y;
        if(user!=null) {
            if(textView!=null) {
                textView.setText(user.getNickname());
            }
        }
    }

    public void message(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

    public void login(View view) {
        control.goLogin();
    }


    public void logout(View view){

        Editor editor=pref.edit();
        editor.remove("id");
        editor.commit();
        control.logout(user);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        detector.onTouchEvent(event);
        return true;
    }
    public void startMatch1(View view){
        //CLASSIC MODE
        control.searchOpponent(CLASSIC_MODE,user);

    }
    public void startMatch2(View view){

        //RESTART MODE
        control.searchOpponent(RESTART_MODE,user);
    }
    public void startMatch3(View view){
        //MISC MODE
        control.searchOpponent(MISC_MODE,user);

    }
}