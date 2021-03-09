package com.example.keyknowledge;

import com.example.keyknowledge.model.QuizTest;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class AllTests {

    public static Test suite(){
        TestSuite suite = new TestSuite();
        suite.addTest(QuizTest.suite());
        return suite;
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
    }
}
