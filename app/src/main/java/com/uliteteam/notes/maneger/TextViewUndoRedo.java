package com.uliteteam.notes.maneger;

import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.SharedPreferences;
import java.lang.CharSequence;
import java.util.LinkedList;
import android.text.TextWatcher;
import android.text.Editable;
import android.text.Selection;

public class TextViewUndoRedo {
  // Fields
  private boolean mIsUndoOrRedo = false;  // true if undoing or redoing
  private EditHistory mEditHistory;  // Edit history object
  private EditTextChangeListener mChangeListener;  // Text change listener object
  private TextView mTextView;  // TextView object being monitored for changes
  private MenuItem btnUndo;  // "Undo" button
  private MenuItem btnRedo;  // "Redo" button

  // Constructor
  public TextViewUndoRedo(TextView textView, MenuItem undo, MenuItem redo) {
    mTextView = textView;
    mEditHistory = new EditHistory();
    mChangeListener = new EditTextChangeListener();
    mTextView.addTextChangedListener(mChangeListener);
    btnUndo = undo;
    btnRedo = redo;
  }

  // Updates the state of the undo and redo buttons
  public void updateButtons() {
    if (btnRedo == null || btnUndo == null) return;

    // Set button transparency and enable/disable state based on whether undo/redo is possible
    btnUndo.getIcon().setAlpha(getCanUndo() ? 255 : 130);
    btnUndo.setEnabled(getCanUndo());
    btnRedo.getIcon().setAlpha(getCanRedo() ? 255 : 130);
    btnRedo.setEnabled(getCanRedo());
  }

  // Disconnects the text change listener
  public void disconnect() {
    mTextView.removeTextChangedListener(mChangeListener);
  }

  // Sets the maximum size of the edit history
  public void setMaxHistorySize(int maxHistorySize) {
    mEditHistory.setMaxHistorySize(maxHistorySize);
  }

  // Clears the edit history
  public void clearHistory() {
    mEditHistory.clear();
  }

  // Returns true if an undo operation can be performed
  public boolean getCanUndo() {
    return (mEditHistory.mmPosition > 0);
  }

  // Performs an undo operation
  public void undo() {
    EditItem edit = mEditHistory.getPrevious();
    if (edit == null) {
      return;
    }

    // Perform the undo operation
    Editable text = mTextView.getEditableText();
    int start = edit.mmStart;
    int end = start + (edit.mmAfter != null ? edit.mmAfter.length() : 0);

    mIsUndoOrRedo = true;
    text.replace(start, end, edit.mmBefore);
    mIsUndoOrRedo = false;

    // Remove underlines from the text
    for (Object o : text.getSpans(0, text.length(), android.text.style.UnderlineSpan.class)) {
      text.removeSpan(o);
    }

    // Set the cursor to the appropriate position
    Selection.setSelection(text, edit.mmBefore == null ? start : (start + edit.mmBefore.length()));
  }

  // Returns true if a redo operation can be performed
  public boolean getCanRedo() {
    return (mEditHistory.mmPosition < mEditHistory.mmHistory.size());
  }

  // Performs a redo operation
  public void redo() {
    EditItem edit = mEditHistory.getNext();
    if (edit == null) {
      return;
    }

    // Perform the redo operation
    Editable text = mTextView.getEditableText();
    int start = edit.mmStart;
    int end = start + (edit.mmBefore != null ? edit.mmBefore.length() : 0);

    mIsUndoOrRedo = true;
    text.replace(start, end, edit.mmAfter);
    mIsUndoOrRedo = false;


    for (Object o : text.getSpans(0, text.length(), android.text.style.UnderlineSpan.class)) {
      text.removeSpan(o);
    }

    Selection.setSelection(text, edit.mmAfter == null ? start : (start + edit.mmAfter.length()));
  }

