package com.uliteteam.notes;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.color.DynamicColors;
import com.uliteteam.notes.App;

public class App extends Application {

  private static App sIstance;
  private SharedPreferences preferences;
  public Context context;

  @Override
  public void onCreate() {
    super.onCreate();
    sIstance = this;
    Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));
    context = this;

    preferences = getSharedPreferences("com.uliteteam.notes", MODE_PRIVATE);

    var theme = preferences;

    AppCompatDelegate.setDefaultNightMode(
        theme.getInt("notes_theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM));
  }

  public static Context getContext() {
    return sIstance.getApplicationContext();
  }

  public static App getInstance() {
    return sIstance;
  }

  public static boolean isAtLeastS() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
  }

  public static boolean isAtLeastQ() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
  }

  public static SharedPreferences getDefaultPreferences() {
    return PreferenceManager.getDefaultSharedPreferences(sIstance.getApplicationContext());
  }

  public void updateTheme(int nightMode, Activity activity) {
    AppCompatDelegate.setDefaultNightMode(nightMode);
    activity.recreate();
    SharedPreferences.Editor editor = this.preferences.edit();
        editor.putInt("notes_theme", nightMode);
        editor.apply();    
  }
}
