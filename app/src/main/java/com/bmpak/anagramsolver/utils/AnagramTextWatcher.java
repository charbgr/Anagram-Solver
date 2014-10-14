package com.bmpak.anagramsolver.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import com.bmpak.anagramsolver.interfaces.OnDictionaryChangeListener;
import com.bmpak.anagramsolver.dictionary.Dictionary;
import com.bmpak.anagramsolver.word.EnglishWord;
import com.bmpak.anagramsolver.word.FranceWord;
import com.bmpak.anagramsolver.word.GermanWord;
import com.bmpak.anagramsolver.word.GreekWord;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by charbgr on 10/1/14.
 */
public class AnagramTextWatcher implements TextWatcher, OnDictionaryChangeListener {

    public interface OnWordsFound{
        public void onWordsFound(int wordsCount);
    }

    private OnWordsFound mCallback;
    private ArrayAdapter<String> adapter;
    private Realm realm;

    private String dictionary;
    private String anagram;


    public AnagramTextWatcher(OnWordsFound mCallback, Realm realm, ArrayAdapter<String> adapter) {
        this.mCallback = mCallback;
        this.realm = realm;
        this.adapter = adapter;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence inputWord, int start, int before, int count) {
        if (inputWord.length() > 0) {
            anagram = inputWord.toString().trim();
            searchForAnagrams(anagram);

        } else {
            mCallback.onWordsFound(-1);
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void dictionaryChange(String dictionary) {
        this.dictionary = dictionary;
        if(anagram != null)
            searchForAnagrams(anagram);

    }

    private void searchForAnagrams(String anagram){
        anagram = Parser.parseWord(anagram, dictionary);
        anagram = MyTextUtils.orderAlphabetically(anagram);

        RealmResults results = realm
                .where(Dictionary.getClass(dictionary))
                .equalTo("wordAnagrammized", anagram)
                .findAll();

        //clear previous results
        adapter.clear();

        if(dictionary.equals(Dictionary.ENGLISH_DICTIONARY_ASSET)) {
            for (EnglishWord word : (RealmResults<EnglishWord>)results)
                adapter.add(word.getWord());
        }else if(dictionary.equals(Dictionary.GREEK_DICTIONARY_ASSET)) {
            for (GreekWord word : (RealmResults<GreekWord>) results)
                adapter.add(word.getWord());
        }else if(dictionary.equals(Dictionary.FRANCE_DICTIONARY_ASSET)){
            for(FranceWord word : (RealmResults<FranceWord>) results)
                adapter.add(word.getWord());
        }else if(dictionary.equals(Dictionary.GERMAN_DICTIONARY_ASSET)){
            for(GermanWord word : (RealmResults<GermanWord>) results)
                adapter.add(word.getWord());
        }

        adapter.notifyDataSetChanged();
        mCallback.onWordsFound(results.size());

    }
}
