package com.uliteteam.notes.activity;

/** Created By Amr Ayman */
import android.os.Bundle;
import android.view.Menu;
import com.uliteteam.notes.maneger.NoteManeger;
import com.uliteteam.notes.databinding.ActivityNoteBinding;
import com.uliteteam.notes.R;

public class NoteActivity extends BaseActivity {

  private ActivityNoteBinding binding;
  NoteManeger noteManeger = new NoteManeger(this);
        Menu menu;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityNoteBinding.inflate(getLayoutInflater());

    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);

    inflateViewToManeger();
    noteManeger.setSelectedNoteColor("0");
    noteManeger.fab.setOnClickListener(
        v -> {
          noteManeger.checkNameErrors();
          onBackPressed();
        });
    noteManeger.setMenuR(R.menu.menu_edit_create);
    noteManeger.onCreateOptionsMenu(menu);
  }

  private void inflateViewToManeger() {
    noteManeger.setAppbar(binding.appBar);
    noteManeger.setContenar(binding.continar);
    noteManeger.setNoteTitle(binding.noteTitle);
    noteManeger.setNoteEdit(binding.noteContent);
    noteManeger.setFab(binding.fab);
  }
}
