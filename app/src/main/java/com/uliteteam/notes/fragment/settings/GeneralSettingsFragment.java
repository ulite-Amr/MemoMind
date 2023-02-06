package com.uliteteam.notes.fragment.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.transition.MaterialSharedAxis;
import com.uliteteam.notes.R;
import com.uliteteam.notes.fragment.settings.BasePreferenceFragment;

public class GeneralSettingsFragment extends BasePreferenceFragment {


  public GeneralSettingsFragment() {
    super(R.string.pref_general);
  }

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.pref_general, rootKey);
        
  }
}
