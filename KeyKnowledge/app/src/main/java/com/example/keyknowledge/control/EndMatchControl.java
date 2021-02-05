package com.example.keyknowledge.control;

import com.example.keyknowledge.EndMatch;
import com.example.keyknowledge.model.EndMatchManager;
import com.example.keyknowledge.model.Quiz;

public class EndMatchControl {

    private EndMatch endMatch;
    private EndMatchManager manager;
    public EndMatchControl(EndMatch endMatch) {
        this.endMatch=endMatch;
        manager=new EndMatchManager(this);
    }

    public void updateMatch(Quiz q, int p) {
        manager.updateMatch(q,p);
    }

    public void waitOpponent() {
        endMatch.waitOpponent();
    }

    public void finish(Quiz q) {
        endMatch.end(q);
    }
}
