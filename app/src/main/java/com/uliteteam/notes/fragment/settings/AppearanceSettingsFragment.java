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

/* This class represents a fragment for appearance settings that extends BasePreferenceFragment
 and implements SharedPreferences.OnSharedPreferenceChangeListener */

public class AppearanceSettingsFragment extends BasePreferenceFragment
    implements SharedPreferences.OnSharedPreferenceChangeListener {
  // The default constructor of the class.
  public AppearanceSettingsFragment() {
    super(R.string.pref_appearance); // calling the super constructor of BasePreferenceFragment
  }

  // This method is called during the fragment creation.
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    // Inflates the XML preferences file for this fragment
    addPreferencesFromResource(R.xml.pref_appearance);

    // Gets the SharedPreferences for the default preferences of the application.
    final SharedPreferences preferences =
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity());

    // Gets the App instance
    var app = App.getInstance();

    // Finds the preference that allows the user to change the theme.
    var preferenceTheme = findPreference("notes_theme");

    // Finds the SwitchPreferenceCompat for dynamic color and sets its listener.
    final SwitchPreferenceCompat dynamicColor =
        (SwitchPreferenceCompat) findPreference("dymanic_color");
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

    // Sets the OnPreferenceChangeListener for the theme preference.
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

    // Makes the dynamic color preference visible or not according to the Android version.
    if (DynamicColors.isDynamicColorAvailable()) {
      dynamicColor.setVisible(true);
    } else {
      dynamicColor.setVisible(false);
    }
  }

  // Called immediately after onCreateView() has returned, but before any saved state has been
  // restored in to the view.
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  // Called when the view previously created by onCreateView() has been detached from the fragment.
  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

  // This method is called when a shared preference is changed, added, or removed.
  @Override
  public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
    // Leave empty since there are no shared preferences to change in this fragment.
  }
}
