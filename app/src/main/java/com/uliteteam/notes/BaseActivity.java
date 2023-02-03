package com.uliteteam.notes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
//import com.itsaky.androidide.logsender.LogSender;
// import com.itsaky.androidide.logsender.LogSender;

public class BaseActivity extends AppCompatActivity {

  public App app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // LogSender.startLogging(this);
//    LogSender.startLogging(this);
    // inside onCreate method
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    boolean isDynamic = preferences.getBoolean("dymanic_color", false);
    if (isDynamic) {
      setTheme(R.style.Theme_UliteTeam_Monet);
    }
    super.onCreate(savedInstanceState);
    Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));

    app = App.getInstance();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    var id = item.getItemId();

    if (id == android.R.id.home) {
      onBackPressed();
    } else {
      return super.onOptionsItemSelected(item);
    }

    return true;
  }

  public void makeText(String messege) {
    Toast.makeText(this, messege, Toast.LENGTH_SHORT).show();
  }
}
