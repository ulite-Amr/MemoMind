package com.uliteteam.notes.activity;

import android.content.res.Configuration;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.elevation.SurfaceColors;
////import com.itsaky.androidide.logsender.LogSender;
import com.uliteteam.notes.activity.BaseActivity;
import com.uliteteam.notes.model.Note;
import com.uliteteam.notes.databinding.ActivityNoteBinding;
import android.view.MenuItem;
import android.view.Menu;
import com.uliteteam.notes.R;
import android.content.Intent;
import com.uliteteam.notes.maneger.TextViewUndoRedo;
import com.uliteteam.notes.util.NoteDataBase;

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
    }else if(id == R.id.colorOfNote){
            
                  final BottomSheetDialog bottomSheetColors =
          new BottomSheetDialog(NoteActivity.this, R.style.ModalBottomSheetDialog);
      // Creating a new instance of the BottomSheetDialog with the context of the Details activity
      // and the specified style

      View bottomSheetView = getLayoutInflater().inflate(R.layout.color_pick_note, null);
      // Inflating the color_pick_note layout file and assigning it to the bottomSheetView variable

      bottomSheetColors.setContentView(bottomSheetView);
      // Setting the content view of the BottomSheetDialog to be the bottomSheetView

      // Initialize views
      final ImageView[] imageColors = new ImageView[11];
      final View[] viewColors = new View[11];
      for (int i = 0; i < 11; i++) {
        imageColors[i] =
            bottomSheetView.findViewById(
                getResources().getIdentifier("imageColor" + (i + 1), "id", getPackageName()));
        // Initializing the imageColors array and finding the ImageView with the corresponding ID in
        // the layout file
        viewColors[i] =
            bottomSheetView.findViewById(
                getResources().getIdentifier("color" + (i + 1), "id", getPackageName()));
        // Initializing the viewColors array and finding the View with the corresponding ID in the
        // layout file
      }

      for (int i = 0; i < imageColors.length; i++) {
        imageColors[i].setImageResource(
            i == Integer.parseInt(selectedNoteColor) ? R.drawable.done_circle : 0);
      }

      for (int i = 0; i < 11; i++) {
        final int finalI = i;
        viewColors[i].setOnClickListener(
            v -> {
              selectedNoteColor = String.valueOf(finalI);
              // Setting the value of the selectedNoteColor variable to the color that was clicked
              for (int j = 0; j < 11; j++) {
                imageColors[j].setImageResource(j == finalI ? R.drawable.done_circle : 0);
                // Setting the image resource of the imageColors array to the done_circle drawable
                // if the color was selected and setting it to 0 otherwise
              }
            });
        
        
      }
      bottomSheetColors.show();
            
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
