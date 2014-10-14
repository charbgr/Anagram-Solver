package com.bmpak.anagramsolver.utils;

import java.util.Arrays;

/**
 * Created by charbgr on 10/1/14.
 */
public class MyTextUtils {


    public static String orderAlphabetically(String word) {
        char chars[] = word.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }

}
