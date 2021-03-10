package com.example.keyknowledge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

import static com.example.keyknowledge.model.Quiz.RESTART_MODE;

public class Pairing extends Activity {

    PairingControl control=new PairingControl(this);
    User user;
    String mode;
    Quiz quiz;
    TextView startMatch, user1Nick, user2Nick, userNick;
    LinearLayout linearSearch;
    LottieAnimationView lottieConn;
    Animation fade_In, fade_Out;
    LottieAnimationView lottieCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        user=(User)i.getSerializableExtra("user");
        Log.d("INFO", "USER: " + user);
        mode=i.getStringExtra("mode");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_player);
        control.createMatch(user,mode);
        linearSearch = findViewById(R.id.linearForSearch);
        lottieConn = findViewById(R.id.lottieConnection);
        userNick = findViewById(R.id.userNick);
        userNick.setText(user.getNickname());
        user1Nick = findViewById(R.id.user1Nick);
        user2Nick = findViewById(R.id.user2Nick);
        startMatch = findViewById(R.id.startMatch);
        lottieCount = findViewById(R.id.lottieCount);
        fade_Out = new AlphaAnimation(0, 1);
        fade_Out.setStartOffset(500);
        fade_Out.setDuration(5000);
        fade_In = new AlphaAnimation(1, 0);
        fade_In.setInterpolator(new DecelerateInterpolator());
        fade_In.setStartOffset(1000);
        fade_In.setDuration(1000);
        checkSmallPhone();
    }

    private void checkSmallPhone(){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        System.out.println("misura altezza: " + height);
        if(height < 1000){
            userNick.setY(userNick.getY() - 50);
            user1Nick.setY(user1Nick.getY() + 80);
            user2Nick.setY(user2Nick.getY() + 80);
        }
        if(height < 700){
            userNick.setY(userNick.getY() - 90);
            user1Nick.setY(user1Nick.getY() + 100);
            user2Nick.setY(user2Nick.getY() + 100);
        }
    }


    public void message(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

    public void setQuiz(Quiz x){
        quiz=x;
        if(!quiz.getUser2().equals("void")){
            if(quiz.getUser2().equals(user.getNickname())){
                user1Nick.setText(quiz.getUser2());
                user2Nick.setText(quiz.getUser1());
            }else{
                user1Nick.setText(quiz.getUser1());
                user2Nick.setText(quiz.getUser2());
            }
            startMatch.setVisibility(View.VISIBLE);
            linearSearch.setVisibility(View.INVISIBLE);
            lottieConn.setVisibility(View.VISIBLE);
            lottieConn.playAnimation();
            user1Nick.startAnimation(fade_Out);
            user2Nick.startAnimation(fade_Out);
            user1Nick.setVisibility(View.VISIBLE);
            user2Nick.setVisibility(View.VISIBLE);
            lottieCount.setVisibility(View.VISIBLE);
            lottieCount.playAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        control.resetMatch(quiz);
        super.onBackPressed();
    }
}
