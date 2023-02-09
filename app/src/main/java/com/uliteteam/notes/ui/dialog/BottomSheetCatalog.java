package com.uliteteam.notes.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.ext.SdkExtensions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.uliteteam.notes.BuildConfig;
import com.uliteteam.notes.R;
import com.uliteteam.notes.callback.BottomSheetColorsCallBack;
import com.uliteteam.notes.databinding.ColorPickNoteBinding;
import com.uliteteam.notes.model.Note;
import com.uliteteam.notes.ui.dialog.BottomSheetCatalog;
import com.uliteteam.notes.util.NoteDataBase;
import com.uliteteam.notes.utile.FilePicker;

public class BottomSheetCatalog {

  private final Context context;
  private final BottomSheetDialog bottomSheetDialog;
  private final ImageView[] imageColors;
  private final View[] viewColors;
  public String selectedNoteColor;
  private final LinearLayout menuImage;

  public BottomSheetCatalog(Context context, int style) {
    this.context = context;
    this.selectedNoteColor = selectedNoteColor;
    this.bottomSheetDialog = new BottomSheetDialog(context, style);
    View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.color_pick_note, null);
    bottomSheetDialog.setContentView(bottomSheetView);
    this.imageColors = new ImageView[11];
    this.viewColors = new View[11];

    for (int i = 0; i < 11; i++) {
      this.imageColors[i] =
          bottomSheetView.findViewById(
              context
                  .getResources()
                  .getIdentifier("imageColor" + (i + 1), "id", context.getPackageName()));
      this.viewColors[i] =
          bottomSheetView.findViewById(
              context
                  .getResources()
                  .getIdentifier("color" + (i + 1), "id", context.getPackageName()));
    }
    this.menuImage = bottomSheetView.findViewById(R.id.menuImage);
    this.menuImage.setClickable(true);
    this.menuImage.setOnClickListener(
        v -> {
          launchPhotoPicker();
          bottomSheetDialog.cancel();
        });
    for (int i = 0; i < imageColors.length; i++) {
      imageColors[i].setImageResource(
          i == Integer.parseInt(selectedNoteColor) ? R.drawable.done_circle : 0);
    }
    for (int i = 0; i < 11; i++) {
      final int finalI = i;
      viewColors[i].setOnClickListener(
          v -> {
            selectedNoteColor = String.valueOf(finalI);
            for (int j = 0; j < 11; j++) {
              imageColors[j].setImageResource(j == finalI ? R.drawable.done_circle : 0);
            }
            statusColors();
          });
    }
  }

  public void show() {
    this.bottomSheetDialog.show();
  }

  private void launchPhotoPicker() {
    // Add your implementation here
  }

  private void statusColors() {
    // Add your implementation here
  }

  public String getSelectedNoteColor() {
    return this.selectedNoteColor;
  }

  public void setSelectedNoteColor(String selectedNoteColor) {
    this.selectedNoteColor = selectedNoteColor;
  }
}
