package com.uliteteam.notes.maneger;

/** Created by Amr Ayman */
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.uliteteam.notes.activity.BaseActivity;
import com.uliteteam.notes.callback.BottomSheetCatalogCallBack;
import com.uliteteam.notes.maneger.ColorOfNote;
import com.uliteteam.notes.maneger.TextViewUndoRedo;
import com.uliteteam.notes.model.Note;
import com.uliteteam.notes.ui.dialog.BottomSheetCatalog;
import com.uliteteam.notes.util.NoteDataBase;
import com.uliteteam.notes.R;
import android.text.Editable;
import android.text.TextWatcher;

public class NoteManeger extends BaseActivity {

  private TextViewUndoRedo undoRedo = null;
  private MenuItem undo = null;
  private MenuItem redo = null;
  private MenuItem colors;
  private boolean Editable = false;
  private ColorOfNote colorOfNote;
  private NoteDataBase db;
  private Note note;
  private String selectedNoteColor;
  // Views
  private AppBarLayout appbar;
  private EditText noteTitle;
  private AppCompatEditText noteEdit;
  private TextView noteTitleText;
  private TextView contentText;
  private ExtendedFloatingActionButton fab;
  private CoordinatorLayout contenar;
  private LinearLayout editorContinar;
  private LinearLayout previewContinar;

  private String colorId;
  private BottomSheetCatalog bottomSheet;

  private Context context;
        
  String todeysDate = Calendar.getInstance().getTime().toString();      

  final Runnable updateMenuIconsState = () -> undoRedo.updateButtons();

  public NoteManeger(Context context) {
    this.context = context;
  }

  /*Constractors */
  public MenuItem getUndo() {
    return this.undo;
  }

  // Methods

  // Update Undo/Redo Maneger
  public void updateUndoRedoBtnState() {
    new Handler(Looper.getMainLooper()).postDelayed(updateMenuIconsState, 10);
  }

  public void copyNote() {
    /*Copy Note Text Title And Content
    Result :

    title: Title Note

    content :
    Note Content....*/
    ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    ClipData clip =
        ClipData.newPlainText(
            "CopyNote",
            "Title : "
                + noteTitle.getText().toString()
                + "\n\n Note Content : "
                + "\n"
                + noteEdit.getText().toString());
    clipBoard.setPrimaryClip(clip);
    makeText("Copyed Nite");
  }

  // Enabled(False),Enabled(True) to switch Preview Mod To Edit Mod loop
  public void EditorPreview(boolean Edit) {

    if (Edit == false) editorContinar.setVisibility(View.GONE);
    else editorContinar.setVisibility(View.VISIBLE);
    noteTitle.setEnabled(Edit);
    noteEdit.setEnabled(Edit);
  }

  protected void onCreate(Bundle savedInstanceState) {

    noteEdit.addTextChangedListener(
        new TextWatcher() {

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
            contentText.setText(noteEdit.getText().toString());
          }

          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
            contentText.setText(noteEdit.getText().toString());
          }

          @Override
          public void afterTextChanged(Editable arg0) {
            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
            contentText.setText(noteEdit.getText().toString());
          }
        });
  }

  // Updated Undo/Redo All times

  public void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    if (undoRedo != null) undoRedo.updateButtons();
  }

  protected void onResume() {
    super.onResume();
    if (undoRedo != null) undoRedo.updateButtons();
  }

  public void onConfigurationChanged(Configuration config) {
    super.onConfigurationChanged(config);

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
        
  public void checkNameErrors(EditText title) {
    if (title.getText().toString().equals("")) {
      title.setError("Field cannot be empty!");

      return;
    }

    Note note =
        new Note(
            noteTitle.getText().toString(),
            noteEdit.getText().toString(),
            todeysDate,
            selectedNoteColor);
    NoteDataBase db = new NoteDataBase(this);
    db.addNote(note);
    onBackPressed();
  }      
        
  public void newCatalog(){
          BottomSheetCatalog bottomSheet =
          new BottomSheetCatalog(
              this,
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

  public AppCompatEditText getNoteEdit() {
    return this.noteEdit;
  }

  public void setNoteEdit(AppCompatEditText noteEdit) {
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

  public CoordinatorLayout getContenar() {
    return this.contenar;
  }

  public void setContenar(CoordinatorLayout contenar) {
    this.contenar = contenar;
  }
}
