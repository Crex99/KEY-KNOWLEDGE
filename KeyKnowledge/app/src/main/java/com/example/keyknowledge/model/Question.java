package com.example.keyknowledge.model;

public class Question {

    private String id;
    private String testo;
    private String risposta1;
    private String risposta2;
    private String risposta3;
    private String risposta4;
    private int risposta_corretta;
    private String categoria;

    public Question() {
    }

    public Question(String id,String testo,String risposta1,String risposta2,String risposta3,String risposta4,int risposta_corretta,String categoria) {
        this.testo = testo;
        this.id=id;
        this.risposta1=risposta1;
        this.risposta2=risposta2;
        this.risposta3=risposta3;
        this.risposta4=risposta4;
        this.risposta_corretta=risposta_corretta;
        this.categoria=categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getId() {
        return id;
    }

    public String getRisposta1() {
        return risposta1;
    }

    public String getRisposta2() {
        return risposta2;
    }

    public String getRisposta3() {
        return risposta3;
    }

    public String getRisposta4() {
        return risposta4;
    }

    public int getRisposta_corretta() {
        return risposta_corretta;
    }

    public String getTesto() {
        return testo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRisposta1(String risposta1) {
        this.risposta1 = risposta1;
    }

    public void setRisposta2(String risposta2) {
        this.risposta2 = risposta2;
    }

    public void setRisposta3(String risposta3) {
        this.risposta3 = risposta3;
    }

    public void setRisposta4(String risposta4) {
        this.risposta4 = risposta4;
    }

    public void setRisposta_corretta(int risposta_corretta) {
        this.risposta_corretta = risposta_corretta;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", testo='" + testo + '\'' +
                ", risposta1='" + risposta1 + '\'' +
                ", risposta2='" + risposta2 + '\'' +
                ", risposta3='" + risposta3 + '\'' +
                ", risposta4='" + risposta4 + '\'' +
                ", risposta_corretta=" + risposta_corretta +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
