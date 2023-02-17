package com.uliteteam.notes.fragment.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.transition.MaterialSharedAxis;
import com.uliteteam.notes.R;
import com.uliteteam.notes.fragment.settings.BasePreferenceFragment;

public class NoteSettingsFragment extends BasePreferenceFragment {

  // Constructor to set the title ID of the fragment
  public NoteSettingsFragment() {
    super(R.string.pref_note);
  }

  // Override the method to inflate the preferences from the XML resource
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.pref_note, rootKey);
  }
}
