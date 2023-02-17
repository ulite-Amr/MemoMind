package com.uliteteam.notes.fragment.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.transition.MaterialSharedAxis;
import com.uliteteam.notes.R;
import com.uliteteam.notes.fragment.settings.BasePreferenceFragment;

public class HomeSettingsFragment extends BasePreferenceFragment {

  // Constructor to set the title of the fragment
  public HomeSettingsFragment() {
    super(R.string.home_string);
  }

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    // Load the preferences from an XML resource
    setPreferencesFromResource(R.xml.pref_home, rootKey);
        
  }
}
