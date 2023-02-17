package com.uliteteam.notes.fragment.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.transition.MaterialSharedAxis;
import com.uliteteam.notes.R;

// import the BasePreferenceFragment superclass
import com.uliteteam.notes.fragment.settings.BasePreferenceFragment;

// create a new GeneralSettingsFragment class that extends BasePreferenceFragment
public class GeneralSettingsFragment extends BasePreferenceFragment {

  // call the superclass constructor with the resource ID for the fragment s title
  public GeneralSettingsFragment() {
    super(R.string.pref_general);
  }

  // override the onCreatePreferences method to load the preferences from an XML resource
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.pref_general, rootKey);
  }
}
