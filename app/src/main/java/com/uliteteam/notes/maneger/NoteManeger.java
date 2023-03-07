package com.uliteteam.notes.maneger;

/** Created by Amr Ayman */
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.uliteteam.notes.R;
import com.uliteteam.notes.activity.BaseActivity;
import com.uliteteam.notes.activity.Details;
import com.uliteteam.notes.activity.NoteActivity;
import com.uliteteam.notes.callback.BottomSheetCatalogCallBack;
import com.uliteteam.notes.model.Note;
import com.uliteteam.notes.ui.dialog.BottomSheetCatalog;
import com.uliteteam.notes.util.NoteDataBase;

public class NoteManeger extends BaseActivity {

  public TextViewUndoRedo undoRedo = null;
  public MenuItem undo = null;
  public MenuItem redo = null;
  public MenuItem colors;
  public boolean Editable = false;
  public NoteDataBase db;
  public Note note;
  public String selectedNoteColor;
  // Views
  public AppBarLayout appbar;
  public Toolbar toolbar;
  public EditText noteTitle;
  public EditText noteEdit;
  public TextView noteTitleText;
  public TextView contentText;
  public ExtendedFloatingActionButton fab;
  public LinearLayout contenar;
  public LinearLayout editorContinar;
  public LinearLayout previewContinar;

  public String colorId;
  BottomSheetCatalog bottomSheet;
  int menuR;
  boolean edit = false;
  public ColorOfNote colorOfNote;

  public Context context;

  public NoteManeger(Context context) {
    this.context = (BaseActivity) context;
  }

  public NoteActivity noteActivity = (NoteActivity) context;
  public Details detailsActivity = (Details) context;

  String todeysDate = Calendar.getInstance().getTime().toString();

  final Runnable updateMenuIconsState = () -> undoRedo.updateButtons();

  public NoteManeger() {}

  // Methods

  // on Create Menus inflate R.menu.menu_edut_note
  public void onCreateOptions(Menu menu) {
    undo = menu.findItem(R.id.undo);
    redo = menu.findItem(R.id.redo);
    colors = menu.findItem(R.id.colorOfNote);
    undoRedo = new TextViewUndoRedo(noteEdit, undo, redo);

    if (edit) colorOfNote = new ColorOfNote(colors, Editable);
    updateUndoRedoBtnState();
    if (edit) colors.setEnabled(false);
    if (edit) colorId = note.getColor();
    else colorId = "0";
  }

  // Update Undo/Redo Maneger
  public void updateUndoRedoBtnState() {
    new Handler(Looper.getMainLooper()).postDelayed(updateMenuIconsState, 10);
  }

  /*On Click Menus
  undo,redo,(),Fovoraite,settings,archive,copy*/
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    var id = item.getItemId();
    if (edit) colorId = note.getColor();

    if (undoRedo != null) {
      undoRedo.updateButtons();
    }

