package com.example.keyknowledge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.keyknowledge.control.MainControl;
import com.example.keyknowledge.model.User;



public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final String RESTART_MODE="RESTART_MODE",MISC_MODE="MISC_MODE",CLASSIC_MODE="CLASSIC_MODE";
    User user;
    MainControl control=new MainControl(this);
    TextView textView;
    GestureDetector detector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("main");
        control.controlAccess();
        super.onCreate(savedInstanceState);
    }

    public void setContent(int x,User y) {
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

    public void register(View view) {

        Toast.makeText(this,"funzionalità ancora non disponibile", Toast.LENGTH_LONG).show();
    }

    public void logout(View view){
        control.logout(user);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        detector.onTouchEvent(event);
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