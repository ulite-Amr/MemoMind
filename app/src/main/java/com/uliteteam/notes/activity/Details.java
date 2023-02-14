package com.uliteteam.notes.activity;
/*---------What does this class do?----------
 *    1-Get Details From Notes Adapter by Key "ID"
 *    2-Edit And Save Notes
 *     --   --  -- -- -- -- -- -- -- */
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.appcompat.view.menu.MenuBuilder;
// import com.itsaky.androidide.logsender.LogSender;
import com.uliteteam.notes.callback.BottomSheetCatalogCallBack;
import com.uliteteam.notes.model.Note;
import com.uliteteam.notes.R;
import com.uliteteam.notes.databinding.ActivityDetailsBinding;
import com.uliteteam.notes.maneger.ColorOfNote;
import com.uliteteam.notes.maneger.TextViewUndoRedo;
import com.uliteteam.notes.ui.dialog.BottomSheetCatalog;
import com.uliteteam.notes.util.NoteDataBase;
import com.uliteteam.notes.utile.FilePicker;

public class Details extends BaseActivity {

  // inilaizetion
  ActivityDetailsBinding binding;
  TextViewUndoRedo undoRedo = null;
  private MenuItem undo = null;
  private MenuItem redo = null;
  private MenuItem colors;
  boolean Editable = false;
  ColorOfNote colorOfNote;
  NoteDataBase db;
  public Note note;
  public String selectedNoteColor;
  Window w = this.getWindow();
  String colorId;
  BottomSheetCatalog bottomSheet;

  String todeysDate = Calendar.getInstance().getTime().toString();
  final Runnable updateMenuIconsState = () -> undoRedo.updateButtons();
  private FilePicker filepicker;
  private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityDetailsBinding.inflate(getLayoutInflater());
    //    LogSender.startLogging(this);
    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);

    Intent i = getIntent();
    long id = i.getLongExtra("ID", 0);

    db = new NoteDataBase(this);
    note = db.getNote(id);
    colorId = note.getColor();

    filepicker =
        new FilePicker(this) {

          @Override
          public void onRequestPermission(boolean isGranted) {}

          @Override
          public void onPickFile(Uri uri) {

            return;
          }
        };
    pickMedia =
        registerForActivityResult(
            new PickVisualMedia(),
            uri -> {
              // Callback is invoked after the user selects a media item or closes the
              // photo picker.

            });

    selectedNoteColor = note.getColor();

    binding.Coordinator.setTransitionName("appcard");

    EditorPreview(false);
    binding.noteTitle.setText(note.getTitle());
    binding.content.setText(note.getContent());

    binding.noteTitleText.setText(note.getTitle());
    binding.contentText.setText(note.getContent());

    bottomSheet =
        new BottomSheetCatalog(
            Details.this,
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
    bottomSheet.statusColors();

    binding.fab.setOnClickListener(
        v -> {
          if (Editable == false) {
            binding.fab.setIconResource(R.drawable.ic_save);
            EditorPreview(true);
            binding.noteTitle.requestFocus();
            colors.setEnabled(true);
            Editable = true;
          } else if (Editable == true) {
            // implements your code here
            editNote();
            colors.setEnabled(false);
          } else {

          }
        });

    Toast.makeText(this, selectedNoteColor, Toast.LENGTH_SHORT).show();
    binding.content.addTextChangedListener(
        new TextWatcher() {

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
            binding.contentText.setText(binding.content.getText().toString());
          }

          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
            binding.contentText.setText(binding.content.getText().toString());
          }

          @Override
          public void afterTextChanged(Editable arg0) {
            if (undoRedo != null) {
              undoRedo.updateButtons();
            }
            binding.contentText.setText(binding.content.getText().toString());
          }
        });
  }

  // on Create Menus inflate R.menu.menu_edut_note
  public boolean onCreateOptionsMenu(Menu menu) {
    if (menu instanceof MenuBuilder) ((MenuBuilder) menu).setOptionalIconsVisible(true);

    getMenuInflater().inflate(R.menu.menu_edit_note, menu);
    undo = menu.findItem(R.id.undo);
    redo = menu.findItem(R.id.redo);
    this.colors = menu.findItem(R.id.colorOfNote);
    undoRedo = new TextViewUndoRedo(binding.content, undo, redo);
    colorOfNote = new ColorOfNote(colors, Editable);
    updateUndoRedoBtnState();
    this.colors.setEnabled(false);
    colorId = note.getColor();
    return super.onCreateOptionsMenu(menu);
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
    colorId = note.getColor();

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

      return true;
    } else if (id == R.id.settings) {

      startActivity(new Intent(this, SettingsActivity.class));

      return true;
    } else if (id == R.id.archive) {

      return true;
    } else if (id == R.id.copy) {
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
                  + binding.noteTitle.getText().toString()
                  + "\n\n Note Content : "
                  + "\n"
                  + binding.content.getText().toString());
      clipBoard.setPrimaryClip(clip);

      return true;

    } else if (id == R.id.colorOfNote) {
      bottomSheet.show();

      return true;
    } else return false;
  }

  private void clickAnimation(View v, String color) {
    // TODO: Implement this method

    android.content.res.ColorStateList clrb =
        new android.content.res.ColorStateList(
            new int[][] {new int[] {}}, new int[] {Color.parseColor(color)});
    android.graphics.drawable.RippleDrawable ripdrb =
        new android.graphics.drawable.RippleDrawable(clrb, null, null);
    v.setBackground(ripdrb);
  }

  // Enabled(False),Enabled(True) to switch Preview Mod To Edit Mod loop
  public void EditorPreview(boolean Edit) {

    if (Edit == false) {

      binding.editorContinar.setVisibility(View.GONE);
      binding.noteTitle.setEnabled(false);
      binding.content.setEnabled(false);

    } else if (Edit == true) {

      binding.editorContinar.setVisibility(View.VISIBLE);
      binding.noteTitle.setEnabled(true);
      binding.content.setEnabled(true);
    }
  }

  // Updated Undo/Redo All times
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

  @Override
  public void onBackPressed() {
    // for switch preview Not And edut mod on Back btn
    if (Editable == true) {
      editNote();
    } else if (Editable == false) {
      finish();
    }
  }

  // Edit Note And Update Date Todey
  public void editNote() {

    note.setTitle(binding.noteTitle.getText().toString());
    note.setContent(binding.content.getText().toString());
    note.setColor(selectedNoteColor);
    note.setDate("Edit In : " + todeysDate);
    db.editNote(note);
    EditorPreview(false);
    Toast.makeText(this, "Edited successfully", Toast.LENGTH_SHORT).show();
    Editable = false;
    undoRedo.clearHistory();
    undoRedo.updateButtons();
    binding.fab.setIconResource(R.drawable.ic_edit);
    binding.noteTitleText.setText(binding.noteTitle.getText().toString());
  }
}
