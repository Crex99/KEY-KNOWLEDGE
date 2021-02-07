package com.example.keyknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;




public class MainActivity extends Activity implements GestureDetector.OnGestureListener {
    SharedPreferences pref;
    public static final String RESTART_MODE="RESTART_MODE",MISC_MODE="MISC_MODE",CLASSIC_MODE="CLASSIC_MODE";
    User user;
    MainControl control=new MainControl(this);
    TextView textView;
    ImageView logo;
    GestureDetector detector;
    Animation zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("main");
        super.onCreate(savedInstanceState);
        pref=getSharedPreferences("profile",MODE_PRIVATE);
        control.controlAccess(pref.getString("id",null));
    }

    @Override
    protected void onStart() {
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
        if(x==R.layout.home){
            System.out.println("home");
            detector=new GestureDetector(this,this);
        }
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
        //detector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX()>e2.getX()) {
            //swipe verso sinistra
        }else {
            //swipe verso destra
            control.goKnowledge(user);
        }
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