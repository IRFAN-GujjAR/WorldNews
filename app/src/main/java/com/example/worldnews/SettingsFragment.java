package com.example.worldnews;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preference);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();

        int count = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {

            Preference preference = preferenceScreen.getPreference(i);
            String value = sharedPreferences.getString(preference.getKey(), "");
            setPreferenceSummary(preference, value);

        }

    }

    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
                if(listPreference.getKey()==getString(R.string.category_key)) {
                    setPreferenceIconForCategory(listPreference, value);
                }else if(listPreference.getKey()==getString(R.string.country_key)) {
                    setPreferenceIconForCountry(listPreference, value);
                }
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null) {
            String value = sharedPreferences.getString(preference.getKey(), "");
            setPreferenceSummary(preference, value);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setPreferenceIconForCountry(ListPreference listPreference, String value) {
        int resourceIdOfIcon = 0;
        switch (value) {
            case "select_all":
                resourceIdOfIcon = R.drawable.ic_worldwide;
                break;
            case "ar":
                resourceIdOfIcon = R.drawable.ic_argentina;
                break;
            case "au":
                resourceIdOfIcon = R.drawable.ic_australia;
                break;
            case "at":
                resourceIdOfIcon = R.drawable.ic_austria;
                break;
            case "be":
                resourceIdOfIcon = R.drawable.ic_belgium;
                break;
            case "br":
                resourceIdOfIcon = R.drawable.ic_brazil;
                break;
            case "bg":
                resourceIdOfIcon = R.drawable.ic_bulgaria;
                break;
            case "ca":
                resourceIdOfIcon = R.drawable.ic_canada;
                break;
            case "cn":
                resourceIdOfIcon = R.drawable.ic_china;
                break;
            case "co":
                resourceIdOfIcon = R.drawable.ic_colombia;
                break;
            case "cu":
                resourceIdOfIcon = R.drawable.ic_cuba;
                break;
            case "cz":
                resourceIdOfIcon = R.drawable.ic_czech_republic;
                break;
            case "eg":
                resourceIdOfIcon = R.drawable.ic_egypt;
                break;
            case "fr":
                resourceIdOfIcon = R.drawable.ic_france;
                break;
            case "de":
                resourceIdOfIcon = R.drawable.ic_germany;
                break;
            case "gr":
                resourceIdOfIcon = R.drawable.ic_greece;
                break;
            case "hk":
                resourceIdOfIcon = R.drawable.ic_hong_kong;
                break;
            case "hu":
                resourceIdOfIcon = R.drawable.ic_hungary;
                break;
            case "in":
                resourceIdOfIcon = R.drawable.ic_india;
                break;
            case "id":
                resourceIdOfIcon = R.drawable.ic_indonesia;
                break;
            case "ie":
                resourceIdOfIcon = R.drawable.ic_ireland;
                break;
            case "il":
                resourceIdOfIcon = R.drawable.ic_israel;
                break;
            case "it":
                resourceIdOfIcon = R.drawable.ic_italy;
                break;
            case "jp":
                resourceIdOfIcon = R.drawable.ic_japan;
                break;
            case "lv":
                resourceIdOfIcon = R.drawable.ic_latvia;
                break;
            case "lt":
                resourceIdOfIcon = R.drawable.ic_lithuania;
                break;
            case "my":
                resourceIdOfIcon = R.drawable.ic_malaysia;
                break;
            case "mx":
                resourceIdOfIcon = R.drawable.ic_mexico;
                break;
            case "ma":
                resourceIdOfIcon = R.drawable.ic_morocco;
                break;
            case "nl":
                resourceIdOfIcon = R.drawable.ic_netherlands;
                break;
            case "nz":
                resourceIdOfIcon = R.drawable.ic_new_zealand;
                break;
            case "ng":
                resourceIdOfIcon = R.drawable.ic_nigeria;
                break;
            case "no":
                resourceIdOfIcon = R.drawable.ic_norway;
                break;
            case "ph":
                resourceIdOfIcon = R.drawable.ic_philippines;
                break;
            case "pl":
                resourceIdOfIcon = R.drawable.ic_poland;
                break;
            case "pt":
                resourceIdOfIcon = R.drawable.ic_portugal;
                break;
            case "ro":
                resourceIdOfIcon = R.drawable.ic_romania;
                break;
            case "ru":
                resourceIdOfIcon = R.drawable.ic_russia;
                break;
            case "sa":
                resourceIdOfIcon = R.drawable.ic_saudi_arabia;
                break;
            case "rs":
                resourceIdOfIcon = R.drawable.ic_serbia;
                break;
            case "sg":
                resourceIdOfIcon = R.drawable.ic_singapore;
                break;
            case "sk":
                resourceIdOfIcon = R.drawable.ic_slovakia;
                break;
            case "si":
                resourceIdOfIcon = R.drawable.ic_slovenia;
                break;
            case "za":
                resourceIdOfIcon = R.drawable.ic_south_africa;
                break;
            case "kr":
                resourceIdOfIcon = R.drawable.ic_south_korea;
                break;
            case "se":
                resourceIdOfIcon = R.drawable.ic_sweden;
                break;
            case "ch":
                resourceIdOfIcon = R.drawable.ic_switzerland;
                break;
            case "tw":
                resourceIdOfIcon = R.drawable.ic_taiwan;
                break;
            case "th":
                resourceIdOfIcon = R.drawable.ic_thailand;
                break;
            case "tr":
                resourceIdOfIcon = R.drawable.ic_turkey;
                break;
            case "ae":
                resourceIdOfIcon = R.drawable.ic_united_arab_emirates;
                break;
            case "ua":
                resourceIdOfIcon = R.drawable.ic_ukraine;
                break;
            case "gb":
                resourceIdOfIcon = R.drawable.ic_united_kingdom;
                break;
            case "us":
                resourceIdOfIcon = R.drawable.ic_united_states;
                break;
            case "ve":
                resourceIdOfIcon = R.drawable.ic_venezuela;
                break;

        }
        listPreference.setIcon(resourceIdOfIcon);
    }

    private void setPreferenceIconForCategory(ListPreference listPreference, String value) {
        int resourceIdforCategory = 0;
        switch (value) {
            case "general":
                resourceIdforCategory = R.drawable.ic_general;
                break;
            case "sports":
                resourceIdforCategory = R.drawable.ic_sports;
                break;
            case "health":
                resourceIdforCategory = R.drawable.ic_health;
                break;
            case "entertainment":
                resourceIdforCategory = R.drawable.ic_entertainment;
                break;
            case "science":
                resourceIdforCategory = R.drawable.ic_science;
                break;
            case "technology":
                resourceIdforCategory = R.drawable.ic_technology;
                break;
            case "business":
                resourceIdforCategory = R.drawable.ic_business;
                break;

        }
        listPreference.setIcon(resourceIdforCategory);
    }
}
