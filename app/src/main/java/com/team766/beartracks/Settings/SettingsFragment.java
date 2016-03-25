package com.team766.beartracks.Settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.team766.beartracks.R;

/**
 * Created by tommypacker on 7/31/15.
 */
public class SettingsFragment extends PreferenceFragment{

    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference licenses = findPreference("licenses");
        Preference source = findPreference("viewSource");
        Preference author = findPreference("author");
        Preference appVersion = findPreference("AppVersion");

        source.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                viewSource();
                return false;
            }
        });

        author.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                viewAuthor();
                return false;
            }
        });

        appVersion.setTitle("Bear Tracks " + "0.0.1");
    }

    private boolean viewSource(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://github.com/Team766/BearTracks_Android"));
        startActivity(intent);
        return true;
    }

    private boolean viewAuthor(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://github.com/tommypacker"));
        startActivity(intent);
        return true;
    }
}
