package com.example.keyknowledge;


import com.example.keyknowledge.model.Question;
import com.example.keyknowledge.model.QuestionManager;
import com.google.firebase.database.FirebaseDatabase;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

public class AllTests {

    public static Test suite(){
        TestSuite suite = new TestSuite();
        suite.addTest(QuestionManagerTest.suite());
        //suite.addTest(QuestionTest.suite());
        suite.addTest(QuizManagerTest.suite());
        //suite.addTest(QuizTest.suite());
        suite.addTest(UserManagerTest.suite());
        //suite.addTest(UserTest.suite());
        return suite;
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
    }
}
