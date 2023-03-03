package com.uliteteam.notes.maneger;

/** Created by Amr Ayman */
import android.content.Context;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.uliteteam.notes.maneger.ColorOfNote;
import com.uliteteam.notes.maneger.TextViewUndoRedo;
import com.uliteteam.notes.model.Note;
import com.uliteteam.notes.ui.dialog.BottomSheetCatalog;
import com.uliteteam.notes.util.NoteDataBase;

public class NoteManeger {

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

  private String colorId;
  private BottomSheetCatalog bottomSheet;

  private Context context;

  public NoteManeger(Context context) {
    this.context = context;
  }

        /*Constractors */
  public MenuItem getUndo() {
    return this.undo;
  }
        
  //Methods      

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
