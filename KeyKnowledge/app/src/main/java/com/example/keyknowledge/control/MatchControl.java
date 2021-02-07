package com.example.keyknowledge.control;

import com.example.keyknowledge.model.*;

public class MatchControl {

    private MatchManager manager;

    public MatchControl(){
        manager=new MatchManager();
    }

    public void createMatch(int id,User user,String mode){
        manager.createMatch(id,user,mode);
    }
}
