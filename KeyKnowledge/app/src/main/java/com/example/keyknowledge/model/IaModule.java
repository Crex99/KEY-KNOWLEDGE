package com.example.keyknowledge.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class IaModule {


    private static final int MAX_STREAK=2;
    private Quiz quiz;
    private DatabaseReference mDatabase;
    private QuestionManager manager;
    private ArrayList<String> categories=new ArrayList<String>();
    private HashMap<String,Integer> giuste;
    private HashMap<String,Integer> sbagliate;
    private String[] questions={"arte","generale","geo","scienze","storia"};
    private String[] levels={"livello1","livello2","livello3","livello4"};
    private HashMap<String,Boolean> risposte;

    public IaModule(Quiz q){
        manager=new QuestionManager();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        quiz=q;
        risposte=new HashMap<String,Boolean>();
        giuste=new HashMap<String,Integer>();
        sbagliate=new HashMap<String,Integer>();
        categories.add("arte");
        categories.add("cultura generale");
        categories.add("geografia");
        categories.add("scienze");
        categories.add("storia");
        for(int i=0;i<5;i++){
            String curr=categories.get(i);
            giuste.put(curr,0);
            sbagliate.put(curr,0);
        }
    }

    public  void nextQuestion(int current_question,boolean risposta){
        String id="";
        String categoria="";
        String livello="";
        int level=0;
        Random r=new Random();
        if(current_question==0){
            int cat=r.nextInt(categories.size()-1);
             level=r.nextInt(questions.length-1);
            int max=(level+1)*4;
            int min=(level+1)*4-(questions.length-1);
            int domanda=randInt(min,max);
            categoria=categories.get(cat);
            livello=levels[level];
            id=questions[cat]+domanda;
            manager.getQuestion(categoria,livello,id);
        }else{
            risposte.put(id,risposta);
            if(risposta){
                giuste.put(categoria,giuste.get(categoria)+1);
                sbagliate.put(categoria,0);
                for(String cat:categories){
                    if(!cat.equals(categoria)){
                        categories.add(cat);
                    }
                }
                if(giuste.get(categoria)==1){
                    if(level<4){
                        level++;
                        id=getRandomQuestion(level,categoria);
                        manager.getQuestion(categoria,"livello"+level,id);
                    }
                    if(id==null){
                            String category=getRandomCategory(categoria);
                            id=getRandomQuestion(0,category);
                            manager.getQuestion(category,"livello0",id);
                        //id=getRandomQuestion(level,categoria);
                    }

                    //manager.getQuestion(categoria,livello,id);
                }else if(giuste.get(categoria)>1){
                    String category=getRandomCategory(categoria);
                    id=getRandomQuestion(0,category);
                    manager.getQuestion(categoria,"livello0",id);
                }
            }else{
                categories.add(categoria);
                sbagliate.put(categoria,sbagliate.get(categoria)+1);
                giuste.put(categoria,0);
                if(sbagliate.get(categoria)==1){
                    manager.getQuestion(categoria,livello,id);
                }else if(sbagliate.get(categoria)==2){
                    level--;

                }else if(sbagliate.get(categoria)>=3){
                    String category=getRandomCategory(categoria);
                    id=getRandomQuestion(0,category);
                    manager.getQuestion(category,"livello0",id);
                    return;
                }
            }




        }
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    //prende in input la categoria che non deve essere ripescata
    private String getRandomCategory(String exclusive){
        int index=randInt(0,categories.size()-1);
        String result=categories.get(index);
        while(result.equals(exclusive)){
            index=randInt(0,categories.size()-1);
            result=categories.get(index);
        }

        return result;
    }

   private String getRandomQuestion(int level,String categoria){
        int max=(level+1)*4;
        int min=(level+1)*4-(questions.length-1);
        int domanda=randInt(min,max);
        String result=categoria+domanda;
        while((risposte.containsKey(result))) {
            if(risposte.containsKey(categoria+min)&&risposte.containsKey(categoria+min+1)&&risposte.containsKey(categoria+min+2)&&risposte.containsKey(categoria+max)) {
                return null;
            }
            domanda=randInt(min,max);
            result=categoria+domanda;
        }
        return result;
    }
}
