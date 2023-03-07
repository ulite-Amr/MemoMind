package com.uliteteam.notes.activity;

/*Created By Amr Ayman*/
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.uliteteam.notes.activity.NoteActivity;
import com.uliteteam.notes.maneger.NoteManeger;
import com.uliteteam.notes.databinding.ActivityNoteBinding;
import com.uliteteam.notes.R;

public class NoteActivity extends BaseActivity {

  private ActivityNoteBinding binding;
  NoteManeger noteManeger = new NoteManeger(NoteActivity.this);
  Menu menu;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityNoteBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    inflateViewToManeger();
    setSupportActionBar(noteManeger.toolbar);
    noteManeger.onCreate(savedInstanceState);
    setSupportActionBar(binding.toolbar);

    noteManeger.setSelectedNoteColor("0");
    noteManeger.fab.setOnClickListener(
        v -> {
          noteManeger.checkNameErrors();
        });
  }

  private void inflateViewToManeger() {
    noteManeger.setAppbar(binding.appBar);
    noteManeger.setContenar(binding.continar);
    noteManeger.setNoteTitle(binding.noteTitle);
    noteManeger.setNoteEdit(binding.noteContent);
    noteManeger.setFab(binding.fab);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_edit_create, menu);
    noteManeger.onCreateOptions(menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
      return true;
    } else {
      return noteManeger.onOptionsItemSelected(item);
    }
  }

  public void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    noteManeger.onPostCreate(savedInstanceState);
  }

  public void onResume() {
    super.onResume();
    noteManeger.onResume();
  }

  public void onConfigurationChanged(Configuration config) {
    super.onConfigurationChanged(config);
    noteManeger.onConfigurationChanged(config);
	
  }
  
  public void goSettings(Context context){
		startActivity(new Intent(context,SettingsActivity.class));
	}
}
