package com.example.keyknowledge.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static final String[] CATEGORIE={"arte","cultura generale","geografia","scienze","storia"};
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
        for(int i=0;i<5;i++){
            String curr=CATEGORIE[i];
            giuste.put(curr,0);
            sbagliate.put(curr,0);
            categories.add(curr);
        }
    }

    public  void nextQuestion(int current_question,boolean risposta){
        if(!id.equals("")){
            Nodo pre=new Nodo(id,categoria,level);
            risposte.add(pre);
        }
        if(current_question==0){
            sendFirstQuestion();
            return;
        }else{
            updateStory(risposta,categoria);
            if(risposta){
                downProbability(categoria);
                if(giuste.get(categoria)==1){
                    Nodo next=getRandomQuestionUp(level,categoria);
                    sendQuestion(next);
                    return;
                }else if(giuste.get(categoria)==2){
                    Nodo next=getRandomQuestionUp(level-1,categoria);
                    sendQuestion(next);
                    return;
                }else if(giuste.get(categoria)>=3){
                    Nodo next=changeCategoryUp(categoria);
                    sendQuestion(next);
                    return;
                }
            }else{
                upProbability(categoria);
                if(sbagliate.get(categoria)==1){
                    Nodo next=getRandomQuestionDown(level,categoria);
                    sendQuestion(next);
                    return;
                }else if(sbagliate.get(categoria)==2){
                    Nodo next=getRandomQuestionDown(level-1,categoria);
                    sendQuestion(next);
                    return;
                }else if(sbagliate.get(categoria)>=3){
                    Nodo next=changeCategoryDown(categoria);
                    sendQuestion(next);
                    return;
                }
            }
        }
    }

    private void sendFirstQuestion(){
        Random r=new Random();
        int cat=r.nextInt(categories.size()-1);
        level=randInt(1,4);
        int max=(level)*4;
        int min=(level)*4-(questions.length-1);
        int domanda=randInt(min,max);
        categoria=categories.get(cat);
        id=questions[cat]+domanda;
        livello="livello"+level;
        manager.getQuestion(categoria,livello,id);
    }

    private void sendQuestion(Nodo next){
        categoria=next.getCategoria();
        level=next.getLivello();
        id=next.getId();
        manager.getQuestion(categoria,"livello"+level,id);
    }

    private Nodo changeCategoryDown(String attuale){
        String cavia=getRandomCategory(attuale);
        for(Nodo n:risposte){
            if(n.getCategoria().equals(cavia)){
                int livello=n.getLivello();
                return getRandomQuestionDown(livello,cavia);
            }
        }
        return getRandomQuestionUp(1,cavia);
    }

    private Nodo changeCategoryUp(String attuale){
        String cavia=getRandomCategory(attuale);
        for(Nodo n:risposte){
            if(n.getCategoria().equals(cavia)){
                int livello=n.getLivello();
                return getRandomQuestionUp(livello,cavia);
            }
        }
        return getRandomQuestionUp(1,cavia);
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

    private Nodo getRandomQuestionUp(int level,String categoria){

        String q=getRandomQuestion(level,categoria);
        int livello=level;
        while(q==null){
            livello++;
            if(livello>4){
                return changeCategoryDown(categoria);

            }
            q=getRandomQuestion(livello,categoria);
        }
        Nodo result=new Nodo(q,categoria,livello);
        return result;
    }

    private Nodo getRandomQuestionDown(int level,String categoria){
        String q=getRandomQuestion(level,categoria);
        int livello=level;
        while(q==null){
            livello--;
            if(livello<1){
                return changeCategoryUp(categoria);

            }
            q=getRandomQuestion(livello,categoria);
        }
        Nodo result=new Nodo(q,categoria,livello);
        return result;
    }

    private void upProbability(String category){
        categories.add(category);
    }

    private void downProbability(String category){
        for(String s:categories){
            if(s.equals(category)){
                continue;
            }
            categories.add(s);
        }
    }

    private void updateStory(boolean b,String category){
        for(int i=0;i<5;i++){
            String curr=CATEGORIE[i];
            if(curr.equals(category)){
                continue;
            }
            giuste.put(curr,0);
            sbagliate.put(curr,0);
        }
        if(b){
            giuste.put(category,giuste.get(category)+1);
            sbagliate.put(category,0);
        }else{
            sbagliate.put(category,sbagliate.get(category)+1);
            giuste.put(category,0);
        }
    }
}