package com.bmpak.anagramsolver.utils;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import com.bmpak.anagramsolver.dictionary.Dictionary;
import com.bmpak.anagramsolver.interfaces.OnDictionaryChangeListener;
import com.bmpak.anagramsolver.word.EnglishWord;
import com.bmpak.anagramsolver.word.FranceWord;
import com.bmpak.anagramsolver.word.GermanWord;
import com.bmpak.anagramsolver.word.GreekWord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by charbgr on 10/1/14.
 */
public class AnagramTextWatcher implements TextWatcher, OnDictionaryChangeListener {

    public interface OnWordsFound {
        public void onWordsFound(int wordsCount);
    }

    private OnWordsFound mCallback;
    private ArrayAdapter<String> adapter;
    private Realm realm;

    private String dictionary;
    private String anagram;

    /* flag to prevent search again for the same word */
    private boolean didScrabble;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    public AnagramTextWatcher(OnWordsFound mCallback, Realm realm, ArrayAdapter<String> adapter) {
        this.mCallback = mCallback;
        this.realm = realm;
        this.adapter = adapter;
        didScrabble = true;
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

            didScrabble = false;
        } else {
            mCallback.onWordsFound(-1);
            adapter.clear();
            adapter.notifyDataSetChanged();

            didScrabble = true;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void dictionaryChange(String dictionary) {
        this.dictionary = dictionary;
        if (anagram != null)
            searchForAnagrams(anagram);

        didScrabble = false;

    }

    private void searchForAnagrams(String anagram) {
        anagram = Parser.parseWord(anagram, dictionary);
        anagram = MyTextUtils.orderAlphabetically(anagram);

        RealmResults results = realm
                .where(Dictionary.getClass(dictionary))
                .equalTo("wordAnagrammized", anagram)
                .findAll();

        //clear previous results
        adapter.clear();

        addResultsToAdapter(results);

        adapter.notifyDataSetChanged();
        mCallback.onWordsFound(results.size());

    }


    public void setSwipeRefreshLayout(SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    public void scrabble() {

        if (!didScrabble) {
            didScrabble = true;

            new AsyncTask<Void, Void, ArrayList<String>>() {

                @Override
                protected void onPreExecute() {
                    if (!adapter.isEmpty())
                        adapter.clear();
                }

                @Override
                protected ArrayList<String> doInBackground(Void... params) {

                    int numOfSubsets = 1 << anagram.length();
                    Set<String> matchingWords = new HashSet<String>();

                    for (int i = 0; i < numOfSubsets; i++) {
                        int pos = anagram.length() - 1;
                        int bitmask = i;

                        StringBuilder scrabbleBuilder = new StringBuilder("");
                        while (bitmask > 0) {
                            if ((bitmask & 1) == 1)
                                scrabbleBuilder.append(anagram.charAt(pos));
                            bitmask >>= 1;
                            pos--;
                        }

                        if (scrabbleBuilder.toString().length() > 2) {

                            String scrabbled = scrabbleBuilder.toString();
                            scrabbled = Parser.parseWord(scrabbled, dictionary);
                            scrabbled = MyTextUtils.orderAlphabetically(scrabbled);

                            RealmResults results = realm
                                    .where(Dictionary.getClass(dictionary))
                                    .equalTo("wordAnagrammized", scrabbled)
                                    .findAll();

                            matchingWords.addAll(getResultsToList(results));
                        }
                    }

                    ArrayList<String> sortedWords = new ArrayList<String>(matchingWords);

                    Collections.sort(sortedWords, new Comparator<String>() {
                        public int compare(String a, String b) {
                            if (a.length() < b.length()) {
                                return 1;
                            } else if (a.length() > b.length()) {
                                return -1;
                            }
                            return a.compareTo(b);
                        }
                    });


                    return sortedWords;
                }

                @Override
                protected void onPostExecute(ArrayList<String> words) {
                    adapter.addAll(words);
                    adapter.notifyDataSetChanged();
                    mCallback.onWordsFound(words.size());

                    if (mSwipeRefreshLayout != null)
                        mSwipeRefreshLayout.setRefreshing(false);
                }
            }.execute();
        }else{
            if (mSwipeRefreshLayout != null)
                mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void addResultsToAdapter(RealmResults results) {
        if (dictionary.equals(Dictionary.ENGLISH_DICTIONARY_ASSET)) {
            for (EnglishWord word : (RealmResults<EnglishWord>) results)
                adapter.add(word.getWord());
        } else if (dictionary.equals(Dictionary.GREEK_DICTIONARY_ASSET)) {
            for (GreekWord word : (RealmResults<GreekWord>) results)
                adapter.add(word.getWord());
        } else if (dictionary.equals(Dictionary.FRANCE_DICTIONARY_ASSET)) {
            for (FranceWord word : (RealmResults<FranceWord>) results)
                adapter.add(word.getWord());
        } else if (dictionary.equals(Dictionary.GERMAN_DICTIONARY_ASSET)) {
            for (GermanWord word : (RealmResults<GermanWord>) results)
                adapter.add(word.getWord());
        }
    }

    private ArrayList<String> getResultsToList(RealmResults results) {

        ArrayList<String> list = new ArrayList<String>();

        if (dictionary.equals(Dictionary.ENGLISH_DICTIONARY_ASSET)) {
            for (EnglishWord word : (RealmResults<EnglishWord>) results)
                list.add(word.getWord());
        } else if (dictionary.equals(Dictionary.GREEK_DICTIONARY_ASSET)) {
            for (GreekWord word : (RealmResults<GreekWord>) results)
                list.add(word.getWord());
        } else if (dictionary.equals(Dictionary.FRANCE_DICTIONARY_ASSET)) {
            for (FranceWord word : (RealmResults<FranceWord>) results)
                list.add(word.getWord());
        } else if (dictionary.equals(Dictionary.GERMAN_DICTIONARY_ASSET)) {
            for (GermanWord word : (RealmResults<GermanWord>) results)
                list.add(word.getWord());
        }

        return list;
    }

}
