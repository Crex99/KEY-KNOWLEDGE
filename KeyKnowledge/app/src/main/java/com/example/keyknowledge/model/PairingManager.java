package com.example.keyknowledge.model;





import com.example.keyknowledge.control.*;



public class PairingManager {
    private PairingControl control;
    private QuizManager managerQuiz;

    public PairingManager(PairingControl c){
        managerQuiz=new QuizManager();
        control=c;
    }

    public void createMatch(User user,String mode) {
        managerQuiz.createMatch(user,mode,control);
    }
    public void resetMatch(Quiz quiz) {
        managerQuiz.resetMatch(quiz);
    }
}
