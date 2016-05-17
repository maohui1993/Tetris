package org.oucho.tetris;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

/*************************************************/
/* Preferences screen activity *******************/
/*************************************************/
public class Preferences extends PreferenceActivity{
	
	//Names the preferences are stored with
	private static final String MUSIC_KEY = "prefSoundMusic";
	private static final String FX_KEY = "prefSoundFX";
	private static final String VIB_KEY = "prefVibration";
	private static final String BG_KEY = "prefBackgrounds";
	private static final String ABG_KEY = "prefAboutBg";
	private static final String KEEPON_KEY = "prefKeepOn";
	
	PreferenceScreen preferences;
    SharedPreferences settings;
	
    /*************************************************/
	/* On create *************************************/
	/*************************************************/
	/* Sets the layout and the ui elements and *******/
	/* defines listener to change preferences. *******/
	/*************************************************/
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
		
	    final CheckBoxPreference music = (CheckBoxPreference) findPreference(MUSIC_KEY);
	    final CheckBoxPreference fx = (CheckBoxPreference) findPreference(FX_KEY);
	    final CheckBoxPreference vib = (CheckBoxPreference) findPreference(VIB_KEY);
	    final CheckBoxPreference backgrounds = (CheckBoxPreference) findPreference(BG_KEY);
	    final Preference aBackgrounds = findPreference(ABG_KEY);
	    final CheckBoxPreference keepOn = (CheckBoxPreference) findPreference(KEEPON_KEY);

	    music.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                    final CheckBoxPreference music = (CheckBoxPreference) preference;
                    music.setChecked((Boolean) newValue);
                    saveBoolean("music", (Boolean) newValue);
                    return false;
            	}
	    });
	    
	    fx.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                    final CheckBoxPreference fx = (CheckBoxPreference) preference;
                    fx.setChecked((Boolean) newValue);
                    saveBoolean("fx", (Boolean) newValue);
                    return false;
            	}
	    });
	    
	    vib.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                    final CheckBoxPreference vib = (CheckBoxPreference) preference;
                    vib.setChecked((Boolean) newValue);
                    saveBoolean("vib", (Boolean) newValue);
                    return false;
            	}
	    });
	    
	    backgrounds.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                    final CheckBoxPreference backgrounds = (CheckBoxPreference) preference;
                    backgrounds.setChecked((Boolean) newValue);
                    saveBoolean("backgrounds", (Boolean) newValue);
                    return false;
            	}
	    });

	    
	    keepOn.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                    final CheckBoxPreference keepOn = (CheckBoxPreference) preference;
                    keepOn.setChecked((Boolean) newValue);
                    saveBoolean("keepOn", (Boolean) newValue);
                    return false;
            	}
	    });
	}
	
	/*************************************************/
	/* Saves the value "value" for the preference ****/
	/* "field". **************************************/
	/*************************************************/
	private void saveBoolean(final String field, final boolean value) {
        final SharedPreferences prefFile = getSharedPreferences("settings", 0);
        final SharedPreferences.Editor editor = prefFile.edit();
        editor.putBoolean(field, value);
        editor.commit();
	}
}