  public void storePersistentState(android.content.SharedPreferences.Editor editor, String prefix) {

    editor.putString(prefix + ".hash", String.valueOf(mTextView.getText().toString().hashCode()));
    editor.putInt(prefix + ".maxSize", mEditHistory.mmMaxHistorySize);
    editor.putInt(prefix + ".position", mEditHistory.mmPosition);
    editor.putInt(prefix + ".size", mEditHistory.mmHistory.size());

    int i = 0;
    for (EditItem ei : mEditHistory.mmHistory) {
      String pre = prefix + "." + i;

      editor.putInt(pre + ".start", ei.mmStart);
      editor.putString(pre + ".before", ei.mmBefore.toString());
      editor.putString(pre + ".after", ei.mmAfter.toString());

      i++;
    }
  }

  public boolean restorePersistentState(SharedPreferences sp, String prefix)
      throws IllegalStateException {

    boolean ok = doRestorePersistentState(sp, prefix);
    if (!ok) {
      mEditHistory.clear();
    }

    return ok;
  }

  private boolean doRestorePersistentState(SharedPreferences sp, String prefix) {

    String hash = sp.getString(prefix + ".hash", null);
    if (hash == null) {
      return true;
    }

    if (Integer.valueOf(hash) != mTextView.getText().toString().hashCode()) {
      return false;
    }

    mEditHistory.clear();
    mEditHistory.mmMaxHistorySize = sp.getInt(prefix + ".maxSize", -1);

    int count = sp.getInt(prefix + ".size", -1);
    if (count == -1) {
      return false;
    }

    for (int i = 0; i < count; i++) {
      String pre = prefix + "." + i;

      int start = sp.getInt(pre + ".start", -1);
      String before = sp.getString(pre + ".before", null);
      String after = sp.getString(pre + ".after", null);

      if (start == -1 || before == null || after == null) {
        return false;
      }
      mEditHistory.add(new EditItem(start, before, after));
    }

    mEditHistory.mmPosition = sp.getInt(prefix + ".position", -1);
    if (mEditHistory.mmPosition == -1) {
      return false;
    }

    return true;
  }

  private final class EditHistory {

    private int mmPosition = 0;
    private int mmMaxHistorySize = -1;
    private final LinkedList<EditItem> mmHistory = new LinkedList<EditItem>();

    private void clear() {
      mmPosition = 0;
      mmHistory.clear();
    }

    private void add(EditItem item) {
      while (mmHistory.size() > mmPosition) {
        mmHistory.removeLast();
      }
      mmHistory.add(item);
      mmPosition++;

      if (mmMaxHistorySize >= 0) {
        trimHistory();
      }
    }

    private void setMaxHistorySize(int maxHistorySize) {
      mmMaxHistorySize = maxHistorySize;
      if (mmMaxHistorySize >= 0) {
        trimHistory();
      }
    }

    private void trimHistory() {
      while (mmHistory.size() > mmMaxHistorySize) {
        mmHistory.removeFirst();
        mmPosition--;
      }

      if (mmPosition < 0) {
        mmPosition = 0;
      }
    }

    private EditItem getPrevious() {
      if (mmPosition == 0) {
        return null;
      }
      mmPosition--;
      return mmHistory.get(mmPosition);
    }

    private EditItem getNext() {
      if (mmPosition >= mmHistory.size()) {
        return null;
      }

      EditItem item = mmHistory.get(mmPosition);
      mmPosition++;
      return item;
    }
  }

  private final class EditItem {
    private final int mmStart;
    private final CharSequence mmBefore;
    private final CharSequence mmAfter;

    public EditItem(int start, CharSequence before, CharSequence after) {
      mmStart = start;
      mmBefore = before;
      mmAfter = after;
    }
  }

  private final class EditTextChangeListener implements TextWatcher {

    private CharSequence mBeforeChange;
    private CharSequence mAfterChange;

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      if (mIsUndoOrRedo) {
        return;
      }

      mBeforeChange = s.subSequence(start, start + count);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
      if (mIsUndoOrRedo) {
        return;
      }

      mAfterChange = s.subSequence(start, start + count);
      mEditHistory.add(new EditItem(start, mBeforeChange, mAfterChange));
    }

    public void afterTextChanged(Editable s) {}
  }
}
