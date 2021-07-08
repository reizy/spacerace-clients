package com.codenjoy.dojo.spacerace.client;

public class Logger {

    public void log(String... out) {
        for (int i = 0; i < out.length; i++) {
            System.out.println(out[i]);
        }
    }

}
