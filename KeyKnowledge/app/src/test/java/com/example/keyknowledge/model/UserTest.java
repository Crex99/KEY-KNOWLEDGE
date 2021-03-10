package com.example.keyknowledge.model;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UserTest extends TestCase {

    private User user;

    @Override
    protected void setUp() throws Exception {
        user = new User("nick", "password", "email", "offline");
    }


}
