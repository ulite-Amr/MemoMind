package com.uliteteam.notes.ui.dialog;

import android.os.Build;
import android.os.ext.SdkExtensions;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import com.google.android.material.appbar.AppBarLayout;
import com.uliteteam.notes.BuildConfig;
import com.uliteteam.notes.R;
import android.content.Context;
import android.app.Activity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.uliteteam.notes.callback.BottomSheetColorsCallBack;
import com.uliteteam.notes.databinding.ColorPickNoteBinding;
import com.uliteteam.notes.model.Note;
import com.uliteteam.notes.ui.dialog.BottomSheetCatalog;
import com.uliteteam.notes.util.NoteDataBase;
import com.uliteteam.notes.utile.FilePicker;

public class BottomSheetCatalog extends BottomSheetDialog {

  public Context context;
  // inilaizetion
  public NoteDataBase db;
  public Note note;
  public Window w = this.getWindow();
  public long id;
  public View backgroung;
  public AppBarLayout appbar;
  public FilePicker filepicker;
  public String selectedNoteColor;
  public ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

  public BottomSheetCatalog(Context context, int theme) {
    super(context, theme );

    this.context = context;
        
    View bottomSheetView = getLayoutInflater().inflate(R.layout.color_pick_note, null);
    setContentView(bottomSheetView);
    final ImageView[] imageColors = new ImageView[11];
    final View[] viewColors = new View[11];
    for (int i = 0; i < 11; i++) {
      imageColors[i] =
          bottomSheetView.findViewById(
              context
                  .getResources()
                  .getIdentifier("imageColor" + (i + 1), "id", context.getPackageName()));
      viewColors[i] =
          bottomSheetView.findViewById(
              context
                  .getResources()
                  .getIdentifier("color" + (i + 1), "id", context.getPackageName()));
    }
    LinearLayout menuImage = bottomSheetView.findViewById(R.id.menuImage);
    menuImage.setClickable(true);
    menuImage.setOnClickListener(
        v -> {
          launchPhotoPicker();
          cancel();
        });
    
  }

  public void statusColors(View background, AppBarLayout appbar) {

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
      background.setBackgroundColor(context.getColor(selectedColorId));
      w.setStatusBarColor(context.getColor(selectedColorId));
      w.setNavigationBarColor(context.getColor(selectedColorId));
      appbar.setBackgroundColor(context.getColor(selectedColorId));
    }
  }

  private boolean isPhotoPickerAvailable() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) return true;
    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
      return SdkExtensions.getExtensionVersion(Build.VERSION_CODES.R) >= 2;
    else return false;
  }

  public void launchPhotoPicker() {
    if (isPhotoPickerAvailable()) {
      // Launch the photo picker and allow the user to choose only images.
      pickMedia.launch(
          new PickVisualMediaRequest.Builder()
              .setMediaType(PickVisualMedia.ImageOnly.INSTANCE)
              .build());
    } else {
      filepicker.launch("image/*");
    }
  }

  public View getBackgroung() {
    return this.backgroung;
  }

  public void setBackgroung(View backgroung) {
    this.backgroung = backgroung;
  }

  public AppBarLayout getAppbar() {
    return this.appbar;
  }

  public void setAppbar(AppBarLayout appbar) {
    this.appbar = appbar;
  }

  public String getSelectedNoteColor() {
    return this.selectedNoteColor;
  }

  public void setSelectedNoteColor(String selectedNoteColor) {
    this.selectedNoteColor = selectedNoteColor;
  }
}
