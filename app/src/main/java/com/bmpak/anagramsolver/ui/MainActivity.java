package com.bmpak.anagramsolver.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import com.bmpak.anagramsolver.R;
import com.bmpak.anagramsolver.service.InsertDictionaries;
import com.bmpak.anagramsolver.utils.AppPrefs;
import io.realm.Realm;

public class MainActivity extends ActionBarActivity implements
        SplashFragment.OnLanguageSelectedListener {


    private static Realm realm;
    private BroadcastReceiver insertDicts = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new AnagramFragment())
                    //.addToBackStack(null)
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        SharedPreferences prefs = getSharedPreferences(AppPrefs.MAIN_PREF_NAME, MODE_PRIVATE);
        int launchTimes = prefs.getInt(AppPrefs.APP_LAUNCHED_TIMES, 0);
        if (launchTimes == 0) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SplashFragment())
                    .commit();
        } else {

            //in case user forgot to select languages
            Set<String> langs = prefs.getStringSet(AppPrefs.DICTIONARIES_INSTALLED, new HashSet<String>());
            if(langs.size() == 0){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new SplashFragment())
                        .commit();
            }else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new AnagramFragment())
                        .commit();
            }
        }

        prefs.edit().putInt(AppPrefs.APP_LAUNCHED_TIMES, ++launchTimes).commit();

        realm = Realm.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(insertDicts, new IntentFilter(InsertDictionaries.BROADCAST_INSTALL_FINISH));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(insertDicts);
    }

    @Override
    public void onLanguageSelectionFinish(String[] dictionaries) {

        //prepare to save on preferences
        Set<String> dicts = new HashSet<String>(Arrays.asList(dictionaries));

        SharedPreferences prefs = getSharedPreferences(AppPrefs.MAIN_PREF_NAME, MODE_PRIVATE);
        prefs.edit().putStringSet(AppPrefs.DICTIONARIES_INSTALLED, dicts).commit();

        //trigger service
        Intent i = new Intent(this, InsertDictionaries.class);
        i.putExtra(InsertDictionaries.PARAM_DICTIONARIES, dictionaries);
        startService(i);

    }
}
