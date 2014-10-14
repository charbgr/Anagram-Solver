package com.bmpak.anagramsolver.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bmpak.anagramsolver.R;
import com.bmpak.anagramsolver.dictionary.Dictionary;
import com.bmpak.anagramsolver.ui.MainActivity;
import com.bmpak.anagramsolver.utils.MyTextUtils;
import com.bmpak.anagramsolver.utils.Parser;
import com.bmpak.anagramsolver.word.EnglishWord;
import com.bmpak.anagramsolver.word.FranceWord;
import com.bmpak.anagramsolver.word.GermanWord;
import com.bmpak.anagramsolver.word.GreekWord;
import io.realm.Realm;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class InsertDictionaries extends IntentService {


    public static final String PARAM_DICTIONARIES = "dictionaries";
    public static final String BROADCAST_INSTALL_FINISH = "install_finished";

    public InsertDictionaries() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String[] dictionaries = intent.getStringArrayExtra(PARAM_DICTIONARIES);
            Realm realm = Realm.getInstance(this);


            for (String dictionary : dictionaries) {
                //long start = System.currentTimeMillis();
                //System.out.println("Installing " + dictionary + " AT: " + start);

                BufferedReader in = null;
                try {
                    in = new BufferedReader(
                            new InputStreamReader(getAssets().open(dictionary), "UTF-8")
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                realm.beginTransaction();

                //read dictionary from asset folder
                String lineWord;
                try {
                    while ((lineWord = in.readLine()) != null) {

                        //parse each with the correct parser
                        lineWord = Parser.parseWord(lineWord, dictionary);
                        if (dictionary.equals(Dictionary.ENGLISH_DICTIONARY_ASSET)) {
                            EnglishWord ew = realm.createObject(EnglishWord.class);
                            ew.setWord(lineWord);
                            ew.setWordAnagrammized(MyTextUtils.orderAlphabetically(lineWord));
                            //System.out.println("WORD: "+ew.getWord() + " | ANAGRAM: "+ew.getWordAnagrammized());
                        } else if (dictionary.equals(Dictionary.GREEK_DICTIONARY_ASSET)) {
                            GreekWord gw = realm.createObject(GreekWord.class);
                            gw.setWord(lineWord);
                            gw.setWordAnagrammized(MyTextUtils.orderAlphabetically(lineWord));
                        } else if (dictionary.equals(Dictionary.FRANCE_DICTIONARY_ASSET)) {
                            FranceWord fw = realm.createObject(FranceWord.class);
                            fw.setWord(lineWord);
                            fw.setWordAnagrammized(MyTextUtils.orderAlphabetically(lineWord));
                        } else if (dictionary.equals(Dictionary.GERMAN_DICTIONARY_ASSET)) {
                            GermanWord gw = realm.createObject(GermanWord.class);
                            gw.setWord(lineWord);
                            gw.setWordAnagrammized(MyTextUtils.orderAlphabetically(lineWord));
                        }
                    }

                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                realm.commitTransaction();
                //System.out.println("FINISH in " + (System.currentTimeMillis() - start));

            }

            //send broadcast to MainActivity
            sendBroadcast(new Intent(InsertDictionaries.BROADCAST_INSTALL_FINISH));


            //send notification
            Intent resultIntent = new Intent(this, MainActivity.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setAutoCancel(true)
                            .setContentTitle(getResources().getString(R.string.install_finish))
                            .setContentText(getResources().getString(R.string.ready_to_use_app))
                            .setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, mBuilder.build());

        }
    }

}
