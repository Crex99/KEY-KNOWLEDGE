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
import java.util.Objects;
import java.util.Random;

public class IaModule {

    private class Nodo{

        private String id;
        private String categoria;
        private int livello;

        public Nodo(String a,String b,int c){
            id=a;
            categoria=b;
            livello=c;
        }

        public String getId(){
            return id;
        }

        public String getCategoria(){
            return categoria;
        }

        public int getLivello(){
            return livello;
        }

        public void setId(String x){
            id=x;
        }

        public void setCategoria(String x){
            categoria=x;
        }

        public void setLivello(int x){
            livello=x;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Nodo nodo = (Nodo) o;
            if(nodo.getId().equals(id)){
                return true;
            }else{
                return false;
            }
        }
    }


    private Quiz quiz;
    private DatabaseReference mDatabase;
    private QuestionManager manager;
    private ArrayList<String> categories=new ArrayList<String>();
    private HashMap<String,Integer> giuste;
    private HashMap<String,Integer> sbagliate;
    private String[] questions={"arte","generale","geo","scienze","storia"};
    private String[] levels={"livello1","livello2","livello3","livello4"};
    private ArrayList<Nodo> risposte;
    private String id="",categoria="",livello="";
    private int level=0;

    public IaModule(Quiz q){
        manager=new QuestionManager();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        quiz=q;
        risposte=new ArrayList<Nodo>();
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
        if(!id.equals("")){
            Nodo pre=new Nodo(id,categoria,level);
            risposte.add(pre);
        }
        Random r=new Random();
        if(current_question==0){
            int cat=r.nextInt(categories.size()-1);
            level=randInt(1,4);
            int max=(level)*4;
            int min=(level)*4-(questions.length-1);
            int domanda=randInt(min,max);
            categoria=categories.get(cat);
            id=questions[cat]+domanda;
            livello="livello"+level;
            manager.getQuestion(categoria,livello,id);
            return;
        }else{
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
                            id=getRandomQuestion(1,category);
                            manager.getQuestion(category,"livello1",id);
                        //id=getRandomQuestion(level,categoria);
                    }

                    //manager.getQuestion(categoria,livello,id);
                }else if(giuste.get(categoria)>1){
                    String category=getRandomCategory(categoria);
                    id=getRandomQuestion(1,category);
                    manager.getQuestion(categoria,"livello1",id);
                }
            }else{
                categories.add(categoria);
                sbagliate.put(categoria,sbagliate.get(categoria)+1);
                giuste.put(categoria,0);
                if(sbagliate.get(categoria)==1){
                    id=getRandomQuestion(level,categoria);
                    while(id==null){
                        level--;
                        id=getRandomQuestion(level,categoria);
                    }
                    manager.getQuestion(categoria,livello,id);
                    return;
                }else if(sbagliate.get(categoria)==2){
                    level--;

                }else if(sbagliate.get(categoria)>=3){
                    String category=getRandomCategory(categoria);
                    id=getRandomQuestion(1,category);
                    manager.getQuestion(category,"livello1",id);
                    return;
                }
            }




        }
    }

    private Nodo changeCategory(String attuale){
        Nodo result;
        String cavia=getRandomCategory(attuale);
        for(Nodo n:risposte){
            if(n.getCategoria().equals(cavia)){
                int livello=n.getLivello();
                String q=getRandomQuestion(livello,cavia);
                while(q==null){
                    livello++;
                    q=getRandomQuestion(livello,cavia);
                }
                result=new Nodo(q,cavia,livello);
                return result;
            }
        }
        String question=getRandomQuestion(1,cavia);
        int i=1;
        while(question==null){
            i++;
            question=getRandomQuestion(i,cavia);
        }
        result=new Nodo(question,cavia,i);
        return result;
    }

    private static int randInt(int min, int max) {

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
        int max=(level)*4;
        int min=(level)*4-3;
        int domanda=randInt(min,max);
        String result=categoria+domanda;
        Nodo nodo=new Nodo(result,categoria,level);
        Nodo nodo1=new Nodo(categoria+min,categoria,level);
        Nodo nodo2=new Nodo(categoria+(min+1),categoria,level);
        Nodo nodo3=new Nodo(categoria+(min+2),categoria,level);
        Nodo nodo4=new Nodo(categoria+max,categoria,level);
        while((risposte.contains(nodo))) {
            if(risposte.contains(nodo1)&&risposte.contains(nodo2)&&risposte.contains(nodo3)&&risposte.contains(nodo4)) {
                return null;
            }
            domanda=randInt(min,max);
            result=categoria+domanda;
        }
        return result;
    }
}
