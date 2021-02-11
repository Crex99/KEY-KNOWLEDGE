package com.example.keyknowledge.model;




import androidx.annotation.NonNull;
import com.example.keyknowledge.control.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;


public class PairingManager {
    private static final int MAX_ROOMS=100;
    private static final int LAST_ROOM=0;
    private static final String TABLE="matches";

    private DatabaseReference mDatabase;
    private PairingControl control;
    private MatchControl control2;

    public PairingManager(PairingControl c){
        control=c;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createMatch(User user,String mode) {
        mDatabase.child(TABLE).child(mode).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue()!=null) {
                    for (int i = 0; i < MAX_ROOMS; i++) {
                        String id = "" + i + "";
                        if (currentData.hasChild(id)) {
                            String status = currentData.child(id).child("status").getValue(String.class);
                            if (status.equals("void")) {
                                mDatabase.child(TABLE).child(mode).child(id).child("status").setValue("wait");
                                mDatabase.child(TABLE).child(mode).child(id).child("user1").setValue(user.getNickname());
                                mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        control.setQuiz(snapshot.child(id).getValue(Quiz.class));
                                        String status = snapshot.child(id).child("status").getValue(String.class);
                                        String opponent=snapshot.child(id).child("user2").getValue(String.class);
                                        if(status.equals("full")&&(!(opponent).equals("void"))) {
                                            Quiz quiz=snapshot.child(id).getValue(Quiz.class);
                                            control.startMatch(quiz,1);
                                            mDatabase.child(TABLE).child(mode).removeEventListener(this);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });
                                break;
                            } else if (status.equals("wait")) {
                                mDatabase.child(TABLE).child(mode).child(id).child("status").setValue("full");
                                mDatabase.child(TABLE).child(mode).child(id).child("user2").setValue(user.getNickname());
                                mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String status=snapshot.child(id).child("status").getValue(String.class);
                                        if(status.equals("full")){
                                            Quiz quiz=snapshot.child(id).getValue(Quiz.class);
                                            control.startMatch(quiz,2);
                                            mDatabase.child(TABLE).child(mode).removeEventListener(this);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                break;
                            }
                        }else{
                            String us=user.getNickname();
                            Quiz quiz=new Quiz();
                            quiz.setId(i);
                            quiz.setStatus("wait");
                            quiz.setUser2("void");
                            quiz.setUser1(us);
                            quiz.setMode(mode);
                            quiz.setNumQuesiti(10);
                            mDatabase.child(TABLE).child(mode).child(""+quiz.getId()+"").setValue(quiz);
                            mDatabase.child(TABLE).child(mode).addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    control.setQuiz(snapshot.child(id).getValue(Quiz.class));
                                    String status = snapshot.child(id).child("status").getValue(String.class);
                                    String opponent=snapshot.child(id).child("user2").getValue(String.class);
                                    if(status.equals("full")&&(!(opponent).equals("void"))) {
                                        Quiz quiz=snapshot.child(id).getValue(Quiz.class);
                                        control.startMatch(quiz,1);
                                        mDatabase.child(TABLE).child(mode).removeEventListener(this);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });
                            break;
                        }
                    }
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(DatabaseError error, boolean committed, DataSnapshot currentData) {

            }

        });
    }
    public void resetMatch(Quiz quiz) {
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").child("status").setValue("void");
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").child("user1").setValue("void");
        mDatabase.child(TABLE).child(quiz.getMode()).child(""+quiz.getId()+"").child("user2").setValue("void");
    }
}
