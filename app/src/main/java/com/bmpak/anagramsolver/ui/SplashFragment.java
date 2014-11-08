package com.bmpak.anagramsolver.ui;

/**
 * Created by charbgr on 10/1/14.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import com.bmpak.anagramsolver.R;
import com.bmpak.anagramsolver.dictionary.Dictionary;
import com.bmpak.anagramsolver.ui.view.MyTextView;
import com.bmpak.anagramsolver.utils.ViewUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class SplashFragment extends Fragment implements View.OnClickListener {

    private OnLanguageSelectedListener mCallback;


    /**
     * Callback to an Activity for language selection
     */
    public interface OnLanguageSelectedListener {
        public void onLanguageSelectionFinish(String[] dictionaries);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnLanguageSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.hide();
    }

    private Hashtable<String, Boolean> selectedLangs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedLangs = new Hashtable<String, Boolean>();
        selectedLangs.put(Dictionary.ENGLISH_DICTIONARY_ASSET, false);
        selectedLangs.put(Dictionary.GREEK_DICTIONARY_ASSET, false);
        selectedLangs.put(Dictionary.FRANCE_DICTIONARY_ASSET, false);
        selectedLangs.put(Dictionary.GERMAN_DICTIONARY_ASSET, false);
    }

    @InjectView(R.id.install_languages)
    Button installBtn;

    @InjectView(R.id.select_languages)
    MyTextView message;

    @InjectView(R.id.en_dict)
    ImageButton enDictBtn;

    @InjectView(R.id.gr_el_dict)
    ImageButton grDictBtn;

    @InjectView(R.id.fr_dict)
    ImageButton frDictBtn;

    @InjectView(R.id.ge_dict)
    ImageButton geDictBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.inject(this, rootView);

        enDictBtn.setOnClickListener(this);
        enDictBtn.setTag(Dictionary.ENGLISH_DICTIONARY_ASSET);
        ViewUtils.setAlpha(enDictBtn, ViewUtils.DEFAULT_SEMI_ALPHA, 0);

        grDictBtn.setOnClickListener(this);
        grDictBtn.setTag(Dictionary.GREEK_DICTIONARY_ASSET);
        ViewUtils.setAlpha(grDictBtn, ViewUtils.DEFAULT_SEMI_ALPHA, 0);

        frDictBtn.setOnClickListener(this);
        frDictBtn.setTag(Dictionary.FRANCE_DICTIONARY_ASSET);
        ViewUtils.setAlpha(frDictBtn, ViewUtils.DEFAULT_SEMI_ALPHA, 0);

        geDictBtn.setOnClickListener(this);
        geDictBtn.setTag(Dictionary.GERMAN_DICTIONARY_ASSET);
        ViewUtils.setAlpha(geDictBtn, ViewUtils.DEFAULT_SEMI_ALPHA, 0);

        installBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if is at least on lang selected to install
                boolean isAtLeastOneSelected = false;
                for (Map.Entry<String, Boolean> set : selectedLangs.entrySet()) {

                    if (set.getValue() == true) {
                        isAtLeastOneSelected = true;
                        break;
                    }
                }

                //if yes, make an array and send a callback to Activity to
                // start the loader and replace fragment
                if (isAtLeastOneSelected) {
                    ArrayList<String> dictionaries = new ArrayList<String>();
                    for (Map.Entry<String, Boolean> set : selectedLangs.entrySet()) {
                        if (set.getValue())
                            dictionaries.add(set.getKey());
                    }

                    mCallback.onLanguageSelectionFinish(
                            dictionaries.toArray(new String[dictionaries.size()]));

                    installBtn.setVisibility(View.GONE);
                    rootView.findViewById(R.id.languages_section).setVisibility(View.GONE);



                    message.setText(R.string.installing_process);
                } else
                    Toast.makeText(getActivity(), R.string.error_select_one_language, Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }


    @Override
    public void onClick(View view) {

        String dictionary = (String) view.getTag();
        boolean isSelected = !selectedLangs.get(dictionary);
        selectedLangs.put(dictionary, isSelected);

        ViewUtils.setAlpha(view,
                isSelected ? ViewUtils.DEFAULT_FULL_ALPHA : ViewUtils.DEFAULT_SEMI_ALPHA,
                1000);
    }
}
