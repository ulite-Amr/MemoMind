package com.uliteteam.notes.fragment.settings;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ListView;
import androidx.annotation.StringRes;
import androidx.preference.PreferenceFragmentCompat;
import com.google.android.material.snackbar.Snackbar;

// This is an abstract class that extends PreferenceFragmentCompat, used as a base class for other
// preference fragments
public abstract class BasePreferenceFragment extends PreferenceFragmentCompat {

  private int titleId; // integer variable to store the title ID
  public View rootView; // View object to store the root view
  private Settings settings; // Settings object to store application settings

  public BasePreferenceFragment(@StringRes int titleId) { // Constructor with title ID parameter
    this.titleId = titleId; // set the title ID to the provided parameter
  }

  @Override
  public void onViewCreated(
      View view,
      Bundle
          savedInstanceState) { // Override the onViewCreated method to set the root view and
                                // disable padding clipping
    super.onViewCreated(view, savedInstanceState);
    rootView = view;
    getListView().setClipToPadding(false);
  }

  @Override
  public void
      onResume() { // Override the onResume method to set the title when the fragment is resumed
    super.onResume();
    if (titleId != 0) { // If title ID is not 0
      setTitle(getString(titleId)); // set the title to the string with the provided title ID
    }
  }

  protected void setTitle(CharSequence title) { // Method to set the activity title
    getActivity().setTitle(title);
  }

  public Settings getSettings() { // Method to get the application settings
    if (settings == null) { // If settings object is not yet instantiated
      settings = new Settings(); // instantiate it
    }
    return settings; // return the settings object
  }

  public void showSnackbar(String message) { // Method to show a snackbar with a message
    Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
  }
}
