package com.uliteteam.notes.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.uliteteam.notes.R;
import com.uliteteam.notes.callback.BottomSheetCatalogCallBack;

public class BottomSheetCatalog {

  private final Context context;
  private final BottomSheetDialog bottomSheetDialog;
  public String selectedNoteColor;
  public AppBarLayout appbar;
  public View background;
  private final LinearLayout menuImage;
  public BottomSheetCatalogCallBack callBack;

  public BottomSheetCatalog(
      Context context, int style, String selectedNoteColor, BottomSheetCatalogCallBack callBack) {
    this.context = context;
    this.selectedNoteColor = selectedNoteColor;
    this.bottomSheetDialog = new BottomSheetDialog(context, style);

    View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.color_pick_note, null);
    bottomSheetDialog.setContentView(bottomSheetView);
    this.callBack = callBack;

    this.menuImage = bottomSheetView.findViewById(R.id.menuImage);
    this.menuImage.setClickable(true);
    this.menuImage.setOnClickListener(
        v -> {
          launchPhotoPicker();
          bottomSheetDialog.cancel();
        });
  }

  public void show() {
    this.bottomSheetDialog.show();
  }

  private void launchPhotoPicker() {
    // Add your implementation here
  }

  public void statusColors() {

    if (callBack != null) callBack.onChanged(selectedNoteColor);

    Window w = ((Activity) context).getWindow();

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

  public View getBackground() {
    return this.background;
  }

  public void setBackground(View background) {
    this.background = background;
  }
}
