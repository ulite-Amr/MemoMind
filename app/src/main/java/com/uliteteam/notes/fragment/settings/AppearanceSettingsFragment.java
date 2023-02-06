package com.uliteteam.notes.fragment.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.transition.MaterialSharedAxis;
import com.uliteteam.notes.App;
import com.uliteteam.notes.R;
import androidx.preference.PreferenceManager;

public class AppearanceSettingsFragment extends BasePreferenceFragment
    implements SharedPreferences.OnSharedPreferenceChangeListener {

    
  public AppearanceSettingsFragment() {
    super(R.string.pref_appearance);
  }

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    addPreferencesFromResource(R.xml.pref_appearance);
    final SharedPreferences preferences =
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity());
    var app = App.getInstance();
    var preferenceTheme = findPreference("notes_theme");
    final SwitchPreferenceCompat dynamicColor =
        (SwitchPreferenceCompat) findPreference("dymanic_color");
    preferenceTheme.setOnPreferenceChangeListener(
        new Preference.OnPreferenceChangeListener() {
          @Override
          public boolean onPreferenceChange(Preference preference, Object newValue) {
            switch ((String) newValue) {
              case "dark":
                app.updateTheme(AppCompatDelegate.MODE_NIGHT_YES, requireActivity());
                break;
              case "light":
                app.updateTheme(AppCompatDelegate.MODE_NIGHT_NO, requireActivity());
                break;
              case "auto":
                if (App.isAtLeastQ()) {
                  app.updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, requireActivity());
                } else {
                  app.updateTheme(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY, requireActivity());
                }
                break;
            }

            // Save the selected theme to SharedPreferences
            SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("notes_theme", (String) newValue);
            editor.apply();
            return true;
          }
        });

    dynamicColor.setOnPreferenceChangeListener(
        new Preference.OnPreferenceChangeListener() {
          @Override
          public boolean onPreferenceChange(Preference preference, Object newValue) {
            boolean turned = (Boolean) newValue;
            preferences.edit().putBoolean("dymanic_color", turned).apply();
            showSnackbar("Restart the application to apply the dynamic colors");
            return true;
          }
        });

    if (DynamicColors.isDynamicColorAvailable()) {
      dynamicColor.setVisible(true);
    } else {
      dynamicColor.setVisible(false);
    }
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onDestroyView() {

    super.onDestroyView();
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {}
}
