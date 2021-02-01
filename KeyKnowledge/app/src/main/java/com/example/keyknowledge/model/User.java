package com.example.keyknowledge.model;

public class User {

    private String nickname;
    private String email;
    private String password;
    private String stato;
    private String ruolo;
    private int numPartiteVinte;
    private int numPartiteGiocate;

    public User(){

    }

    public User(String a,String b,String c,String d){
        nickname=a;
        email=c;
        password=b;
        stato=d;
        ruolo="giocatore";
        numPartiteVinte=0;
        numPartiteGiocate=0;
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

    public String getStato(){
        return stato;
    }

    public String getRuolo(){
        return ruolo;
    }

    public int getNumPartiteGiocate(){
        return numPartiteGiocate;
    }

    public int getNumPartiteVinte(){
        return numPartiteVinte;
    }


    public void setNickname(String x){
        nickname=x;
    }

    public void setEmail(String x){
        email=x;
    }

    public void setPassword(String x){
        password=x;
    }


    public void setStato(String x){
        stato=x;
    }

    public void setRuolo(String x){
        ruolo=x;
    }

    public void setNumPartiteGiocate(int x){
        numPartiteGiocate=x;
    }

    public void setNumPartiteVinte(int x){
        numPartiteVinte=x;
    }


    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", stato='" + stato + '\'' +
                ", ruolo='" + ruolo + '\'' +
                ", partite vinte='" + numPartiteVinte + '\'' +
                ", partite giocate='" + numPartiteGiocate + '\'' +
                '}';
    }
}