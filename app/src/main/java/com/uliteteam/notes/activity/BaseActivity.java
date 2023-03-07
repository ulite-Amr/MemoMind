package com.uliteteam.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.preference.PreferenceManager;
import android.widget.Toast;
import com.uliteteam.notes.R;
import androidx.appcompat.app.AppCompatActivity;
import com.uliteteam.notes.App;
import com.uliteteam.notes.util.CrashHandler;


public class BaseActivity extends AppCompatActivity {

  public App app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
                
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
  
  public void makeText(String messege,Context context) {
    Toast.makeText(context, messege, Toast.LENGTH_SHORT).show();
  }

  public void url(String url) {
    try {
      Intent open = new Intent(Intent.ACTION_VIEW);
      open.setData(Uri.parse(url));
      open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(open);
    } catch (Throwable th) {
      makeText(th.getMessage());
      th.printStackTrace();
    }
  }
}
