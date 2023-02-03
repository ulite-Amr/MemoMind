package com.uliteteam.notes.activity;
/*---------What does this class do?----------
 *    1-Get Details From Notes Adapter by Key "ID"
 *    2-Edit And Save Notes
 *     --   --  -- -- -- -- -- -- -- */
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.itsaky.androidide.logsender.LogSender;
import com.uliteteam.notes.BaseActivity;
import com.uliteteam.notes.Note;
import com.uliteteam.notes.R;
import com.uliteteam.notes.databinding.ActivityDetailsBinding;
import com.uliteteam.notes.maneger.ColorOfNote;
import com.uliteteam.notes.maneger.TextViewUndoRedo;
import com.uliteteam.notes.util.NoteDataBase;
import java.security.PublicKey;



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
  Note note;
  String selectedNoteColor;
  Window w = this.getWindow();
    

  String todeysDate = Calendar.getInstance().getTime().toString();
  final Runnable updateMenuIconsState = () -> undoRedo.updateButtons();

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
      
 
    selectedNoteColor = note.getColor();
        
        statusColors();
  
      binding.noteTitle.setTransitionName("TITLE");  
        
    EditorPreview(false);
    binding.noteTitle.setText(note.getTitle());
    binding.content.setText(note.getContent());

    binding.noteTitleText.setText(note.getTitle());
    binding.contentText.setText(note.getContent());

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

      final BottomSheetDialog bottomSheetColors =
          new BottomSheetDialog(Details.this, R.style.ModalBottomSheetDialog);
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
                    
                    statusColors();        
                // Setting the image resource of the imageColors array to the done_circle drawable
                // if the color was selected and setting it to 0 otherwise
              }
            });
        
        
      }
      bottomSheetColors.show();      

      return true;
    } else return false;
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

  public void statusColors() {
        
         w = this.getWindow();

    int[] colorIds = {
      0,
      R.color.color1,
      R.color.color2,
      R.color.color3,
      R.color.color4,
      R.color.color5,
      R.color.color6,
      R.color.color7,
      R.color.color8,
      R.color.color9,
      R.color.color10
    };
    if (Integer.parseInt(selectedNoteColor) > 0) {
      int selectedColorId = colorIds[Integer.parseInt(selectedNoteColor)];
      binding.Coordinator.setBackgroundColor(this.getColor(selectedColorId));
      w.setStatusBarColor(this.getColor(selectedColorId));
      w.setNavigationBarColor(this.getColor(selectedColorId));
            binding.appBar.setBackgroundColor(this.getColor(selectedColorId));
    }
  }
}
