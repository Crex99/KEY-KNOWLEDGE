package com.example.keyknowledge.model;

public class User {

    private String nickname;
    private String email;
    private String password;
    private String state;

    public User(){

    }

    public User(String a,String b,String c,String d){
        nickname=a;
        email=c;
        password=b;
        state=d;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

<<<<<<< HEAD
    public String getState(){
        return state;
    }
=======
    public String getState(){ return state;}
>>>>>>> workspace_crex

    public void setNickname(String x){
        nickname=x;
    }

    public void setEmail(String x){
        email=x;
    }

    public void setPassword(String x){
        password=x;
    }

<<<<<<< HEAD
    public void setState(String x){
        state=x;
    }
=======
    public void setState(String x){ state=x;}
>>>>>>> workspace_crex

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}