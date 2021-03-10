package com.example.keyknowledge.control;

import android.content.Intent;
import com.example.keyknowledge.*;
import com.example.keyknowledge.model.*;

import static com.example.keyknowledge.model.Quiz.RESTART_MODE;

public class PairingControl {
    PairingManager manager;
    Pairing pairing;

    public PairingControl(Pairing p){
        pairing=p;
        manager=new PairingManager(this);
    }


    public void createMatch(User user,String mode){
        manager.createMatch(user,mode);
    }

    public void startMatch(Quiz quiz,int player) {
        System.out.println("boh");
        Intent i=new Intent(pairing.getApplicationContext(), Match.class);
        i.putExtra("quiz",quiz);
        i.putExtra("player",player);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                    pairing.startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    public void message(String x){
        pairing.message(x);
    }


    public void resetMatch(Quiz quiz) {
        manager.resetMatch(quiz);
    }

    public void setQuiz(Quiz quiz){
        pairing.setQuiz(quiz);
    }
}