    if (id == R.id.undo) {
      undo();
      return true;
    } else if (id == R.id.redo) {
      redo();
      return true;
    } else if (id == R.id.delete) {
      // deleted Note from deleteNote Method from NoteDataBase.java
      db.deleteNote(note.getID());
      finish();
      return true;
    } else if (id == R.id.Favorite) {
      makeText("Not available yet");
      return true;
    } else if (id == R.id.settings) {
      noteActivity = (NoteActivity) context;
      noteActivity.goSettings(context);
      return true;
    } else if (id == R.id.archive) {
      makeText("Not available yet");
      return true;
    } else if (id == R.id.copy) {

      copyNote();
      return true;

    } else if (id == R.id.colorOfNote) {
      newCatalog();
      return true;
    } else return false;
  }

  public void copyNote() {

    /*Copy Note Text Title And Content
    Result :

    title: Title Note

    content :
    Note Content....*/
    ClipboardManager clipBoard =
        (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
    ClipData clip =
        ClipData.newPlainText(
            "CopyNote",
            "Title : "
                + noteTitle.getText().toString()
                + "\n\n Note Content : "
                + "\n"
                + noteEdit.getText().toString());
    clipBoard.setPrimaryClip(clip);
    makeText("Copyed Nite",context);
  }

  // Enabled(False),Enabled(True) to switch Preview Mod To Edit Mod loop
  public void EditorPreview(boolean Edit) {

    if (Edit == false) editorContinar.setVisibility(View.GONE);
    else editorContinar.setVisibility(View.VISIBLE);
    noteTitle.setEnabled(Edit);
    noteEdit.setEnabled(Edit);
  }

  public void onCreate(Bundle savedInstanceState) {

    colorOfNote = new ColorOfNote(colors, edit);
    noteEdit.addTextChangedListener(
        new TextWatcher() {

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
            if (edit) contentText.setText(noteEdit.getText().toString());
          }

          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
            if (edit) contentText.setText(noteEdit.getText().toString());
          }

          @Override
          public void afterTextChanged(Editable arg0) {
            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
            if (edit) contentText.setText(noteEdit.getText().toString());
          }
        });
  }

  // Updated Undo/Redo All times

  public void onPostCreate(Bundle savedInstanceState) {

    if (undoRedo != null) undoRedo.updateButtons();
  }

  public void onResume() {
    if (undoRedo != null) undoRedo.updateButtons();
  }

  public void onConfigurationChanged(Configuration config) {
    if (undoRedo != null) undoRedo.updateButtons();
  }

  // undo Click From UndoRedo TextView Maneger class
  public void undo() {
    if (undoRedo == null) return;
    if (undoRedo.getCanUndo()) undoRedo.undo();
  }
  // Redi Click From UndoRedo TextView Maneger class
  public void redo() {
    if (undoRedo == null) return;
    if (undoRedo.getCanRedo()) undoRedo.redo();
  }

  public void editNote() {

    note.setTitle(noteTitle.getText().toString());
    note.setContent(noteEdit.getText().toString());
    note.setColor(selectedNoteColor);
    note.setDate("Edit In : " + todeysDate);
    db.editNote(note);
    EditorPreview(false);
    makeText("Edited successfully");
    Editable = false;
    undoRedo.clearHistory();
    undoRedo.updateButtons();
    fab.setIconResource(R.drawable.ic_edit);
    noteTitleText.setText(noteTitle.getText().toString());
  }

  public void checkNameErrors() {
    noteActivity = (NoteActivity) context;
    if (noteTitle.getText().toString().equals("")) {
      noteTitle.setError("Field cannot be empty!");

      return;
    }

    Note note =
        new Note(
            noteTitle.getText().toString(),
            noteEdit.getText().toString(),
            todeysDate,
            selectedNoteColor);
    NoteDataBase db = new NoteDataBase(context);
    db.addNote(note);
    noteActivity.onBackPressed();
  }

  public void newCatalog() {
    BottomSheetCatalog bottomSheet =
        new BottomSheetCatalog(
            context,
            R.style.ModalBottomSheetDialog,
            selectedNoteColor,
            new BottomSheetCatalogCallBack() {
              @Override
              public void onChanged(String color) {
                selectedNoteColor = color;
              }
            });
    bottomSheet.setSelectedNoteColor(selectedNoteColor);
    bottomSheet.setAppbar(appbar);
    bottomSheet.setBackground(contenar);
    bottomSheet.show();
  }

  public void setUndo(MenuItem undo) {
    this.undo = undo;
  }

  public MenuItem getRedo() {
    return this.redo;
  }

  public void setRedo(MenuItem redo) {
    this.redo = redo;
  }

  public MenuItem getColors() {
    return this.colors;
  }

  public void setColors(MenuItem colors) {
    this.colors = colors;
  }

  public String getSelectedNoteColor() {
    return this.selectedNoteColor;
  }

  public void setSelectedNoteColor(String selectedNoteColor) {
    this.selectedNoteColor = selectedNoteColor;
  }

  public AppBarLayout getAppbar() {
    return this.appbar;
  }

  public void setAppbar(AppBarLayout appbar) {
    this.appbar = appbar;
  }

  public EditText getNoteTitle() {
    return this.noteTitle;
  }

  public void setNoteTitle(EditText noteTitle) {
    this.noteTitle = noteTitle;
  }

  public EditText getNoteEdit() {
    return this.noteEdit;
  }

  public void setNoteEdit(EditText noteEdit) {
    this.noteEdit = noteEdit;
  }

  public TextView getNoteTitleText() {
    return this.noteTitleText;
  }

  public void setNoteTitleText(TextView noteTitleText) {
    this.noteTitleText = noteTitleText;
  }

  public TextView getContentText() {
    return this.contentText;
  }

  public void setContentText(TextView contentText) {
    this.contentText = contentText;
  }

  public ExtendedFloatingActionButton getFab() {
    return this.fab;
  }

  public void setFab(ExtendedFloatingActionButton fab) {
    this.fab = fab;
  }

  public LinearLayout getContenar() {
    return this.contenar;
  }

  public void setContenar(LinearLayout contenar) {
    this.contenar = contenar;
  }

  public int getMenuR() {
    return this.menuR;
  }

  public void setMenuR(int menuR) {
    this.menuR = menuR;
  }

  public boolean getEdit() {
    return this.edit;
  }

  public void setEdit(boolean edit) {
    this.edit = edit;
  }
}
