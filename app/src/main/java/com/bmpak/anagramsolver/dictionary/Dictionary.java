package com.bmpak.anagramsolver.dictionary;

import com.bmpak.anagramsolver.R;
import com.bmpak.anagramsolver.word.EnglishWord;
import com.bmpak.anagramsolver.word.FranceWord;
import com.bmpak.anagramsolver.word.GermanWord;
import com.bmpak.anagramsolver.word.GreekWord;

/**
 * Created by charbgr on 10/1/14.
 */
public class Dictionary {

    private Dictionary(){}

    public static final String ENGLISH_DICTIONARY_ASSET = "english_dictionary.txt";
    public static final String GREEK_DICTIONARY_ASSET = "el_dictionary.txt";
    public static final String GERMAN_DICTIONARY_ASSET = "ge_dictionary.txt";
    public static final String FRANCE_DICTIONARY_ASSET = "france_dictionary.txt";


    public static Class getClass(String dictionary){

        if(dictionary.equals(Dictionary.GREEK_DICTIONARY_ASSET)){
            return GreekWord.class;
        }else if(dictionary.equals(Dictionary.FRANCE_DICTIONARY_ASSET)){
            return FranceWord.class;
        }else if(dictionary.equals(Dictionary.GERMAN_DICTIONARY_ASSET)){
            return GermanWord.class;
        }

        return EnglishWord.class;
    }

    public static int getDrawableId(String dictionary){

        if(dictionary.equals(Dictionary.GREEK_DICTIONARY_ASSET)){
            return R.drawable.greece_flag;
        }else if(dictionary.equals(Dictionary.FRANCE_DICTIONARY_ASSET)){
            return R.drawable.france_flag;
        }else if(dictionary.equals(Dictionary.GERMAN_DICTIONARY_ASSET)) {
            return R.drawable.german_flag;
        }

        return R.drawable.english_flag;
    }

 }
