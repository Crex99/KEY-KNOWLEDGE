package com.example.keyknowledge.control;

import com.example.keyknowledge.Pairing;
import com.example.keyknowledge.model.PairingManager;
import com.example.keyknowledge.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class PairingControl {
    int x=0;
    PairingManager manager;
    Pairing pairing;

    public PairingControl(Pairing p){
        pairing=p;
        manager=new PairingManager(this);
    }


    public void createMatch(User user,String mode){
        manager.createMatch(user,mode);
    }

    public void startMatch(String mode, User user, String opponent) {
        x++;
        pairing.message("PARTITA DA INIZIARE "+x);
       // manager.resetMatch();
    }

    public void message(String x){
        pairing.message(x);
    }


    public void resetMatch() {
        manager.resetMatch();
    }
}
