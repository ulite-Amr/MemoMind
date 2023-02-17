package com.uliteteam.notes.fragment.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.transition.MaterialSharedAxis;
import com.uliteteam.notes.R;
import com.uliteteam.notes.fragment.settings.BasePreferenceFragment;

public class RootSettingsFragment extends BasePreferenceFragment {

  public RootSettingsFragment() {
    super(R.string.settings);
  }

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    // Inflate the preferences from the XML resource file
    setPreferencesFromResource(R.xml.pref_root, rootKey);
        
    // Set the enter and exit transition animations for this fragment
    setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
    setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
  }
}
