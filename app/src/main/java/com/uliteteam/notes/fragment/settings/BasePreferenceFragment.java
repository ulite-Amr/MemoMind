package com.uliteteam.notes.fragment.settings;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ListView;
import androidx.annotation.StringRes;
import androidx.preference.PreferenceFragmentCompat;
import com.google.android.material.snackbar.Snackbar;

public abstract class BasePreferenceFragment extends PreferenceFragmentCompat {

  private int titleId;
  public View rootView;
  private Settings settings;

  public BasePreferenceFragment(@StringRes int titleId) {
    this.titleId = titleId;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    rootView = view;
    getListView().setClipToPadding(false);
  }

  @Override
  public void onResume() {
    super.onResume();
    if (titleId != 0) {
      setTitle(getString(titleId));
    }
  }

  protected void setTitle(CharSequence title) {
    getActivity().setTitle(title);
  }

  public Settings getSettings() {
    if (settings == null) {
      settings = new Settings();
    }
    return settings;
  }

  public void showSnackbar(String message) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
  }
}
