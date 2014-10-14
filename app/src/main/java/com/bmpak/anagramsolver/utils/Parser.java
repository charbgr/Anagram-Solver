package com.bmpak.anagramsolver.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

import com.bmpak.anagramsolver.dictionary.Dictionary;

/**
 * Created by charbgr on 10/1/14.
 */
public class Parser {

    private Parser(){}

    private static String greekParser(String word) {
        word = word.replace('ΐ', 'Ι');
        word = word.replace('ΰ', 'Υ');
        word = word.toUpperCase();
        word = word.replace('Ά', 'Α');
        word = word.replace('Έ', 'Ε');
        word = word.replace('Ή', 'Η');
        word = word.replace('Ί', 'Ι');
        word = word.replace('Ό', 'Ο');
        word = word.replace('Ύ', 'Υ');
        word = word.replace('Ώ', 'Ω');

        return word;
    }

    private static String frenchParser(String word) {
        word = word.toLowerCase();
        word = word.replace('ī', 'I');
        word = word.replace('ï', 'I');
        word = word.replace('î', 'I');
        word = word.replace('ā', 'A');
        word = word.replace('â', 'A');
        word = word.replace('à', 'A');
        word = word.replace('č', 'C');
        word = word.replace('ç', 'C');
        word = word.replace('ź', 'Z');
        word = word.replace('ē', 'E');
        word = word.replace('è', 'E');
        word = word.replace('é', 'E');
        word = word.replace('ê', 'E');
        word = word.replace('ü', 'U');
        word = word.replace('ū', 'U');
        word = word.toUpperCase();
        word = word.replace('Ļ', 'L');
        word = word.replace('Ç', 'C');
        word = word.replace('Ó', 'O');
        word = word.replace('Ô', 'O');
        return word;
    }

    private static String germanParse(String word) {
        word = word.toUpperCase();
        word = word.replace('Ä', 'A');
        word = word.replace('Ö', 'O');
        word = word.replace('Ü', 'U');

        return word;
    }

    private static String englishParse(String word){
        return word.toUpperCase();
    }


    public static String parseWord(String word, String dictionary){
        if(dictionary.equals(Dictionary.ENGLISH_DICTIONARY_ASSET)){
            word = englishParse(word);
        }else if(dictionary.equals(Dictionary.GREEK_DICTIONARY_ASSET)){
            word = greekParser(word);
        }else if(dictionary.equals(Dictionary.FRANCE_DICTIONARY_ASSET)){
            word = frenchParser(word);
        }else if(dictionary.equals(Dictionary.GERMAN_DICTIONARY_ASSET)){
            word = germanParse(word);
        }

        return word;
    }

    //http://stackoverflow.com/questions/1008802/converting-symbols-accent-letters-to-english-alphabet
    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
