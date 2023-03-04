package com.uliteteam.notes.activity;

/** Created By Amr Ayman */
import android.os.Bundle;
import com.uliteteam.notes.maneger.NoteManeger;
import com.uliteteam.notes.databinding.ActivityNoteBinding;

public class NoteActivity extends BaseActivity {

  private ActivityNoteBinding binding;
  NoteManeger noteManeger = new NoteManeger();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityNoteBinding.inflate(getLayoutInflater());

    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);

    inflateViewToManeger();
  }

  private void inflateViewToManeger() {
    noteManeger.setAppbar(binding.appBar);
    noteManeger.setContenar(binding.continar);
    noteManeger.setNoteTitle(binding.noteTitle);
    noteManeger.setNoteEdit(binding.noteContent);
    noteManeger.setFab(binding.fab);
  }
}
