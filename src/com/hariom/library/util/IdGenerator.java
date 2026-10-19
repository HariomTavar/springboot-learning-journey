package com.hariom.library.util;

public class IdGenerator {
    private static int bookCounter = 100;
    private static int memberCounter = 1000;
 
    // private constructor: nobody should create an IdGenerator object
    private IdGenerator() {
    }
 
    public static int nextBookId() {
        return ++bookCounter;
    }
 
    public static int nextMemberId() {
        return ++memberCounter;}
}
