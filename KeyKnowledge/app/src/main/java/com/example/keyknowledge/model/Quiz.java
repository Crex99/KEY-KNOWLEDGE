package com.example.keyknowledge.model;

public class Quiz {

    private int id;
    private String mode;
    private int numQuesiti;
    private User user1;
    private User user2;
    private int punteggioG1;
    private int punteggioG2;

    public Quiz(int a,String b,int c,User d,User e){
        id=a;
        mode=b;
        numQuesiti=c;
        user1=d;
        user2=e;
        punteggioG1=0;
        punteggioG2=0;
    }

    public int getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public int getNumQuesiti() {
        return numQuesiti;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public int getPunteggioG1() {
        return punteggioG1;
    }

    public int getPunteggioG2() {
        return punteggioG2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setNumQuesiti(int numQuesiti) {
        this.numQuesiti = numQuesiti;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void setPunteggioG1(int punteggioG1) {
        this.punteggioG1 = punteggioG1;
    }

    public void setPunteggioG2(int punteggioG2) {
        this.punteggioG2 = punteggioG2;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", mode='" + mode + '\'' +
                ", numQuesiti=" + numQuesiti +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", punteggioG1=" + punteggioG1 +
                ", punteggioG2=" + punteggioG2 +
                '}';
    }
}
