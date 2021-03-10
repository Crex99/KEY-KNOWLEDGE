package com.example.keyknowledge.model;

import android.content.Context;
import android.util.Log;

import com.example.keyknowledge.Match;
import com.example.keyknowledge.control.MatchControl;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class QuestionManagerTest extends TestCase{

    QuestionManager questionManager;
    private DatabaseReference mockedDatabaseReference;

    @Before
    public void before() {
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);
    }
    /*@Override
    protected void setUp() throws Exception {
        QuestionManager questionManager = new QuestionManager();
    }*/

    @Test
    public void getQuestionTest() {
        QuestionManager questionManager = new QuestionManager();
        questionManager = Mockito.mock(QuestionManager.class);
        Question question = new Question();
        when(questionManager.getQuestionInEvent(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(question);
        System.out.println(question);
        assertTrue(question != null);
    }
}
