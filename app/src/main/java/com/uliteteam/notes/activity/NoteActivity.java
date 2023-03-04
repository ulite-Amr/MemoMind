package com.uliteteam.notes.activity;

 /**
        Created By Amr Ayman
        
 **/
import android.content.res.Configuration;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.view.menu.MenuBuilder;
//// import com.itsaky.androidide.logsender.LogSender;
import com.uliteteam.notes.activity.BaseActivity;
import com.uliteteam.notes.maneger.NoteManeger;
import com.uliteteam.notes.model.Note;
import com.uliteteam.notes.databinding.ActivityNoteBinding;
import android.view.MenuItem;
import android.view.Menu;
import com.uliteteam.notes.R;
import android.content.Intent;
import com.uliteteam.notes.maneger.TextViewUndoRedo;
import com.uliteteam.notes.ui.dialog.BottomSheetCatalog;
import com.uliteteam.notes.util.NoteDataBase;
import com.uliteteam.notes.callback.BottomSheetCatalogCallBack;

public class NoteActivity extends BaseActivity {
        
        private ActivityNoteBinding binding;
        TextViewUndoRedo undoRedo;
        private MenuItem undo = null;
        private MenuItem redo = null;
        String selectedNoteColor = "0";
        final Runnable updateMenuIconsState = () -> undoRedo.updateButtons();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityNoteBinding.inflate(getLayoutInflater());

    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);

    binding.noteTitle.requestFocus();
    binding.fab.setOnClickListener(n -> FabClick());

    // implemen Edit text UndoRedo
    binding.noteContent.addTextChangedListener(
        new TextWatcher() {

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
          }

          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
          }

          @Override
          public void afterTextChanged(Editable arg0) {
            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
          }
        });
  }

  // get Date and time
  String todeysDate = Calendar.getInstance().getTime().toString();

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  public void FabClick() {
    checkNameErrors(binding.noteTitle);
  }

  private void checkNameErrors(EditText title) {
    if (title.getText().toString().equals("")) {
      title.setError("Field cannot be empty!");

      return;
    }

    Note note =
        new Note(
            binding.noteTitle.getText().toString(),
            binding.noteContent.getText().toString(),
            todeysDate,
            selectedNoteColor);
    NoteDataBase db = new NoteDataBase(this);
    db.addNote(note);
    onBackPressed();
  }

  // Undo Redo
  public boolean onCreateOptionsMenu(Menu menu) {
    if (menu instanceof MenuBuilder) ((MenuBuilder) menu).setOptionalIconsVisible(true);

    getMenuInflater().inflate(R.menu.menu_edit_create, menu);
    undo = menu.findItem(R.id.undo);
    redo = menu.findItem(R.id.redo);
    undoRedo = new TextViewUndoRedo(binding.noteContent, undo, redo);
    updateUndoRedoBtnState();
    return super.onCreateOptionsMenu(menu);
  }

  public void updateUndoRedoBtnState() {
    new Handler(Looper.getMainLooper()).postDelayed(updateMenuIconsState, 10);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    var id = item.getItemId();

    if (undoRedo != null) {
      undoRedo.updateButtons();
    }

    if (id == R.id.undo) {
      undo();
      return true;
    } else if (id == R.id.redo) {
      redo();
      return true;
    } else if (id == R.id.settings) {
      startActivity(new Intent(this, SettingsActivity.class));
      return true;
    } else if (id == R.id.copy) {

      return true;
    } else if (id == R.id.colorOfNote) {

      BottomSheetCatalog bottomSheet =
          new BottomSheetCatalog(
              NoteActivity.this,
              R.style.ModalBottomSheetDialog,
              selectedNoteColor,
              new BottomSheetCatalogCallBack() {
                @Override
                public void onChanged(String color) {
                  selectedNoteColor = color;
                }
              });
      bottomSheet.setSelectedNoteColor(selectedNoteColor);
      bottomSheet.setAppbar(binding.appBar);
      bottomSheet.setBackground(binding.Coordinator);
      bottomSheet.show();

      return true;
    } else return false;
  }

  @Override
  public void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    if (undoRedo != null) undoRedo.updateButtons();
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (undoRedo != null) undoRedo.updateButtons();
  }

  @Override
  public void onConfigurationChanged(Configuration config) {
    super.onConfigurationChanged(config);

    if (undoRedo != null) undoRedo.updateButtons();
  }

  public void undo() {
    if (undoRedo == null) return;
    if (undoRedo.getCanUndo()) undoRedo.undo();
  }

  public void redo() {
    if (undoRedo == null) return;
    if (undoRedo.getCanRedo()) undoRedo.redo();
  }
}
