package com.example.keyknowledge.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class QuizTest extends TestCase {

    private Quiz quiz;

    @Override
    protected void setUp() throws Exception {
        quiz = new Quiz(1, Quiz.RESTART_MODE, 10, "nick1", "nick2");
    }

    public void testPunteggio(){
        quiz.setPunteggioG1(quiz.getPunteggioG1() + 1);
        assertEquals(0, quiz.getPunteggioG1());
    }

    public void testStatusNull(){
        assertEquals(null, quiz.getStatus());
    }

    public static Test suite(){
        return new TestSuite(QuizTest.class);
    }

}
