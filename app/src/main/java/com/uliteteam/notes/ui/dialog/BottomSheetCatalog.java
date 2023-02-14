package com.uliteteam.notes.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.elevation.SurfaceColors;
import com.uliteteam.notes.R;
import com.uliteteam.notes.adapter.ColorAdapter;
import com.uliteteam.notes.callback.BottomSheetCatalogCallBack;
import com.uliteteam.notes.callback.ColersCallBack;
import java.util.ArrayList;
import java.util.List;

public class BottomSheetCatalog {

  private final Context context;
  private final BottomSheetDialog bottomSheetDialog;
  public String selectedNoteColor;
  public AppBarLayout appbar;
  public View background;
  private final LinearLayout menuImage;
  public BottomSheetCatalogCallBack callBack;
  public RecyclerView colorRecycler;
  private final ColorAdapter adapter;

  public BottomSheetCatalog(
      Context context, int style, String selectedNoteColor, BottomSheetCatalogCallBack callBack) {
    this.context = context;
    this.selectedNoteColor = selectedNoteColor;
    this.bottomSheetDialog = new BottomSheetDialog(context, style);

    View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.color_pick_note, null);
    bottomSheetDialog.setContentView(bottomSheetView);
    this.callBack = callBack;

    this.menuImage = bottomSheetView.findViewById(R.id.menuImage);

    /*List Added Here In bottomSheet
    -----------------------------*/
    this.colorRecycler = bottomSheetView.findViewById(R.id.ColorsList);

    LinearLayoutManager horizontalLayoutManager =
        new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

    List<Integer> colors = new ArrayList<>();
    colors.add(Color.GRAY);
    colors.add(R.color.color1);
    colors.add(R.color.color2);
    colors.add(R.color.color3);
    colors.add(R.color.color4);
    colors.add(R.color.color5);
    colors.add(R.color.color6);
    colors.add(R.color.color7);
    colors.add(R.color.color8);
    colors.add(R.color.color9);
    colors.add(R.color.color10);

    colorRecycler.setLayoutManager(horizontalLayoutManager);
    adapter =
        new ColorAdapter(
            context,
            colors,
            Integer.parseInt(selectedNoteColor),
            new ColersCallBack() {
              @Override
              public void onChanged(String color) {
                setSelectedNoteColor(color);
                statusColors();
              }
            });
    colorRecycler.setAdapter(adapter);

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
    } else {
      int colorBase = SurfaceColors.SURFACE_2.getColor(context);
      background.setBackgroundColor(colorBase);
      w.setStatusBarColor(colorBase);
      w.setNavigationBarColor(colorBase);
      appbar.setBackgroundColor(colorBase);
    }
  }

  public String getSelectedNoteColor() {
    return String.valueOf(adapter.getSelectedColor());
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
